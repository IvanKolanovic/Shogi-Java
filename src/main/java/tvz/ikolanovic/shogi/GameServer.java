package tvz.ikolanovic.shogi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tvz.ikolanovic.shogi.engine.sockets.ClientHandlerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class GameServer implements tvz.ikolanovic.shogi.engine.sockets.interfaces.GameServer
{
    private ServerSocket serverSocket;

    @Override
    public void startServer()
    {
        try (Socket socket = serverSocket.accept())
        {
            while (!serverSocket.isClosed())
            {
                log.info("CONNECTION === New client connected");
                ClientHandlerImpl clientHandler = new ClientHandlerImpl(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e)
        {
            log.error("SOCKET ERROR === Closing server socket. REASON: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException
    {
        new GameServer(new ServerSocket(1234)).startServer();
    }
}
