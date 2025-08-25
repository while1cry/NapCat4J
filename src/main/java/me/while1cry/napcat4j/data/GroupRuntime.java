package me.while1cry.napcat4j.data;

import me.while1cry.napcat4j.entity.group.Group;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public interface GroupRuntime extends Closeable {

    void loadAll();

    void load(String groupId);

    void put(Group group);

    void remove(String groupId);

    void removeAll();

    @Nullable Group getGroup();

    CompletableFuture<Group> getOrLoadGroup(String groupId);

    Duration getExpiration();

    void setExpiration(Duration expiration);

    @Override
    default void close() {
        removeAll();
    }
}
