package edu.ntnu.stud.config;

import edu.ntnu.stud.model.base.Bookmark;
import edu.ntnu.stud.model.base.Category;
import edu.ntnu.stud.model.base.Listing;
import edu.ntnu.stud.model.base.Reservation;
import edu.ntnu.stud.model.base.SubCategory;
import edu.ntnu.stud.model.base.User;
import edu.ntnu.stud.model.base.UserHistory;
import edu.ntnu.stud.model.request.CategoryRequest;
import edu.ntnu.stud.model.response.SubCategoryRequest;
import edu.ntnu.stud.repo.BookmarkRepo;
import edu.ntnu.stud.repo.CategoryRepo;
import edu.ntnu.stud.repo.ListingRepo;
import edu.ntnu.stud.repo.ReservationRepo;
import edu.ntnu.stud.repo.SubCategoryRepo;
import edu.ntnu.stud.repo.UserHistoryRepo;
import edu.ntnu.stud.repo.UserRepo;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is used to populate the database with test data when
 * the application starts in the "test" profile.
 */
@Component
@Profile("test-e2e")
public class TestDataInitializer implements CommandLineRunner {

  @Autowired
  private UserRepo userRepo;
  @Autowired
  private CategoryRepo categoryRepo;
  @Autowired
  private SubCategoryRepo subCategoryRepo;
  @Autowired
  private ListingRepo listingRepo;
  @Autowired
  private BookmarkRepo bookmarkRepo;
  @Autowired
  private UserHistoryRepo userHistoryRepo;
  @Autowired
  private ReservationRepo reservationRepo;

  @Override
  public void run(String... args) throws Exception {
    // Create test users
    for (int i = 0; i < 3; i++) {
      User user = new User();
      user.setUsername("testuser" + i);
      user.setPassword("password" + i);
      userRepo.addUser(user);
    }
    User user = new User();
    user.setUsername("testuserAdmin");
    user.setPassword("passwordAdmin");
    user.setAdmin(true);
    userRepo.addUser(user);

    // Create test categories and subcategories
    for (int i = 0; i < 3; i++) {
      categoryRepo.addCategory(new CategoryRequest(
          "Test Category " + i,
          "Test Description " + i,
          "activity"));
    }
    List<Category> categories = categoryRepo.getAllCategories();
    for (int i = 0; i < 6; i++) {
      subCategoryRepo.addSubCategory(new SubCategoryRequest(
          "Test SubCategory " + i,
          "Test Description " + i,
          categories.get(i % categories.size()).getId()));
    }

    // Create test listings
    List<User> users = userRepo.getAllUsers();
    for (int i = 0; i < 3; i++) {
      SubCategory subCategoryForCategory = subCategoryRepo.getSubCategoriesByCategoryId(
          categories.get(i % categories.size()).getId()).get(0);
      listingRepo.saveListing(new Listing(
          "Test Listing " + i,
          100.0 + (long) i,
          "Test Description " + i,
          categories.get(i % categories.size()).getId(),
          subCategoryForCategory.getId(),
          10.395467071105747,
          63.42245621159982,
          users.get(i % users.size()).getId()));
    }
    List<Listing> listing = listingRepo.getAllListings();

    // Create test bookmarks
    for (int i = 0; i < 2; i++) {
      Bookmark bookmark = new Bookmark();
      bookmark.setUserId(users.get(i % users.size()).getId());
      bookmark.setListingId(listing.get(i % listing.size()).getUuid());
      bookmarkRepo.addBookmark(bookmark);
    }

    // Create test user history
    for (int i = 0; i < 2; i++) {
      UserHistory userHistory = new UserHistory();
      userHistory.setUserId(users.get(i % users.size()).getId());
      userHistory.setListingId(listing.get(i % listing.size()).getUuid());
      userHistoryRepo.addUserHistory(userHistory);

      userHistory = new UserHistory();
      userHistory.setUserId(users.get((i + 1) % users.size()).getId());
      userHistory.setListingId(listing.get((i + 1) % listing.size()).getUuid());
      userHistoryRepo.addUserHistory(userHistory);
    }

    // Create test reservation
    for (int i = 0; i < 1; i++) {
      reservationRepo.addReservation(new Reservation(
          0L,
          listing.get(i % listing.size()).getUuid(),
          users.get(i % users.size()).getId(),
          Timestamp.valueOf("2023-10-01 10:00:00" + i)));
    }
  }
}
