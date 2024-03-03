package tvz.ikolanovic.shogi.engine.sockets.interfaces;

import tvz.ikolanovic.shogi.engine.sockets.ClientHandlerImpl;

public interface ClientHandler
{
    void sendBroadcast(String message);

    void send(ClientHandlerImpl clientHandler, String message);

    void clientDisconnected();

    void closeAllStreams();
}
