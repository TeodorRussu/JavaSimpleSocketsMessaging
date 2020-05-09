package player;

import config.Config;
import lombok.Data;
import socket.PlayerSocketInterface;

import java.io.IOException;
import java.util.logging.Logger;

@Data
public class Player implements Runnable {

    Logger logger = Logger.getGlobal();
    private PlayerSocketInterface socket;
    private PlayerGroup playerGroup;

    private int receivedMessagesCounter;
    private int sentMessages;
    private boolean isActive;

    public Player(PlayerSocketInterface socket, PlayerGroup playerGroup) {
        this.socket = socket;
        this.playerGroup = playerGroup;
        this.isActive = true;
    }

    /**
     * When the player instances will SUPPORTER will do nothing, unless INITIATOR will not post the first message Once
     * INITIATOR posts the fisrt message, messaging ping pong will begin.
     * <p>
     * When INITIATOR will reach the limit of sent/received messages, it will post the flag message {@link
     * Config#STOP_PLAYERS_MESSAGE} to the SUPPORTER, and both threads will stop
     */
    @Override
    public void run() {
        if (sentMessages == 0 && playerGroup.equals(PlayerGroup.INITIATOR)) {
            try {
                sendFirstMessage();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }

        while (isActive) {
            try {
                String receivedMessage = socket.acceptMessage();
                if (receivedMessage != null && receivedMessage.equals(Config.STOP_PLAYERS_MESSAGE)) {
                    isActive = false;
                } else if (receivedMessage != null) {
                    receivedMessagesCounter++;
                    logger.info(playerGroup.toString() + " received: " + receivedMessage);
                    if (playerGroup.equals(PlayerGroup.INITIATOR) && sentMessages == Config.MESSAGES_LIMIT
                        && receivedMessagesCounter == Config.MESSAGES_LIMIT) {
                        isActive = false;
                        socket.sendMessage(Config.STOP_PLAYERS_MESSAGE);
                        logger.info(playerGroup.toString() + " sent: " + Config.STOP_PLAYERS_MESSAGE);
                    } else {
                        String replyMessage = receivedMessage + sentMessages;
                        logger.info(playerGroup.toString() + " sent: " + replyMessage);
                        socket.sendMessage(replyMessage);
                        sentMessages++;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.warning(e.getMessage());
            }
        }
        try {
            socket.getSocket().close();
            logger.info(playerGroup.toString() + " closed the socket");
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private void sendFirstMessage() throws IOException {
        socket.sendMessage(Config.START_MESSAGE);
        logger.info(playerGroup.toString() + " sent: " + Config.START_MESSAGE);
        sentMessages++;
    }
}
