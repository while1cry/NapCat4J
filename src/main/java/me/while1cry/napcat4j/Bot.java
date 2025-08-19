package me.while1cry.napcat4j;

public interface Bot extends OneBotAPI, GoCQHTTPAPI, NapCatAPI {

    void createConnection();

    boolean isConnected();

    void disconnect();

    void close();
}
