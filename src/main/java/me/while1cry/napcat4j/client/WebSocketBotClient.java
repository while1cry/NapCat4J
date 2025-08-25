package me.while1cry.napcat4j.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.while1cry.napcat4j.NapCat4J;
import me.while1cry.napcat4j.event.EventCaller;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class WebSocketBotClient extends BotClient implements EventCaller {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final Logger logger;
    private final String token;
    private final Duration timeout;
    private final Client client;

    public WebSocketBotClient(NapCat4J core) {
        super(core);
        this.logger = core.getLogger();
        this.token = core.getToken();
        this.timeout = core.getTimeout();
        this.client = new Client(this);
    }

    @Override
    public void connect() {
        client.connect();
    }

    @Override
    public boolean isConnected() {
        return client.isOpen();
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    protected CompletableFuture<ObjectNode> send(String action, ObjectNode params) {
        return client.sendWithEcho(action, params);
    }

    private static final class Client extends WebSocketClient {

        private final WebSocketBotClient bot;
        private final Logger logger;
        private final ConcurrentHashMap<UUID, CompletableFuture<ObjectNode>> pending = new ConcurrentHashMap<>();

        private Client(WebSocketBotClient bot) {
            super(bot.uri);
            this.bot = bot;
            this.logger = bot.logger;
        }

        @Override
        public void connect() {
            if (bot.token != null)
                addHeader("Authorization", "Bearer " + bot.token);
            super.connect();
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) {
            logger.info("Connection opened.");
        }

        @Override
        public void onMessage(String s) {
            try {
                ObjectNode json = (ObjectNode) mapper.readTree(s);
                if (json.has("echo")) {
                    UUID echo = UUID.fromString(json.get("echo").asText());
                    CompletableFuture<ObjectNode> future = pending.remove(echo);
                    if (future != null) {
                        future.complete(json);
                    }
                } else {
                    bot.eventHandle(s);
                }
            } catch (Exception e) {
                logger.error("Error parsing message", e);
            }
        }

        @Override
        public void onMessage(ByteBuffer bytes) {
            String message = StandardCharsets.UTF_8.decode(bytes).toString();
            onMessage(message);
        }

        @Override
        public void onClose(int i, String s, boolean b) {
            logger.info("Disconnected from {}", getURI());
        }

        @Override
        public void onError(Exception e) {
            logger.error("Web socket client error", e);
        }

        private CompletableFuture<ObjectNode> sendWithEcho(
                String action,
                ObjectNode params
        ) {
            UUID echo = UUID.randomUUID();

            ObjectNode body = mapper.createObjectNode()
                    .put("action", action)
                    .put("echo", echo.toString())
                    .set("params", params);

            CompletableFuture<ObjectNode> futureResponse = new CompletableFuture<>();
            pending.put(echo, futureResponse);

            try {
                send(mapper.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                logger.error("Error sending action {}", action, e);
                futureResponse.completeExceptionally(e);
            }

            return futureResponse.orTimeout(bot.timeout.toMillis(), TimeUnit.MILLISECONDS);
        }
    }
}
