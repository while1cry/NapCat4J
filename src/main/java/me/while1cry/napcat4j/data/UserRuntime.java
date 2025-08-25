package me.while1cry.napcat4j.data;

import me.while1cry.napcat4j.entity.user.User;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface UserRuntime extends Closeable {

    void load(String userId);
    void put(User user);
    void remove(String userId);
    void removeAll();

    @Nullable User getUser(String userId);
    CompletableFuture<User> getOrLoadUser(String userId);

    Duration getExpiration();
    void setExpiration(Duration expiration);

    @Override
    default void close() {
        removeAll();
    }
}
