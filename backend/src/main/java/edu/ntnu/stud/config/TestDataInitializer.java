package edu.ntnu.stud.config;

import edu.ntnu.stud.model.base.User;
import edu.ntnu.stud.model.request.CategoryRequest;
import edu.ntnu.stud.model.response.SubCategoryRequest;
import edu.ntnu.stud.repo.CategoryRepo;
import edu.ntnu.stud.repo.SubCategoryRepo;
import edu.ntnu.stud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * This class is used to populate the database with test data when
 * the application starts in the "test" profile.
 */
@Component
@Profile("test")
public class TestDataInitializer implements CommandLineRunner {

  @Autowired
  private UserRepo userRepo;
  @Autowired
  private CategoryRepo categoryRepo;
  @Autowired
  private SubCategoryRepo subCategoryRepo;

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
          "icon" + i + ".png"
      ));
    }
    for (int i = 0; i < 3; i++) {
      subCategoryRepo.addSubCategory(new SubCategoryRequest(
          "Test SubCategory " + i,
          "Test Description " + i,
          "icon" + i + ".png",
          1 // Assuming the first category is the parent category
      ));
    }
  }
}
