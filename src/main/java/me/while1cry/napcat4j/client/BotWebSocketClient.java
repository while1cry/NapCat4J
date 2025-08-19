package me.while1cry.napcat4j.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import me.while1cry.napcat4j.Bot;
import me.while1cry.napcat4j.entity.group.GroupInfo;
import me.while1cry.napcat4j.entity.group.GroupMemberInfo;
import me.while1cry.napcat4j.entity.message.MessageArray;
import me.while1cry.napcat4j.entity.user.Friend;
import me.while1cry.napcat4j.entity.user.LoginInfo;
import me.while1cry.napcat4j.entity.user.OnlineStatus;
import me.while1cry.napcat4j.entity.user.Stranger;
import me.while1cry.napcat4j.event.EventCaller;
import me.while1cry.napcat4j.event.EventManager;
import me.while1cry.napcat4j.event.message.GroupMessageEvent;
import me.while1cry.napcat4j.event.message.PrivateMessageEvent;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BotWebSocketClient extends WebSocketClient implements Bot, EventCaller {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final String token;
    private final Logger logger;
    private final long TIMEOUT;
    private final ConcurrentHashMap<UUID, CompletableFuture<ObjectNode>> pending = new ConcurrentHashMap<>();
    @Getter
    private final EventManager eventManager;

    public BotWebSocketClient(String address, @Nullable String token) throws URISyntaxException {
        super(new URI(address));
        this.token = token;
        this.logger = LoggerFactory.getLogger("NapCat4J");
        this.TIMEOUT = 5000;

        this.eventManager = new EventManager(logger);
    }

    public BotWebSocketClient(Logger logger, String address, @Nullable String token, long timeout) throws URISyntaxException {
        super(new URI(address));
        this.token = token;
        this.logger = logger;
        this.TIMEOUT = timeout;

        this.eventManager = new EventManager(logger);
    }

    @Override
    public void createConnection() {
        if (token != null)
            addHeader("Authorization", "Bearer " + token);
        logger.info("Connecting to {}...", getURI());
        try {
            connectBlocking();
            logger.info("Connected to {}", getURI());
        } catch (InterruptedException e) {
            logger.error("Error connecting to {}", getURI(), e);
        }
    }

    @Override
    public boolean isConnected() {
        return getReadyState().equals(ReadyState.OPEN);
    }

    @Override
    public void disconnect() {
        this.close();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }

    @Override
    public void onMessage(String s) {
        try {
            ObjectNode json = (ObjectNode) mapper.readTree(s);
            if (json.has("echo")) {
                UUID echo = UUID.fromString(json.get("echo").asText());
                CompletableFuture<ObjectNode> future = pending.remove(echo);
                if (future != null) {
                    future.complete(json);
                }
            } else {
                eventHandle(s);
            }
        } catch (Exception e) {
            logger.error("Error parsing message", e);
        }
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        String message = StandardCharsets.UTF_8.decode(bytes).toString();
        onMessage(message);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.info("Disconnected from {}", getURI());
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public CompletableFuture<String> sendPrivateMessage(String userId, MessageArray messageArray) {
        final String action = "send_private_msg";

        return send(action,
                mapper.createObjectNode()
                        .put("user_id", userId)
                        .set("message", mapper.valueToTree(messageArray.messages)),
                resp -> resp.get("data").get("message_id").asText());
    }

    @Override
    public CompletableFuture<String> sendGroupMessage(String groupId, MessageArray messageArray) {
        final String action = "send_group_msg";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId)
                        .set("message", mapper.valueToTree(messageArray.messages)),
                resp -> resp.get("data").get("message_id").asText());
    }

    @Override
    public CompletableFuture<Boolean> recallMessage(String messageId) {
        final String action = "delete_msg";

        return send(action,
                mapper.createObjectNode()
                        .put("message_id", messageId),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<MessageArray> getMessage(String messageId) {
        final String action = "get_msg";

        return send(action,
                mapper.createObjectNode()
                        .put("message_id", messageId),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), MessageArray.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Boolean> sendLike(String userId, int times) {
        final String action = "send_like";

        return send(action,
                mapper.createObjectNode()
                        .put("user_id", userId)
                        .put("times", times),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> kickGroupMember(String groupId, String userId, boolean blacklist) {
        final String action = "set_group_kick";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId)
                        .put("user_id", userId)
                        .put("reject_add_request", blacklist),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> banGroupMember(String groupId, String userId, Duration duration) {
        final String action = "set_group_ban";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId)
                        .put("user_id", userId)
                        .put("duration", duration.toSeconds()),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> banGroup(String groupId, boolean b) {
        final String action = "set_group_whole_ban";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId)
                        .put("enable", b),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setGroupAdmin(String groupId, String userId, boolean admin) {
        final String action = "set_group_admin";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId)
                        .put("user_id", userId)
                        .put("enable", admin),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> leaveGroup(String groupId) {
        final String action = "set_group_leave";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setFriendAddRequest(String flag, boolean approve, String remark) {
        final String action = "set_friend_add_request";

        return send(action,
                mapper.createObjectNode()
                        .put("flag", flag)
                        .put("approve", approve)
                        .put("remark", remark),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setGroupAddRequest(String flag, boolean approve, String denyReason) {
        final String action = "set_group_add_request";

        return send(action,
                mapper.createObjectNode()
                        .put("flag", flag)
                        .put("approve", approve)
                        .put("reason", denyReason),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<LoginInfo> getLoginInfo() {
        final String action = "get_login_info";

        return send(action,
                mapper.createObjectNode(),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), LoginInfo.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Stranger> getStrangerInfo(String userId) {
        final String action = "get_stranger_info";

        return send(action,
                mapper.createObjectNode()
                        .put("user_id", userId),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), Stranger.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Set<Friend>> getFriendList(boolean noCache) {
        final String action = "get_friend_list";

        return send(action,
                mapper.createObjectNode()
                        .put("no_cache", noCache),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), new TypeReference<>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<GroupInfo> getGroupInfo(String groupId) {
        final String action = "get_group_info";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), GroupInfo.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Set<GroupInfo>> getGroupList() {
        return null;
    }

    @Override
    public CompletableFuture<GroupMemberInfo> getGroupMemberInfo(String groupId, String userId) {
        return null;
    }

    @Override
    public CompletableFuture<Set<GroupMemberInfo>> getGroupMemberList(String groupId) {
        final String action = "get_group_member_list";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId),
                resp -> {
                    try {
                        return mapper.treeToValue(resp.get("data"), new TypeReference<>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Boolean> cleanCache() {
        final String action = "clean_cache";

        return send(action,
                mapper.createObjectNode(),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> deleteFriend(String userId, boolean blacklist, boolean bothDel) {
        final String action = "delete_friend";

        return send(action,
                mapper.createObjectNode()
                        .put("user_id", userId)
                        .put("temp_block", blacklist)
                        .put("temp_both_del", bothDel),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setGroupSign(String groupId) {
        final String action = "set_group_sign";

        return send(action,
                mapper.createObjectNode()
                        .put("group_id", groupId),
                resp -> resp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setOnlineStatus(OnlineStatus status) {
        final String action = "set_online_status";

        return send(action,
                status.toJson(),
                resp -> resp.get("status").asText().equals("ok"));
    }

    private void eventHandle(String event) {
        try {
            ObjectNode json = (ObjectNode) mapper.readTree(event);
            String type = json.get("post_type").asText();
            switch (type) {
                case "meta_event" -> {
                    String metaEventType = json.get("meta_event_type").asText();
                    @Nullable String subType = json.get("sub_type") == null ? null : json.get("sub_type").asText();
                    switch (metaEventType) {
                        case "lifecycle" -> {
                            if (subType != null && subType.equals("connect")) {
                                logger.debug("Receive connection response from {}", getURI());
                            }
                        }
                        case "heartbeat" -> {
                            if (subType != null && subType.equals("connect")) {
                                logger.debug("Receive heartbeat from {}", getURI());
                            }
                        }
                        default -> logger.warn("Unknown meta event type: {}", metaEventType);
                    }
                }
                case "message" -> {
                    ObjectNode node = mapper.createObjectNode();
                    node.set("messages", json.get("message"));
                    MessageArray messageArray = mapper.treeToValue(node, MessageArray.class);
                    switch (json.get("message_type").asText()) {
                        case "group" -> eventManager.callEvent(new GroupMessageEvent(
                                json.get("group_id").asText(),
                                json.get("user_id").asText(),
                                json.get("message_id").asText(),
                                json.get("raw_message").asText(),
                                messageArray
                        ));
                        case "private" -> eventManager.callEvent(new PrivateMessageEvent(
                                json.get("sub_type").asText(),
                                json.get("user_id").asText(),
                                json.get("message_id").asText(),
                                json.get("raw_message").asText(),
                                messageArray
                        ));
                    }
                }
                case "notice" -> {
                    // TODO
                }
                default -> logger.warn("Unknown event type: {}", type);
            }
        } catch (Exception e) {
            logger.error("Error parsing event", e);
        }
    }

    private <T> CompletableFuture<T> send(
            String action,
            ObjectNode params,
            Function<ObjectNode, T> resultExtractor
    ) {
        UUID echo = UUID.randomUUID();

        ObjectNode body = mapper.createObjectNode()
                .put("action", action)
                .put("echo", echo.toString())
                .set("params", params);

        CompletableFuture<ObjectNode> futureResponse = new CompletableFuture<>();
        pending.put(echo, futureResponse);

        try {
            send(mapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            logger.error("Error sending action {}", action, e);
            futureResponse.completeExceptionally(e);
        }

        return futureResponse.thenApply(resultExtractor).orTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
