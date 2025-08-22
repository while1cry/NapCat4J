package me.while1cry.napcat4j.client;

import me.while1cry.napcat4j.entity.status.OnlineStatus;

import java.util.concurrent.CompletableFuture;

public interface NapCatAPI {

    CompletableFuture<Boolean> setGroupSign(String groupId);

    CompletableFuture<Boolean> setOnlineStatus(OnlineStatus status);
}
