package edu.ntnu.stud.config;

import edu.ntnu.stud.service.JWTService;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Interceptor for WebSocket handshake requests.
 * This class is used to extract the JWT token from the request and validate it.
 * If the token is valid, the user ID is added to the attributes map.
 */
@Configuration
public class WebSocketAuthHandshakeInterceptor implements HandshakeInterceptor {

  private final JWTService jwtService;

  public WebSocketAuthHandshakeInterceptor(JWTService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

    String query = request.getURI().getQuery();
    String token = null;

    // Extract the token from the query parameters
    if (StringUtils.hasText(query)) {
      for (String param : query.split("&")) {
        String[] pair = param.split("=");
        if (pair.length == 2 && "token".equals(pair[0])) {
          token = pair[1];
          break;
        }
      }
    }

    // If the token is not found in the query parameters, return false
    if (!StringUtils.hasText(token)) {
      return false;
    }

    // Extract the user ID from the token and add it to the attributes map
    try {
      Long userId = jwtService.extractUserId(token);
      System.out.println("UserId: " + userId);
      attributes.put("user", userId);
      return true;

    } catch (Exception e) {
      System.out.println("Token: " + token);
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception exception) {
  }
}