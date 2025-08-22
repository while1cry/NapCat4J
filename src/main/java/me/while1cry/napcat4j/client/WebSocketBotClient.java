package me.while1cry.napcat4j.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import me.while1cry.napcat4j.dto.group.GroupDto;
import me.while1cry.napcat4j.dto.group.GroupMemberDto;
import me.while1cry.napcat4j.dto.user.FriendDto;
import me.while1cry.napcat4j.dto.user.UserDto;
import me.while1cry.napcat4j.entity.message.Message;
import me.while1cry.napcat4j.entity.status.OnlineStatus;
import me.while1cry.napcat4j.entity.user.LoginInfo;
import me.while1cry.napcat4j.event.EventCaller;
import me.while1cry.napcat4j.event.EventManager;
import me.while1cry.napcat4j.event.message.GroupMessageEvent;
import me.while1cry.napcat4j.event.message.PrivateMessageEvent;
import me.while1cry.napcat4j.event.meta.ConnectMetaEvent;
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

public class WebSocketBotClient extends WebSocketClient implements BotClient, EventCaller {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final String token;
    private final Logger logger;
    private final Duration TIMEOUT;
    private final ConcurrentHashMap<UUID, CompletableFuture<ObjectNode>> pending = new ConcurrentHashMap<>();
    @Getter
    private final EventManager eventManager;

    public WebSocketBotClient(String address, @Nullable String token) throws URISyntaxException {
        super(new URI(address));
        this.token = token;
        this.logger = LoggerFactory.getLogger("NapCat4J");
        this.TIMEOUT = Duration.ofSeconds(5);

        this.eventManager = new EventManager(logger);
    }

    public WebSocketBotClient(Logger logger, String address, @Nullable String token, Duration timeout) throws URISyntaxException {
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
        connect();
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
        logger.error("Web socket client error", e);
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
                                eventManager.callEvent(new ConnectMetaEvent(json.get("time").asLong(), json.get("self_id").asText()));
                            }
                        }
                        case "heartbeat" -> {
                            logger.debug("Receive heartbeat from {}", getURI());
                        }
                        default -> logger.warn("Unknown meta event type: {}", metaEventType);
                    }
                }
                case "message" -> {
                    ObjectNode node = mapper.createObjectNode();
                    node.set("messages", json.get("message"));
                    Message message = mapper.treeToValue(node, Message.class);
                    switch (json.get("message_type").asText()) {
                        case "group" -> eventManager.callEvent(new GroupMessageEvent(
                                json.get("group_id").asText(),
                                json.get("user_id").asText(),
                                json.get("message_id").asText(),
                                json.get("raw_message").asText(),
                                message
                        ));
                        case "private" -> eventManager.callEvent(new PrivateMessageEvent(
                                json.get("sub_type").asText(),
                                json.get("user_id").asText(),
                                json.get("message_id").asText(),
                                json.get("raw_message").asText(),
                                message
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

    @Override
    public CompletableFuture<Boolean> deleteFriend(String userId, boolean blacklist, boolean bothDel) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> setGroupSign(String groupId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> setOnlineStatus(OnlineStatus status) {
        return null;
    }

    @Override
    public CompletableFuture<String> sendPrivateMessage(String userId, Message message) {
        return null;
    }

    @Override
    public CompletableFuture<String> sendGroupMessage(String groupId, Message message) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> recallMessage(String messageId) {
        return null;
    }

    @Override
    public CompletableFuture<Message> getMessage(String messageId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> sendLike(String userId, int times) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> kickGroupMember(String groupId, String userId, boolean blacklist) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> banGroupMember(String groupId, String userId, Duration duration) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> banGroup(String groupId, boolean b) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> setGroupAdmin(String groupId, String userId, boolean admin) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> leaveGroup(String groupId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> setFriendAddRequest(String flag, boolean approve, String remark) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> setGroupAddRequest(String flag, boolean approve, String denyReason) {
        return null;
    }

    @Override
    public CompletableFuture<LoginInfo> getLoginInfo() {
        return null;
    }

    @Override
    public CompletableFuture<UserDto> getStrangerInfo(String userId) {
        return null;
    }

    @Override
    public CompletableFuture<Set<FriendDto>> getFriendList(boolean noCache) {
        return null;
    }

    @Override
    public CompletableFuture<GroupDto> getGroupInfo(String groupId) {
        return null;
    }

    @Override
    public CompletableFuture<Set<GroupDto>> getGroupList() {
        return null;
    }

    @Override
    public CompletableFuture<GroupMemberDto> getGroupMemberInfo(String groupId, String userId) {
        return null;
    }

    @Override
    public CompletableFuture<Set<GroupMemberDto>> getGroupMemberList(String groupId) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> cleanCache() {
        return null;
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

        return futureResponse.thenApply(resultExtractor).orTimeout(TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
    }
}
