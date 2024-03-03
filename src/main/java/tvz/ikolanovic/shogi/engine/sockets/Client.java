package tvz.ikolanovic.shogi.engine.sockets;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class Client
{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientID;

    public Client(Socket socket, String clientID)
    {
        this.socket = socket;
        this.clientID = clientID;
        try
        {
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e)
        {
            this.closeAllStreams();
        }
    }

    public void sendMessage(String newMessage)
    {
        try
        {
//            while (this.socket.isConnected())
//            {
                this.bufferedWriter.write(this.clientID + ": " + newMessage);
                this.bufferedWriter.newLine();
                this.bufferedWriter.flush();
           // }
        } catch (IOException e)
        {
            this.closeAllStreams();
        }
    }

    public void listenForMessage()
    {
        new Thread(() -> {
            String incomingMessage;

            while (this.socket.isConnected())
            {
                try
                {
                    incomingMessage = this.bufferedReader.readLine();
                    System.out.println(incomingMessage);
                } catch (IOException e)
                {
                    this.closeAllStreams();
                }
            }
        }).start();
    }

    public void closeAllStreams()
    {
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
