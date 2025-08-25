package me.while1cry.napcat4j;

import lombok.Getter;
import me.while1cry.napcat4j.client.BotClient;
import me.while1cry.napcat4j.client.BotType;
import me.while1cry.napcat4j.client.WebSocketBotClient;
import me.while1cry.napcat4j.event.EventManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.net.URI;
import java.time.Duration;

public class NapCat4J implements Closeable {

    @Getter
    private final Logger logger;
    @Getter
    private final URI address;
    @Getter
    @Nullable
    private final String token;
    @Getter
    private final Duration timeout;
    @Getter
    private final BotType botType;
    @Getter
    private final BotClient botClient;
    @Getter
    private final EventManager eventManager;

    public NapCat4J(URI address, BotType botType, @Nullable String token, Duration timeout) {
        this.logger = LoggerFactory.getLogger("NapCat4J");
        this.address = address;
        this.token = token;
        this.timeout = timeout;
        this.botType = botType;

        this.eventManager = new EventManager(logger);
        this.botClient = switch (botType) {
            case WEBSOCKET_CLIENT -> new WebSocketBotClient(this);
        };
    }

    public NapCat4J(Logger logger, URI address, BotType botType, @Nullable String token, Duration timeout) {
        this.logger = logger;
        this.address = address;
        this.token = token;
        this.timeout = timeout;
        this.botType = botType;

        this.eventManager = new EventManager(logger);
        this.botClient = switch (botType) {
            case WEBSOCKET_CLIENT -> new WebSocketBotClient(this);
        };
    }

    public void connect() {
        botClient.connect();
    }

    @Override
    public void close() {
        botClient.close();
    }
}
