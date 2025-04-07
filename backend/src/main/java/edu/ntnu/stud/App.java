package edu.ntnu.stud;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application class for the Spring Boot application.
 */
@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Backend API",
        version = "1.0.0",
        description = "This is the backend API for the project."
    )
)
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  /**
   * Configures CORS settings for the application.
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:5173");
      }
    };
  }
}
