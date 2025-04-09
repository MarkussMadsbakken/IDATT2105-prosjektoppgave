package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.CategoryRequest;
import edu.ntnu.stud.model.base.Category;
import java.util.List;
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
 * Test class for CategoryRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryRepoTest {

  @Autowired
  private CategoryRepo categoryRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void setUp() {
    jdbcTemplate.execute("INSERT INTO categories (name, description, icon) " 
        + "VALUES ('Test Category', 'Test Description', 'test-icon.png')");
  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM sub_categories");
    jdbcTemplate.execute("DELETE FROM categories");
  }

  @Test
  public void testAddCategory() {
    CategoryRequest categoryRequest = new CategoryRequest(
        "New Category", "New Description", "new-icon.png");

    categoryRepo.addCategory(categoryRequest);

    List<Category> categories = categoryRepo.getAllCategories();
    assertThat(categories).hasSize(2);
    assertThat(categories).extracting(Category::getName).contains("New Category");
  }

  @Test
  public void testUpdateCategory() {
    Category category = categoryRepo.getAllCategories().getFirst();
    category.setName("Updated Category");
    category.setDescription("Updated Description");
    category.setIcon("updated-icon.png");

    categoryRepo.updateCategory(category);

    Category updatedCategory = categoryRepo.getCategoryById(category.getId());
    assertThat(updatedCategory.getName()).isEqualTo("Updated Category");
    assertThat(updatedCategory.getDescription()).isEqualTo("Updated Description");
    assertThat(updatedCategory.getIcon()).isEqualTo("updated-icon.png");
  }

  @Test
  public void testDeleteCategory() {
    Category category = categoryRepo.getAllCategories().getFirst();

    categoryRepo.deleteCategory(category.getId());

    List<Category> categories = categoryRepo.getAllCategories();
    assertThat(categories).isEmpty();
  }

  @Test
  public void testGetCategoryById() {
    Category category = categoryRepo.getAllCategories().getFirst();

    Category retrievedCategory = categoryRepo.getCategoryById(category.getId());
    assertThat(retrievedCategory).isNotNull();
    assertThat(retrievedCategory.getName()).isEqualTo("Test Category");
  }

  @Test
  public void testGetAllCategories() {
    List<Category> categories = categoryRepo.getAllCategories();

    assertThat(categories).hasSize(1);
    assertThat(categories.getFirst().getName()).isEqualTo("Test Category");
  }
}