package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Category;
import edu.ntnu.stud.model.CategoryRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing categories in the system.
 */
@Repository
public class CategoryRepo {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<Category> categoryRowMapper = (resultSet, rowNum) -> {
    Category category = new Category();
    category.setId(resultSet.getInt("id"));
    category.setName(resultSet.getString("name"));
    category.setDescription(resultSet.getString("description"));
    category.setIcon(resultSet.getString("icon"));
    return category;
  };

  /**
   * Adds a new category to the database.
   *
   * @param category the category to be added
   */
  public void addCategory(CategoryRequest category) {
    String query = "INSERT INTO categories (name, description, icon) VALUES (?, ?, ?)";
    jdbcTemplate.update(query, category.getName(), category.getDescription(), category.getIcon());
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   */
  public void updateCategory(Category category) {
    String query = "UPDATE categories SET name = ?, description = ?, icon = ? WHERE id = ?";
    jdbcTemplate.update(query, 
        category.getName(), 
        category.getDescription(), 
        category.getIcon(), 
        category.getId());
  }

  /**
   * Deletes a category from the database.
   *
   * @param categoryId the ID of the category to be deleted
   */
  public void deleteCategory(int categoryId) {
    String query = "DELETE FROM categories WHERE id = ?";
    jdbcTemplate.update(query, categoryId);
  }

  /**
   * Retrieves a category from the database by its ID.
   *
   * @param categoryId the ID of the category to be retrieved
   * @return the category object, or null if not found
   */
  public Category getCategoryById(int categoryId) {
    String query = "SELECT * FROM categories WHERE id = ?";
    List<Category> categories = jdbcTemplate.query(query, categoryRowMapper, categoryId);
    return categories.isEmpty() ? null : categories.get(0);
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  public List<Category> getAllCategories() {
    String query = "SELECT * FROM categories";
    return jdbcTemplate.query(query, categoryRowMapper);
  }
}
