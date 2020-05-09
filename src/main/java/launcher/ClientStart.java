package launcher;

import socket.ClientSocketImplementation;
import player.Player;
import player.PlayerGroup;

import java.io.IOException;

public class ClientStart {

    public static void main(String[] args) throws IOException {
        Player client = new Player(ClientSocketImplementation.createSocketImplementation(), PlayerGroup.INITIATOR);
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

}
