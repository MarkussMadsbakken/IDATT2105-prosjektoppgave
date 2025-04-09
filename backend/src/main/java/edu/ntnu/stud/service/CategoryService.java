package edu.ntnu.stud.service;

import edu.ntnu.stud.model.CategoryRequest;
import edu.ntnu.stud.model.base.Category;
import edu.ntnu.stud.repo.CategoryRepo;
import edu.ntnu.stud.util.Validate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing categories in the system.
 */
@Service
public class CategoryService {
  @Autowired
  private CategoryRepo categoryRepo;
  @Autowired
  private JWTService jwtService;

  /**
   * Adds a new category to the database.
   *
   * @param category the category to be added
   * @param token the JWT token for authorization
   */
  public void addCategory(CategoryRequest category, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to add a category.");
    Validate.that(category.getName(), Validate.isNotBlankOrNull(),
        "Category name cannot be null or empty.");
    categoryRepo.addCategory(category);
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   * @param token the JWT token for authorization
   */
  public void updateCategory(Category category, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to update a category.");
    Validate.that(category.getName(), Validate.isNotBlankOrNull(),
        "Category name cannot be null or empty.");
    categoryRepo.updateCategory(category);
  }

  /**
   * Deletes a category from the database.
   *
   * @param categoryId the ID of the category to be deleted
   * @param token the JWT token for authorization
   */
  public void deleteCategory(int categoryId, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to delete a category.");
    categoryRepo.deleteCategory(categoryId);
    // Delete subcategories associated with this category
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  public List<Category> getAllCategories() {
    return categoryRepo.getAllCategories();
  }

  /**
   * Retrieves a category by its ID.
   *
   * @param categoryId the ID of the category to be retrieved
   * @return the category with the specified ID, or null if not found
   */
  public Category getCategoryById(int categoryId) {
    return categoryRepo.getCategoryById(categoryId);
  }
}
