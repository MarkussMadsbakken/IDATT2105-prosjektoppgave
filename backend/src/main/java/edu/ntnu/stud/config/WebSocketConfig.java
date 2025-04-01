package edu.ntnu.stud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configures WebSocket settings for the application.
 * This class sets up the STOMP protocol for WebSocket communication,
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // The endpoint clients will use to connect to the WebSocket server
    // withSockJS() enables SockJS fallback options
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // Prefix for messages bound for message-handling methods
    registry.setApplicationDestinationPrefixes("/app");

    // Prefix for messages bound for the broker (to be broadcast to subscribers)
    registry.enableSimpleBroker("/topic", "/queue");

    // For user-specific messages
    registry.setUserDestinationPrefix("/user");
  }
}