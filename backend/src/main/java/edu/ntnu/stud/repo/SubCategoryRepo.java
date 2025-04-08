package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.model.SubCategoryRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing categories in the system.
 */
@Repository
public class SubCategoryRepo {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final RowMapper<SubCategory> subCategoryRowMapper = (resultSet, rowNum) -> {
    SubCategory subCategory = new SubCategory();
    subCategory.setId(resultSet.getInt("id"));
    subCategory.setName(resultSet.getString("name"));
    subCategory.setDescription(resultSet.getString("description"));
    subCategory.setParentId(resultSet.getInt("category_id"));
    return subCategory;
  };

  /**
   * Adds a new category to the database.
   *
   * @param category the category to be added
   */
  public void addSubCategory(SubCategoryRequest category) {
    String query = "INSERT INTO sub_categories (name, description, category_id) VALUES (?, ?, ?)";
    jdbcTemplate.update(query, 
        category.getName(), category.getDescription(), category.getParentId());
  }

  /**
   * Deletes a category from the database.
   *
   * @param categoryId the ID of the category to be deleted
   */
  public void deleteSubCategory(int categoryId) {
    String query = "DELETE FROM sub_categories WHERE id = ?";
    jdbcTemplate.update(query, categoryId);
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   */
  public void updateSubCategory(SubCategory category) {
    String query = 
        "UPDATE sub_categories SET name = ?, description = ?, category_id = ? WHERE id = ?";
    jdbcTemplate.update(query, 
        category.getName(), 
        category.getDescription(), 
        category.getParentId(), 
        category.getId());
  }

  /**
   * Retrieves a category from the database by its ID.
   *
   * @param categoryId the ID of the category to be retrieved
   * @return the category object, or null if not found
   */
  public SubCategory getSubCategoryById(int categoryId) {
    String query = "SELECT * FROM sub_categories WHERE id = ?";
    List<SubCategory> subCategories = jdbcTemplate.query(query, subCategoryRowMapper, categoryId);
    return subCategories.isEmpty() ? null : subCategories.get(0);
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  public List<SubCategory> getAllCategories() {
    String query = "SELECT * FROM sub_categories";
    return jdbcTemplate.query(query, subCategoryRowMapper);
  }

  /**
   * Retrieves all subcategories associated with a specific category ID.
   *
   * @param categoryId the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  public List<SubCategory> getSubCategoriesByCategoryId(int categoryId) {
    String query = "SELECT * FROM sub_categories WHERE category_id = ?";
    return jdbcTemplate.query(query, subCategoryRowMapper, categoryId);
  }
}
