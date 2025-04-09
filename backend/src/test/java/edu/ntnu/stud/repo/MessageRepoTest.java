package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Listing;
import edu.ntnu.stud.model.Message;
import edu.ntnu.stud.model.MessageRequest;
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
public class MessageRepoTest {

  @Autowired
  private MessageRepo messageRepo;

  @Autowired
  private ChatRepo chatRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ListingRepo listingRepo;

  private long chatId;
  private final long senderId = 1L;

  @BeforeEach
  public void setUp() {
    Listing listing = new Listing("test listing for chatRepoTest", 100.0,
        "test listing for chatRepoTest", 1, 1, 0, 0, 1L);
    listingRepo.saveListing(listing);

    chatId = chatRepo.createChat(1L, 2L, listing.getUuid());
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM message");
    jdbcTemplate.execute("DELETE FROM chat");
  }

  @Test
  public void testAddMessage() {
      MessageRequest messageRequest = new MessageRequest("Test message", chatId, senderId);

      int rowsAffected = messageRepo.addMessage(messageRequest);

      assertThat(rowsAffected).isEqualTo(1);

      List<Message> messages = messageRepo.getMessagesByChatId(chatId);
      assertThat(messages).hasSize(1);
      assertThat(messages.getFirst().getMessage()).isEqualTo("Test message");
  }

  @Test
  public void testGetMessagesByChatId() {
      messageRepo.addMessage(new MessageRequest("First message", chatId, senderId));
      messageRepo.addMessage(new MessageRequest("Second message", chatId, senderId));

      List<Message> messages = messageRepo.getMessagesByChatId(chatId);

      assertThat(messages).hasSize(2);
      assertThat(messages).extracting(Message::getMessage).containsExactly("First message", "Second message");
  }
}