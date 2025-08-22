package me.while1cry.napcat4j.client;

public interface BotClient extends OneBotAPI, GoCQHTTPAPI, NapCatAPI {

    void createConnection();

    boolean isConnected();

    void disconnect();

    void close();
}
