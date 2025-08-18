package me.while1cry.napcat4j;

import me.while1cry.napcat4j.entity.user.LoginInfo;
import me.while1cry.napcat4j.entity.group.GroupInfo;
import me.while1cry.napcat4j.entity.group.GroupMemberInfo;
import me.while1cry.napcat4j.entity.message.MessageArray;
import me.while1cry.napcat4j.entity.user.Friend;
import me.while1cry.napcat4j.entity.user.Stranger;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface OneBotAPI {

    CompletableFuture<String> sendPrivateMessage(String userId, MessageArray messageArray);
    CompletableFuture<String> sendGroupMessage(String groupId, MessageArray messageArray);
    CompletableFuture<Boolean> recallMessage(String messageId);
    CompletableFuture<MessageArray> getMessage(String messageId);
    CompletableFuture<Boolean> sendLike(String userId, int times);
    CompletableFuture<Boolean> kickGroupMember(String groupId, String userId, boolean blacklist);
    CompletableFuture<Boolean> banGroupMember(String groupId, String userId, Duration duration);
    CompletableFuture<Boolean> banGroup(String groupId, boolean b);
    CompletableFuture<Boolean> setGroupAdmin(String groupId, String userId, boolean admin);
    CompletableFuture<Boolean> leaveGroup(String groupId);
    CompletableFuture<Boolean> setFriendAddRequest(String flag, boolean approve, String remark);
    CompletableFuture<Boolean> setGroupAddRequest(String flag, boolean approve, String denyReason);
    CompletableFuture<LoginInfo> getLoginInfo();
    CompletableFuture<Stranger> getStrangerInfo(String userId);
    CompletableFuture<Set<Friend>> getFriendList(boolean noCache);
    CompletableFuture<GroupInfo> getGroupInfo(String groupId);
    CompletableFuture<Set<GroupInfo>> getGroupList();
    CompletableFuture<GroupMemberInfo> getGroupMemberInfo(String groupId, String userId);
    CompletableFuture<Set<GroupMemberInfo>> getGroupMemberList(String groupId);
    CompletableFuture<Boolean> cleanCache();
}
