package socket;

import player.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Different implementations will be injected in different type of Player Class INITIATOR type Player will be handled as
 * a Client and {@link ClientSocketImplementation} will be injected in its instance; SUPPORTER type player will act as
 * Server waiting for the first message from INITIATOR. {@link ServersSocketImplementation} will be injected in this
 * type of Player instance
 */
public interface PlayerSocketInterface {

    Socket getSocket();

    default String acceptMessage() throws IOException, ClassNotFoundException {
        InputStream input = getSocket().getInputStream();
        ObjectInputStream ois = new ObjectInputStream(input);
        Message message = (Message) ois.readObject();
        String messageText = message.getText();
        return messageText;
    }

    default void sendMessage(String messageText) throws IOException {
        Message message = new Message(messageText);
        OutputStream output = getSocket().getOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(output);
        ous.writeObject(message);
    }

    PlayerSocketInterface initConnection() throws IOException;
}

