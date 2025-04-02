package edu.ntnu.stud.repo;

import edu.ntnu.stud.model.SubCategory;
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
public class SubCategoryRepo {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String password;

  /**
   * Initializes the repo and loads the MySQL JDBC driver.
   */
  public SubCategoryRepo() {
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
  public void addCategory(SubCategory category) {
    String query = 
        "INSERT INTO categories (name, description, icon, parrent_id) VALUES (?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, category.getName());
      statement.setString(2, category.getDescription());
      statement.setString(3, category.getIcon());
      statement.setInt(4, category.getParrentId());
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
   * Updates an existing category in the database.
   *
   * @param category the category to be updated
   */
  public void updateCategory(SubCategory category) {
    String query =
        "UPDATE categories SET name = ?, description = ?, icon = ?, parrent_id = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, category.getName());
      statement.setString(2, category.getDescription());
      statement.setString(3, category.getIcon());
      statement.setInt(4, category.getId());
      statement.setInt(5, category.getParrentId());
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
  public SubCategory getCategoryById(int categoryId) {
    String query = "SELECT * FROM categories WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, categoryId);
      var resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return new SubCategory(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("icon"),
            resultSet.getInt("parrent_id"));
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
  public List<SubCategory> getAllCategories() {
    String query = "SELECT * FROM categories";
    List<SubCategory> categories = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        categories.add(new SubCategory(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("icon"),
            resultSet.getInt("parrent_id")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return categories;
  }

  /**
   * Retrieves all subcategories associated with a specific category ID.
   *
   * @param categoryId the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  public List<SubCategory> getSubCategoriesByCategoryId(int categoryId) {
    String query = "SELECT * FROM categories WHERE parrent_id = ?";
    List<SubCategory> subCategories = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, categoryId);
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        subCategories.add(new SubCategory(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getString("icon"),
            resultSet.getInt("parrent_id")));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return subCategories;
  }
}
