package me.while1cry.napcat4j;

import lombok.Getter;
import lombok.SneakyThrows;
import me.while1cry.napcat4j.client.BotClient;
import me.while1cry.napcat4j.client.ClientType;
import me.while1cry.napcat4j.client.WebSocketBotClient;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.time.Duration;

public class NapCat4J implements Closeable {

    private final Logger logger;
    @Getter
    private final String address;
    @Getter
    private final ClientType clientType;
    @Getter
    @Nullable
    private final String token;
    @Getter
    private final BotClient botClient;

    @SneakyThrows
    public NapCat4J(String address, ClientType clientType, @Nullable String token, Duration timeout) {
        this.logger = LoggerFactory.getLogger("NapCat4J");
        this.address = address;
        this.clientType = clientType;
        this.token = token;

        this.botClient = switch (clientType) {
            case WEBSOCKET_CLIENT -> new WebSocketBotClient(logger, address, token, timeout);
        };
    }

    public void connect() {
        botClient.createConnection();
    }

    @Override
    public void close() {
        botClient.disconnect();
    }
}
