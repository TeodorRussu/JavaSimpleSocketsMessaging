package socket;

import config.Config;

import java.io.IOException;
import java.net.Socket;

/**
 * socket implementation for Initiator Player
 */
public class ClientSocketImplementation implements PlayerSocketInterface {

    private Socket socket;

    public static PlayerSocketInterface createSocketImplementation() throws IOException {
        PlayerSocketInterface socketImplementation = new ClientSocketImplementation();
        socketImplementation.initConnection();
        return socketImplementation;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public PlayerSocketInterface initConnection() throws IOException {
        socket = new Socket(Config.HOST, Config.PORT);
        return this;
    }

}