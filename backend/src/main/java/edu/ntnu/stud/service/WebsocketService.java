package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for managing WebSocket communication.
 * This class is used to send messages to users over WebSocket.
 */
@Service
public class WebsocketService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void pushChatMessage(long userId, Message message) {
    messagingTemplate.convertAndSendToUser(Long.toString(userId), "/queue/messages", message);
  }
}
