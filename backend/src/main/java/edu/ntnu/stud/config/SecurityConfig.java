package edu.ntnu.stud.config;

import edu.ntnu.stud.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class configures the security settings for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  /**
   * Configures the security filter chain for the application.
   * This method sets up CORS, CSRF, authorization rules, session management,
   * HTTP headers, and the JWT filter.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(request -> request
            .requestMatchers(
                "/api/auth/login",
                "/api/auth/register",
                "/ws/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-ui/index.html", // Allow access to Swagger UI
                "/v3/api-docs/**")        // Allow access to OpenAPI docs
            .permitAll()
            .requestMatchers(
                HttpMethod.GET,
                "/api-docs",
                "/api/notifications",
                "/api/notifications/**",
                "/api/bookmark/**",
                "/api/categories",
                "/api/categories/**",
                "/api/listing",
                    "/api/reservation",
                    "/api/reservation/**",
                "/api/listing/**",
                "/api/reservation",
                "/api/reservation/**",
                "/api/user",
                "/api/user/**",
                "/api/search")
            .permitAll()
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * Creates an AuthenticationProvider bean that uses a DaoAuthenticationProvider
   * with a BCryptPasswordEncoder for password encoding and a UserDetailsService.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    provider.setUserDetailsService(userDetailsService);

    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();

  }

}