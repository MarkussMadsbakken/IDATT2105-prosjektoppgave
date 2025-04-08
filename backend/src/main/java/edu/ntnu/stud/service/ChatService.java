package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Chat;
import edu.ntnu.stud.model.CreateChatRequest;
import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;
import edu.ntnu.stud.repo.ChatRepo;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for handling chat-related operations, such as creating,
 * deleting and adding messages to chats.
 */
@Service
public class ChatService {
  @Autowired
  private JWTService jwtService;

  @Autowired
  private ChatRepo chatRepo;

  @Autowired
  private MessageService messageService;

  @Autowired
  private ListingService listingService;

  @Autowired
  private WebsocketService websocketService;

  /**
   * Creates a new chat with a buyer, seller, and listing.
   * If the chat already exists, it returns the existing chat ID.
   * If the chat does not exist, it creates a new chat and returns the new chat
   * ID.
   *
   * @param request the request object containing buyerId, sellerId, and
   *                listingId
   * @return the ID of the chat created or the existing chat ID
   */
  public Long createChat(CreateChatRequest request, String token) {
    // Get seller and buyer
    // Potentially a bad way to check for buyer but it works
    request.setBuyerId(jwtService.extractUserId(token.substring(7)));
    request.setSellerId(listingService.getListingByUuid(request.getListingId()).getOwnerId());

    // Check if the buyer and seller are the same
    if (request.getBuyerId() == request.getSellerId()) {
      return -1L;
    }

    // If the chat already exists, return false
    Optional<Long> chatId = chatRepo.chatAlreadyExists(
        request.getBuyerId(),
        request.getSellerId(),
        request.getListingId());

    if (chatId.isPresent()) {
      return chatId.get();
    }

    // Create a new chat
    return chatRepo.createChat(request.getBuyerId(), request.getSellerId(), request.getListingId());
  }

  /**
   * Verifies and adds a message to a chat.
   *
   * @param message the message to be added
   * @param token   the JWT token of the user
   * @return true if the message was added successfully, false otherwise
   */
  public boolean addMessageToChat(MessageRequest message, String token) {
    // Extract the user ID from the token
    message.setSenderId(jwtService.extractUserId(token.substring(7)));

    if (!chatRepo.chatExists(message.getChatId())) {
      return false;
    }

    // Check if the user is authorized to send a message in this chat
    if (!chatRepo.userIsParticipant(message.getSenderId(), message.getChatId())) {
      // Throw?
      return false;
    }

    // The message should be valid, so we can add it to the chat
    int messageId = messageService.addMessage(message);

    // Check if the message was added successfully
    if (messageId == 0) {
      return false;
    }

    // Send the message to the other participant using WebSocket
    websocketService.pushChatMessage(
        chatRepo.getOtherParticipantId(message.getSenderId(), message.getChatId()),
        new Message(messageId,
            message.getChatId(),
            message.getSenderId(),
            message.getMessage(),
            new Timestamp(System.currentTimeMillis())));

    return true;
  }

  /**
   * Gets all chats for a given user ID.
   *
   * @param userId the ID of the user
   * @return a list of chats for the user
   */
  public List<Chat> getAllChatsByUserId(long userId) {
    return chatRepo.getAllUserChats(userId);
  }

  /**
   * Gets all chats for a given user ID from the JWT token.
   *
   * @param token the JWT token of the user
   * @return a list of chats for the user
   */
  public List<Chat> getAllChatsByToken(String token) {
    // Extract the user ID from the token
    long userId = jwtService.extractUserId(token.substring(7));

    // Get all chats for the user
    return chatRepo.getAllUserChats(userId);
  }

  /**
   * Gets a chat by its ID.
   *
   * @param chatId the ID of the chat
   * @param token  the JWT token of the user
   * @return the chat object if found and the user is authorized, null otherwise
   */
  public Chat getChatById(long chatId, String token) {
    // Extract the user ID from the token
    long userId = jwtService.extractUserId(token.substring(7));

    // Check if the user is authorized to view this chat
    if (!chatRepo.userIsParticipant(userId, chatId)) {
      return null;
    }

    // The user is authorized to view the chat, so we can return it
    return chatRepo.getChatById(chatId);
  }

  /**
   * Gets all messages for a given chat ID.
   *
   * @param chatId the ID of the chat
   * @param token  the JWT token of the user
   * @return a list of messages for the chat
   */
  public List<Message> getAllMessagesByChatId(long chatId, String token) {
    // Extract the user ID from the token
    long userId = jwtService.extractUserId(token.substring(7));

    // Check if the user is authorized to view this chat
    if (!chatRepo.userIsParticipant(userId, chatId)) {
      return List.of();
    }

    // The user is authorized to view the chat, so we can return the messages
    return messageService.getAllMessagesByChatId(chatId);
  }

  /**
   * Gets the latest message for a given chat ID.
   *
   * @param chatId the ID of the chat
   * @param token the JWT token of the user
   * @return the latest message for the chat, or null if not found or unauthorized
   */
  public Message getLatestMessage(long chatId, String token) {
    // Extract the user ID from the token
    long userId = jwtService.extractUserId(token.substring(7));

    // Check if the user is authorized to view this chat
    if (!chatRepo.userIsParticipant(userId, chatId)) {
      return null;
    }

    // The user is authorized to view the chat, so we can return the latest message
    return messageService.getAllMessagesByChatId(chatId).getLast();
  }

}
