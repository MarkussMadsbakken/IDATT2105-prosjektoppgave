package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Chat;
import edu.ntnu.stud.model.CreateChatRequest;
import edu.ntnu.stud.model.CreateChatResponse;
import edu.ntnu.stud.model.DefaultResponse;
import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;
import edu.ntnu.stud.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling chat-related requests.
 * This class provides endpoints for creating chats, retrieving chat messages,
 * and adding messages to chats.
 */
@Tag(name = "Chat", 
    description = "Endpoints for managing chat messages and conversations")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

  @Autowired
  private ChatService chatService;

  /**
   * Get all chats for the user.
   *
   * @param token the JWT token of the user
   * @return a list of chats for the user
   */
  @Operation(summary = "Get all chats for the user", 
      description = "Retrieve all chats associated with the authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved chats"),
      @ApiResponse(responseCode = "204", description = "No chats found for the user")
  })
  @GetMapping
  public ResponseEntity<List<Chat>> getAllChats(@RequestHeader("Authorization") String token) {

    // Get chats for the user from the token
    List<Chat> chats = chatService.getAllChatsByToken(token);

    // Return the list of chats
    if (chats.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(chats);
  }

  /**
   * Get a specific chat by its ID.
   *
   * @param chatId the ID of the chat
   * @param token the JWT token of the user
   * @return the chat with the specified ID
   */
  @Operation(summary = "Get a specific chat by ID", 
      description = "Retrieve a chat by its unique ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the chat"),
      @ApiResponse(responseCode = "204", description = "Chat not found")
  })
  @GetMapping("/{chatId}")
  public ResponseEntity<Chat> getChat(@PathVariable long chatId,
      @RequestHeader("Authorization") String token) {
    Chat chat = chatService.getChatById(chatId, token);
    if (chat == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(chat);
  }

  /**
   * Get all messages for a specific chat.
   *
   * @param chatId the ID of the chat
   * @param token  the JWT token of the user
   * @return a list of messages for the chat
   */
  @Operation(summary = "Get all messages for a chat", 
      description = "Retrieve all messages for a specific chat.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved messages"),
      @ApiResponse(responseCode = "204", description = "No messages found for the chat")
  })
  @GetMapping("/{chatId}/messages")
  public ResponseEntity<List<Message>> getChatMessages(@PathVariable long chatId,
      @RequestHeader("Authorization") String token) {
    List<Message> messages = chatService.getAllMessagesByChatId(chatId, token);
    if (messages.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(messages);
  }

  /**
   * Get the latest message for a specific chat.
   *
   * @param chatId the ID of the chat
   * @param token  the JWT token of the user
   * @return the latest message for the chat
   */
  @Operation(summary = "Get the latest message for a chat", 
      description = "Retrieve the most recent message for a specific chat.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the latest message"),
      @ApiResponse(responseCode = "204", description = "No messages found for the chat")
  })
  @GetMapping("/{chatId}/messages/latest")
  public ResponseEntity<Message> getLatestMessage(@PathVariable long chatId,
      @RequestHeader("Authorization") String token) {
    Message message = chatService.getLatestMessage(chatId, token);
    if (message == null) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(message);
  }

  /**
   * Create a new chat.
   *
   * @param token the JWT token of the user
   * @param req   the request body containing the details of the chat to be created
   * @return a response entity containing the ID of the created chat
   */
  @Operation(summary = "Create a new chat", 
      description = "Create a new chat with the specified details.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully created the chat")
  })
  @PostMapping
  public ResponseEntity<CreateChatResponse> createChat(@RequestHeader("Authorization") String token,
      @RequestBody CreateChatRequest req) {
    Long chatId = chatService.createChat(req, token);
    return ResponseEntity.ok(new CreateChatResponse(chatId));
  }

  /**
   * Add a message to a chat.
   *
   * @param chatId  the ID of the chat
   * @param token   the JWT token of the user
   * @param message the message to be added to the chat
   * @return a response entity indicating the success or failure of the operation
   */
  @Operation(summary = "Add a message to a chat", 
      description = "Add a new message to a specific chat.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Message sent successfully"),
      @ApiResponse(responseCode = "400", description = "Failed to send message")
  })
  @PostMapping("/{chatId}/messages")
  public ResponseEntity<DefaultResponse> addMessageToChat(@PathVariable long chatId,
      @RequestHeader("Authorization") String token, @RequestBody MessageRequest message) {
    message.setChatId(chatId);
    if (chatService.addMessageToChat(message, token)) {
      return ResponseEntity.ok(new DefaultResponse("Message sent successfully", "messageSent"));
    } else {
      return ResponseEntity.badRequest().body(
          new DefaultResponse("Failed to send message", "messageNotSent"));
    }
  }

}
