package tvz.ikolanovic.shogi.engine.sockets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import tvz.ikolanovic.shogi.engine.sockets.interfaces.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ClientHandlerImpl implements Runnable, ClientHandler
{
    private static final List<ClientHandlerImpl> CONNECTED_USERS = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientID;

    public ClientHandlerImpl(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientID = bufferedReader.readLine();
            CONNECTED_USERS.add(this);
            this.sendBroadcast("SERVER: " + this.clientID + " has joined the server!");
        } catch (IOException e)
        {
            this.closeAllStreams();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run()
    {
        String clientMessage;

        while (socket.isConnected())
        {
            try
            {
                clientMessage = this.bufferedReader.readLine();
                this.sendBroadcast(clientMessage);

            } catch (IOException e)
            {
                log.error("SOCKET ERROR === " + e.getMessage());
                this.closeAllStreams();
                break;
            }
        }
    }


    @Override
    public void sendBroadcast(String message)
    {
        CONNECTED_USERS.forEach(clientHandler -> this.send(clientHandler, message));
    }


    @Override
    public void send(ClientHandlerImpl clientHandler, String message)
    {
        try
        {
            if (!clientHandler.getClientID().equals(this.clientID))
            {
                clientHandler.getBufferedWriter().write(message);
                clientHandler.getBufferedWriter().newLine();
                clientHandler.getBufferedWriter().flush();
            }
        } catch (IOException e)
        {
            log.error("SOCKET ERROR === " + e.getMessage());
            this.closeAllStreams();
        }
    }

    @Override
    public void clientDisconnected()
    {
        CONNECTED_USERS.remove(this);
        this.sendBroadcast("SERVER: " + this.clientID + " has left the server!");
    }

    @Override
    public void closeAllStreams()
    {
        this.clientDisconnected();
        try
        {
            if (this.bufferedReader != null) bufferedReader.close();
            if (this.bufferedWriter != null) bufferedWriter.close();
            if (this.socket != null) socket.close();
        } catch (IOException e)
        {
            log.error("SOCKET ERROR === " + e.getMessage());
        }
    }
}
