package socket;


import config.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * socket implementation for Supporter Player
 */
public class ServersSocketImplementation implements PlayerSocketInterface {

    ServerSocket serverSocket;
    Socket socket;

    public static PlayerSocketInterface createSocketImplementation() throws IOException {
        PlayerSocketInterface socketImplementation = new ServersSocketImplementation();
        socketImplementation.initConnection();
        return socketImplementation;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public PlayerSocketInterface initConnection() throws IOException {
        serverSocket = new ServerSocket(Config.PORT);
        socket = serverSocket.accept();
        return this;
    }

}