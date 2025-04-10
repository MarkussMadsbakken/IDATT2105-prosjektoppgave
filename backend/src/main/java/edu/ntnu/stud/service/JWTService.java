package edu.ntnu.stud.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class for handling JWT (JSON Web Token) operations.
 * This class provides methods to generate, validate, and extract information
 * from JWTs.
 */
@Service
public class JWTService {

  private String secretkey = "";

  /**
   * Constructor that initializes the secret key for signing JWTs.
   */
  public JWTService() {

    try {
      KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
      SecretKey sk = keyGen.generateKey();
      secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Generates a JWT token with the specified username, userId, and admin status.
   *
   * @param username the username to include in the token
   * @param userId   the user ID to include in the token
   * @param isAdmin  whether the user is an admin
   * @return the generated JWT token as a string
   */
  public String generateToken(String username, long userId, boolean isAdmin) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("refreshToken", "placeholder");
    claims.put("userId", userId);
    claims.put("isAdmin", isAdmin);

    return Jwts.builder()
        .claims()
        .add(claims)
        .subject(username)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 5 * 60 * 60))
        .and()
        .signWith(getKey())
        .compact();

  }

  private SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretkey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String extractUserName(String token) {
    // extract the username from jwt token
    return extractClaim(token, Claims::getSubject);
  }

  public Long extractUserId(String token) {
    return extractClaim(token, claims -> claims.get("userId", Long.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public boolean extractIsAdmin(String token) {
    return extractClaim(token, claims -> claims.get("isAdmin", Boolean.class));
  }

}