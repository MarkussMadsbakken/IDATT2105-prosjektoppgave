package edu.ntnu.stud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .setHandshakeHandler(new WebSocketHandshakeHandler())
        .addInterceptors(applicationContext.getBean(WebSocketAuthHandshakeInterceptor.class));
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setUserDestinationPrefix("/user");
  }
}