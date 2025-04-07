package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service class for managing WebSocket communication. Used to send websocket
 * messages to
 * specific users.
 */
@Service
public class WebsocketService {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  /**
   * Pushes a chat message to a specific user.
   *
   * @param userId  the ID of the user to send the message to
   * @param message the message to be sent
   */
  public void pushChatMessage(long userId, Message message) {
    messagingTemplate.convertAndSendToUser(Long.toString(userId), "/queue/messages", message);
  }

  /**
   * Pushes a notification to a specific user.
   *
   * @param userId  the ID of the user to send the notification to
   * @param message the notification message to be sent
   */
  public void pushNotification(long userId, Notification message) {
    messagingTemplate.convertAndSendToUser(Long.toString(userId), "/queue/notifications", message);
  }
}
