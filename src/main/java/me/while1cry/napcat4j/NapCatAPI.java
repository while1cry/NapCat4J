package me.while1cry.napcat4j;

import me.while1cry.napcat4j.entity.user.OnlineStatus;

import java.util.concurrent.CompletableFuture;

public interface NapCatAPI {

    CompletableFuture<Boolean> setGroupSign(String groupId);

    CompletableFuture<Boolean> setOnlineStatus(OnlineStatus status);
}
