package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.base.Chat;
import edu.ntnu.stud.model.base.Listing;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


/**
 * Test class for ChatRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ChatRepoTest {

  @Autowired
  private ChatRepo chatRepo;

  @Autowired
  private ListingRepo listingRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final long buyerId = 1L;
  private final long sellerId = 2L;
  private String listingId;

  /**
   * Sets up the test environment by creating a test listing.
   */
  @BeforeEach
  public void setUp() {
    Listing listing = new Listing("test listing for chatRepoTest", 100.0,
        "test listing for chatRepoTest", 1, 1, 0, 0, 1L);

    listingRepo.saveListing(listing);

    listingId = listing.getUuid();
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM chat");
    jdbcTemplate.execute("DELETE FROM listings");
  }

  @Test
  public void testCreateChat() {
    long rowsAffected = chatRepo.createChat(buyerId, sellerId, listingId);

    assertThat(rowsAffected).isEqualTo(1);
    Optional<Long> chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId);
    assertThat(chatId).isPresent();
  }

  @Test
  public void testChatExists() {
    chatRepo.createChat(buyerId, sellerId, listingId);
    Long chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId).orElse(null);
    boolean exists = chatRepo.chatExists(chatId);
    assertThat(exists).isTrue();
  }

  @Test
  public void testChatAlreadyExists() {
    chatRepo.createChat(buyerId, sellerId, listingId);

    Optional<Long> chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId);
    assertThat(chatId).isPresent();
  }

  @Test
  public void testDeleteChat() {
    chatRepo.createChat(buyerId, sellerId, listingId);
    Long chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId).orElse(null);

    boolean deleted = chatRepo.deleteChat(String.valueOf(chatId));
    assertThat(deleted).isTrue();

    boolean exists = chatRepo.chatExists(chatId);
    assertThat(exists).isFalse();
  }

  @Test
  public void testGetAllUserChats() {
    chatRepo.createChat(buyerId, sellerId, listingId);

    List<Chat> chats = chatRepo.getAllUserChats(buyerId);
    assertThat(chats).hasSize(1);
    assertThat(chats.getFirst().getListingId()).isEqualTo(listingId);
  }

  @Test
  public void testGetChatById() {
    chatRepo.createChat(buyerId, sellerId, listingId);
    Long chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId).orElse(null);

    Chat chat = chatRepo.getChatById(chatId);
    assertThat(chat).isNotNull();
    assertThat(chat.getListingId()).isEqualTo(listingId);
  }

  @Test
  public void testUserIsParticipant() {
    chatRepo.createChat(buyerId, sellerId, listingId);
    Long chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId).orElse(null);

    boolean isParticipant = chatRepo.userIsParticipant(buyerId, chatId);
    assertThat(isParticipant).isTrue();
  }

  @Test
  public void testGetOtherParticipantId() {
    chatRepo.createChat(buyerId, sellerId, listingId);
    Long chatId = chatRepo.chatAlreadyExists(buyerId, sellerId, listingId).orElse(null);

    Long otherParticipantId = chatRepo.getOtherParticipantId(buyerId, chatId);
    assertThat(otherParticipantId).isEqualTo(sellerId);
  }
}