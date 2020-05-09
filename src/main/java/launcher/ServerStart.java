package launcher;

import player.Player;
import player.PlayerGroup;
import socket.ServersSocketImplementation;

import java.io.IOException;

public class ServerStart {

    public static void main(String[] args) throws IOException {
        Player server = new Player(ServersSocketImplementation.createSocketImplementation(), PlayerGroup.SUPPORTER);
        Thread clientThread = new Thread(server);
        clientThread.start();
    }

}
