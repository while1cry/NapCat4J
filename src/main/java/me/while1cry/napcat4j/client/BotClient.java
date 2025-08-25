package me.while1cry.napcat4j.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.while1cry.napcat4j.NapCat4J;
import me.while1cry.napcat4j.dto.group.GroupDto;
import me.while1cry.napcat4j.dto.group.GroupMemberDto;
import me.while1cry.napcat4j.dto.group.GroupOverviewDto;
import me.while1cry.napcat4j.dto.user.FriendDto;
import me.while1cry.napcat4j.dto.user.UserDto;
import me.while1cry.napcat4j.entity.message.Message;
import me.while1cry.napcat4j.entity.status.OnlineStatus;
import me.while1cry.napcat4j.entity.user.LoginInfo;
import me.while1cry.napcat4j.event.EventManager;
import me.while1cry.napcat4j.event.message.GroupMessageEvent;
import me.while1cry.napcat4j.event.message.PrivateMessageEvent;
import me.while1cry.napcat4j.event.meta.ConnectMetaEvent;
import me.while1cry.napcat4j.event.meta.HeartbeatMetaEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.Closeable;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public abstract class BotClient implements OneBotAPI, GoCQHTTPAPI, NapCatAPI, Closeable {

    protected static final ObjectMapper mapper = new ObjectMapper();

    protected final NapCat4J core;
    protected final Logger logger = core.getLogger();
    protected final URI uri = core.getAddress();
    protected final EventManager eventManager = core.getEventManager();

    public abstract void connect();

    public abstract boolean isConnected();

    public abstract void close();

    @Override
    public CompletableFuture<Boolean> deleteFriend(String userId, boolean blacklist, boolean bothDel) {
        ObjectNode params = mapper.createObjectNode()
                .put("user_id", userId)
                .put("temp_block", blacklist)
                .put("temp_both_del", bothDel);
        return send("delete_friend", params)
                .thenApply(rsp -> rsp.get("data").get("result").asInt() == 0);
    }

    @Override
    public CompletableFuture<Boolean> setGroupSign(String groupId) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId);
        return send("set_group_sign", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setOnlineStatus(OnlineStatus status) {
        ObjectNode params = mapper.valueToTree(status);
        return send("set_online_status", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    @SneakyThrows
    public CompletableFuture<String> sendPrivateMessage(String userId, Message message) {
        ObjectNode params = mapper.createObjectNode()
                .put("user_id", userId)
                .set("message", mapper.readTree(message.toJson()));
        return send("send_private_msg", params)
                .thenApply(rsp -> rsp.get("data").get("message_id").asText());
    }

    @Override
    @SneakyThrows
    public CompletableFuture<String> sendGroupMessage(String groupId, Message message) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .set("message", mapper.readTree(message.toJson()));
        return send("send_group_msg", params)
                .thenApply(rsp -> rsp.get("data").get("message_id").asText());
    }

    @Override
    public CompletableFuture<Boolean> recallMessage(String messageId) {
        ObjectNode params = mapper.createObjectNode().put("message_id", messageId);
        return send("delete_msg", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Message> getMessage(String messageId) {
        ObjectNode params = mapper.createObjectNode().put("message_id", messageId);
        return send("get_msg", params)
                .thenApply(rsp -> Message.fromJson((ArrayNode) rsp.get("data").get("message")));
    }

    @Override
    public CompletableFuture<Boolean> sendLike(String userId, int times) {
        ObjectNode params = mapper.createObjectNode()
                .put("user_id", userId)
                .put("times", times);
        return send("send_like", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> kickGroupMember(String groupId, String userId, boolean blacklist) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("user_id", userId)
                .put("reject_add_request", blacklist);
        return send("set_group_kick", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> banGroupMember(String groupId, String userId, Duration duration) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("user_id", userId)
                .put("duration", duration.toSeconds());
        return send("set_group_ban", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> banGroup(String groupId, boolean b) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("enable", b);
        return send("set_group_whole_ban", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setGroupAdmin(String groupId, String userId, boolean b) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("user_id", userId)
                .put("enable", b);
        return send("set_group_whole_ban", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> leaveGroup(String groupId) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId);
        return send("set_group_leave", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setFriendAddRequest(String flag, boolean approve, String remark) {
        ObjectNode params = mapper.createObjectNode()
                .put("flag", flag)
                .put("approve", approve)
                .put("remark", remark);
        return send("set_friend_add_request", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<Boolean> setGroupAddRequest(String flag, boolean approve, @Nullable String denyReason) {
        ObjectNode params = mapper.createObjectNode()
                .put("flag", flag)
                .put("approve", approve);
        if (denyReason != null)
            params.put("reason", denyReason);
        return send("set_group_add_request", params)
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    @Override
    public CompletableFuture<LoginInfo> getLoginInfo() {
        return send("get_login_info", mapper.createObjectNode())
                .thenApply(rsp -> {
                    try {
                        return mapper.treeToValue(rsp.get("data"), LoginInfo.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<UserDto> getStrangerInfo(String userId) {
        ObjectNode params = mapper.createObjectNode().put("user_id", userId);
        return send("get_stranger_info", params)
                .thenApply(rsp -> {
                    try {
                        return mapper.treeToValue(rsp.get("data"), UserDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Set<FriendDto>> getFriendList(boolean noCache) {
        ObjectNode params = mapper.createObjectNode().put("no_cache", noCache);
        return send("get_friend_list", params)
                .thenApply(rsp -> {
                    try {
                        FriendDto[] array = mapper.treeToValue(rsp.get("data"), FriendDto[].class);
                        return new HashSet<>(Arrays.asList(array));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<GroupDto> getGroupInfo(String groupId) {
        ObjectNode params = mapper.createObjectNode().put("group_id", groupId);
        return send("get_group_info", params)
                .thenApply(rsp -> {
                    try {
                        return mapper.treeToValue(rsp.get("data"), GroupDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Set<GroupOverviewDto>> getGroupList(boolean noCache) {
        ObjectNode params = mapper.createObjectNode().put("no_cache", noCache);
        return send("get_group_list", params)
                .thenApply(rsp -> {
                    try {
                        GroupOverviewDto[] array = mapper.treeToValue(rsp.get("data"), GroupOverviewDto[].class);
                        return new HashSet<>(Arrays.asList(array));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<GroupMemberDto> getGroupMemberInfo(String groupId, String userId, boolean noCache) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("user_id", userId)
                .put("no_cache", noCache);
        return send("get_group_member_info", params)
                .thenApply(rsp -> {
                    try {
                        return mapper.treeToValue(rsp.get("data"), GroupMemberDto.class);
                    } catch (JsonProcessingException e) {
                        throw  new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Set<GroupMemberDto>> getGroupMemberList(String groupId, boolean noCache) {
        ObjectNode params = mapper.createObjectNode()
                .put("group_id", groupId)
                .put("no_cache", noCache);
        return send("get_group_list", params)
                .thenApply(rsp -> {
                    try {
                        GroupMemberDto[] array = mapper.treeToValue(rsp.get("data"), GroupMemberDto[].class);
                        return new HashSet<>(Arrays.asList(array));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public CompletableFuture<Boolean> cleanCache() {
        return send("clean_cache", mapper.createObjectNode())
                .thenApply(rsp -> rsp.get("status").asText().equals("ok"));
    }

    protected void eventHandle(String event) {
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
                        case "heartbeat" -> eventManager.callEvent(new HeartbeatMetaEvent(json.get("time").asLong(),
                                json.get("self_id").asText(),
                                new HeartbeatMetaEvent.Status(
                                        json.get("status").get("online").asBoolean(),
                                        json.get("status").get("good").asBoolean()),
                                json.get("interval").asLong()));
                        default -> logger.warn("Unknown meta event type: {}", metaEventType);
                    }
                }
                case "message" -> {
                    Message message = Message.fromJson((ArrayNode) json.get("message"));
                    switch (json.get("message_type").asText()) {
                        case "group" -> eventManager.callEvent(new GroupMessageEvent(
                                json.get("group_id").asText(),
                                json.get("user_id").asText(),
                                json.get("message_id").asText(),
                                json.get("raw_message").asText(),
                                message
                        ));
                        case "private" -> {
                            eventManager.callEvent(new PrivateMessageEvent(
                                    json.get("sub_type").asText(),
                                    json.get("user_id").asText(),
                                    json.get("message_id").asText(),
                                    json.get("raw_message").asText(),
                                    message
                            ));
                        }
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

    protected abstract CompletableFuture<ObjectNode> send(String action, ObjectNode params);
}
