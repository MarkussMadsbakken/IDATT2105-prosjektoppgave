package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.Category;
import edu.ntnu.stud.model.CategoryRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing categories in the system.
 */
@Repository
public class CategoryRepo {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  /**
   * Initializes the repo and loads the MySQL JDBC driver.
   */
  public CategoryRepo() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a new category to the database.
   *
   * @param category the category to be added
   */
  public void addCategory(CategoryRequest category) {
    String query = "INSERT INTO categories (name, description, icon) VALUES (?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, category.getName());
      statement.setString(2, category.getDescription());
      statement.setString(3, category.getIcon());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   */
  public void updateCategory(Category category) {
    String query = "UPDATE categories SET name = ?, description = ?, icon = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, category.getName());
      statement.setString(2, category.getDescription());
      statement.setString(3, category.getIcon());
      statement.setInt(4, category.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deletes a category from the database.
   *
   * @param categoryId the ID of the category to be deleted
   */
  public void deleteCategory(int categoryId) {
    String query = "DELETE FROM categories WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, categoryId);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieves a category from the database by its ID.
   *
   * @param categoryId the ID of the category to be retrieved
   * @return the category object, or null if not found
   */
  public Category getCategoryById(int categoryId) {
    String query = "SELECT * FROM categories WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, categoryId);
      var resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new Category(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("icon"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  public List<Category> getAllCategories() {
    String query = "SELECT * FROM categories";
    List<Category> categories = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        categories.add(new Category(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("icon")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }
}
