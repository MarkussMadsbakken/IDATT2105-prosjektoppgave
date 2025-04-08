package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Chat;
import edu.ntnu.stud.model.Listing;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

  @BeforeEach
  public void setUp() {
    Listing listing = new Listing("test listing for bookmarkRepoTest", 100.0,
        "test listing for bookmarkRepoTest", 1, 1, 0, 0, 1L);

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
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

    assertThat(chatId).isGreaterThan(0);
    boolean exists = chatRepo.chatExists(chatId);
    assertThat(exists).isTrue();
  }

  @Test
  public void testChatExists() {
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

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
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

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
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

    Chat chat = chatRepo.getChatById(chatId);
    assertThat(chat).isNotNull();
    assertThat(chat.getListingId()).isEqualTo(listingId);
  }

  @Test
  public void testUserIsParticipant() {
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

    boolean isParticipant = chatRepo.userIsParticipant(buyerId, chatId);
    assertThat(isParticipant).isTrue();
  }

  @Test
  public void testGetOtherParticipantId() {
    long chatId = chatRepo.createChat(buyerId, sellerId, listingId);

    Long otherParticipantId = chatRepo.getOtherParticipantId(buyerId, chatId);
    assertThat(otherParticipantId).isEqualTo(sellerId);
  }
}