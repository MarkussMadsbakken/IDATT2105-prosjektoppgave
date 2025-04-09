package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Notification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NotificationRepoTest {

  @Autowired
  private NotificationRepo notificationRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final long userId = 1L;

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM notifications");
  }

  @Test
  public void testAddNotification() {
    Notification notification = new Notification();
    notification.setUserId(userId);
    notification.setMessage("Test notification");
    notification.setLink("http://example.com");

    int rowsAffected = notificationRepo.addNotification(notification);

    assertThat(rowsAffected).isEqualTo(1);

    List<Notification> notifications = notificationRepo.getNotificationsByUserId(userId);
    assertThat(notifications).hasSize(1);
    assertThat(notifications.getFirst().getMessage()).isEqualTo("Test notification");
    assertThat(notifications.getFirst().getLink()).isEqualTo("http://example.com");
  }

  @Test
  public void testGetNotificationsByUserId() {
    Notification notification1 = new Notification();
    notification1.setUserId(userId);
    notification1.setMessage("First notification");
    notification1.setLink("http://example.com/1");

    Notification notification2 = new Notification();
    notification2.setUserId(userId);
    notification2.setMessage("Second notification");
    notification2.setLink("http://example.com/2");

    notificationRepo.addNotification(notification1);
    notificationRepo.addNotification(notification2);

    List<Notification> notifications = notificationRepo.getNotificationsByUserId(userId);

    assertThat(notifications).hasSize(2);
    assertThat(notifications).extracting(Notification::getMessage)
        .containsExactly("First notification", "Second notification");
  }

  @Test
  public void testMarkNotificationAsRead() {
    Notification notification = new Notification();
    notification.setUserId(userId);
    notification.setMessage("Unread notification");
    notification.setLink("http://example.com");

    notificationRepo.addNotification(notification);

    List<Notification> notifications = notificationRepo.getNotificationsByUserId(userId);
    long notificationId = notifications.getFirst().getId();

    notificationRepo.markNotificationAsRead(notificationId);

    Notification updatedNotification = notificationRepo.getNotificationById(notificationId);
    assertThat(updatedNotification.isRead()).isTrue();
  }

  @Test
  public void testGetAllNotifications() {
      Notification notification1 = new Notification();
      notification1.setUserId(userId);
      notification1.setMessage("First notification");
      notification1.setLink("http://example.com/1");

      Notification notification2 = new Notification();
      notification2.setUserId(userId);
      notification2.setMessage("Second notification");
      notification2.setLink("http://example.com/2");

      notificationRepo.addNotification(notification1);
      notificationRepo.addNotification(notification2);

      List<Notification> notifications = notificationRepo.getAllNotifications();

      assertThat(notifications).hasSize(2);
      assertThat(notifications).extracting(Notification::getMessage)
          .containsExactlyInAnyOrder("First notification", "Second notification");
      assertThat(notifications).extracting(Notification::getLink)
          .containsExactlyInAnyOrder("http://example.com/1", "http://example.com/2");
  }
}