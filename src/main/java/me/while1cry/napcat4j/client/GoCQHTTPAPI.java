package me.while1cry.napcat4j.client;

import java.util.concurrent.CompletableFuture;

public interface GoCQHTTPAPI {

    CompletableFuture<Boolean> deleteFriend(String userId, boolean blacklist, boolean bothDel);
}
