package me.while1cry.napcat4j.client;

import me.while1cry.napcat4j.dto.group.GroupDto;
import me.while1cry.napcat4j.dto.group.GroupMemberDto;
import me.while1cry.napcat4j.dto.user.FriendDto;
import me.while1cry.napcat4j.dto.user.UserDto;
import me.while1cry.napcat4j.entity.message.Message;
import me.while1cry.napcat4j.entity.user.LoginInfo;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface OneBotAPI {

    CompletableFuture<String> sendPrivateMessage(String userId, Message message);

    CompletableFuture<String> sendGroupMessage(String groupId, Message message);

    CompletableFuture<Boolean> recallMessage(String messageId);

    CompletableFuture<Message> getMessage(String messageId);

    CompletableFuture<Boolean> sendLike(String userId, int times);

    CompletableFuture<Boolean> kickGroupMember(String groupId, String userId, boolean blacklist);

    CompletableFuture<Boolean> banGroupMember(String groupId, String userId, Duration duration);

    CompletableFuture<Boolean> banGroup(String groupId, boolean b);

    CompletableFuture<Boolean> setGroupAdmin(String groupId, String userId, boolean admin);

    CompletableFuture<Boolean> leaveGroup(String groupId);

    CompletableFuture<Boolean> setFriendAddRequest(String flag, boolean approve, String remark);

    CompletableFuture<Boolean> setGroupAddRequest(String flag, boolean approve, String denyReason);

    CompletableFuture<LoginInfo> getLoginInfo();

    CompletableFuture<UserDto> getStrangerInfo(String userId);

    CompletableFuture<Set<FriendDto>> getFriendList(boolean noCache);

    CompletableFuture<GroupDto> getGroupInfo(String groupId);

    CompletableFuture<Set<GroupDto>> getGroupList();

    CompletableFuture<GroupMemberDto> getGroupMemberInfo(String groupId, String userId);

    CompletableFuture<Set<GroupMemberDto>> getGroupMemberList(String groupId);

    CompletableFuture<Boolean> cleanCache();
}
