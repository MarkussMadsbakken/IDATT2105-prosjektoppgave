package edu.ntnu.stud.service;

import edu.ntnu.stud.model.Category;
import edu.ntnu.stud.model.CategoryRequest;
import edu.ntnu.stud.repo.CategoryRepo;
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

  /**
   * Adds a new category to the database.
   *
   * @param category the category to be added
   */
  public void addCategory(CategoryRequest category) {
    categoryRepo.addCategory(category);
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   */
  public void updateCategory(Category category) {
    categoryRepo.updateCategory(category);
  }

  /**
   * Deletes a category from the database.
   *
   * @param categoryId the ID of the category to be deleted
   */
  public void deleteCategory(int categoryId) {
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
