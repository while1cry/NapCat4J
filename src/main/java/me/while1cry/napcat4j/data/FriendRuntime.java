package me.while1cry.napcat4j.data;

import me.while1cry.napcat4j.entity.user.Friend;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface FriendRuntime extends Closeable {

    void loadAll();

    void load(String userId);

    void put(Friend friend);

    void remove(String userId);

    void removeAll();

    @Nullable Friend getFriend(String userId);

    CompletableFuture<Friend> getOrLoadFriend(String userId);

    Duration getExpiration();

    void setExpiration(Duration expiration);

    @Override
    default void close() {
        removeAll();
    }
}
