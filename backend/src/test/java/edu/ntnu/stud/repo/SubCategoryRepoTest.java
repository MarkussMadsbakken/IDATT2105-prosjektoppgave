package edu.ntnu.stud.repo;

import static org.assertj.core.api.Assertions.assertThat;

import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.model.SubCategoryRequest;
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
 * Test class for SubCategoryRepo.
 */
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SubCategoryRepoTest {

  @Autowired
  private SubCategoryRepo subCategoryRepo;

  @Autowired
  CategoryRepo categoryRepo;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private int parentCategoryId;

  /**
   * Sets up the test environment by creating a parent category and a subcategory.
   */
  @BeforeEach
  public void setUp() {
    jdbcTemplate.execute(
        "INSERT INTO categories (name, description, icon) " 
        + "VALUES ('Parent Category', 'Parent Description', 'parent-icon.png')");

    parentCategoryId = categoryRepo.getAllCategories().getFirst().getId();

    jdbcTemplate.execute(
        "INSERT INTO sub_categories (name, description, category_id) " 
        + "VALUES ('Test SubCategory', 'Test Description', "
            + parentCategoryId + ")");

  }

  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM sub_categories");
    jdbcTemplate.execute("DELETE FROM categories");
  }

  @Test
  public void testAddSubCategory() {
    SubCategoryRequest subCategoryRequest = 
        new SubCategoryRequest("New SubCategory", "New Description", "icon",
        parentCategoryId);

    subCategoryRepo.addSubCategory(subCategoryRequest);

    List<SubCategory> subCategories = subCategoryRepo.getAllCategories();
    assertThat(subCategories).hasSize(2);
    assertThat(subCategories).extracting(SubCategory::getName).contains("New SubCategory");
  }

  @Test
  public void testUpdateSubCategory() {
    SubCategory subCategory = subCategoryRepo.getAllCategories().getFirst();
    subCategory.setName("Updated SubCategory");
    subCategory.setDescription("Updated Description");

    subCategoryRepo.updateSubCategory(subCategory);

    SubCategory updatedSubCategory = subCategoryRepo.getSubCategoryById(subCategory.getId());
    assertThat(updatedSubCategory.getName()).isEqualTo("Updated SubCategory");
    assertThat(updatedSubCategory.getDescription()).isEqualTo("Updated Description");
  }

  @Test
  public void testDeleteSubCategory() {
    SubCategory subCategory = subCategoryRepo.getAllCategories().getFirst();

    subCategoryRepo.deleteSubCategory(subCategory.getId());

    List<SubCategory> subCategories = subCategoryRepo.getAllCategories();
    assertThat(subCategories).isEmpty();
  }

  @Test
  public void testGetSubCategoryById() {
    SubCategory subCategory = subCategoryRepo.getAllCategories().getFirst();

    SubCategory retrievedSubCategory = subCategoryRepo.getSubCategoryById(subCategory.getId());
    assertThat(retrievedSubCategory).isNotNull();
    assertThat(retrievedSubCategory.getName()).isEqualTo("Test SubCategory");
  }

  @Test
  public void testGetAllCategories() {
    List<SubCategory> subCategories = subCategoryRepo.getAllCategories();

    assertThat(subCategories).hasSize(1);
    assertThat(subCategories.getFirst().getName()).isEqualTo("Test SubCategory");
  }

  @Test
  public void testGetSubCategoriesByCategoryId() {
    List<SubCategory> subCategories = 
        subCategoryRepo.getSubCategoriesByCategoryId(parentCategoryId);

    assertThat(subCategories).hasSize(1);
    assertThat(subCategories.getFirst().getName()).isEqualTo("Test SubCategory");
  }
}