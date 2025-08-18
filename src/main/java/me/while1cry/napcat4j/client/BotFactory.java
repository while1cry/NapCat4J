package me.while1cry.napcat4j.client;

import me.while1cry.napcat4j.OneBotAPI;
import me.while1cry.napcat4j.enums.ConnectionMode;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;

public class BotFactory {

    public static OneBotAPI createOnebot(ConnectionMode connectionMode, String address, @Nullable String token) throws URISyntaxException {
        return switch (connectionMode) {
            case WEBSOCKET -> new BotWebSocketClient(address, token);
            default -> throw new IllegalArgumentException("Not implemented yet!");
        };
    }
}
