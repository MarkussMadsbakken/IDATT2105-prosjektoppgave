package edu.ntnu.stud.service;

import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.model.SubCategoryRequest;
import edu.ntnu.stud.repo.SubCategoryRepo;
import edu.ntnu.stud.util.Validate;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for managing subcategories in the system.
 */
@Service
public class SubCategoryService {

  @Autowired
  private SubCategoryRepo subCategoryRepo;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private JWTService jwtService;

  /**
   * Initializes the SubCategoryService with a CategoryService instance.
   *
   * @param categoryService the CategoryService instance to be used
   */
  SubCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /**
   * Adds a new subcategory to the database.
   *
   * @param subCategory the subcategory to be added
   * @param token the JWT token for authorization
   */
  public void addSubCategory(SubCategoryRequest subCategory, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to add a subcategory.");
    Validate.that(subCategory.getName(), Validate.isNotEmptyOrBlankOrNull(),
        "Subcategory name cannot be null or empty.");
    Validate.that(categoryService.getCategoryById(subCategory.getParentId()), Validate.isNotNull(), 
        "Parent category does not exist.");
    subCategoryRepo.addSubCategory(subCategory);
  }

  /**
   * Updates an existing subcategory in the database.
   *
   * @param subCategory the subcategory to be updated
   * @param token the JWT token for authorization
   */
  public void updateSubCategory(SubCategory subCategory, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to update a subcategory.");
    Validate.that(subCategory.getName(), Validate.isNotEmptyOrBlankOrNull(),
        "Subcategory name cannot be null or empty.");
    Validate.that(categoryService.getCategoryById(subCategory.getParentId()), Validate.isNotNull(), 
        "Parent category does not exist.");
    subCategoryRepo.updateSubCategory(subCategory);
  }

  /**
   * Deletes a subcategory from the database.
   *
   * @param subCategoryId the ID of the subcategory to be deleted
   * @param token the JWT token for authorization
   */
  public void deleteSubCategory(int subCategoryId, String token) {
    Validate.that(jwtService.extractIsAdmin(token.substring(7)), Validate.isTrue(),
        "You are not authorized to delete a subcategory.");
    subCategoryRepo.deleteSubCategory(subCategoryId);
  }

  /**
   * Retrieves all subcategories from the database.
   *
   * @return a list of all subcategories
   */
  public List<SubCategory> getAllSubCategories() {
    return subCategoryRepo.getAllCategories();
  }

  /**
   * Retrieves a subcategory by its ID.
   *
   * @param subCategoryId the ID of the subcategory to be retrieved
   * @return the subcategory with the specified ID, or null if not found
   */
  public SubCategory getSubCategoryById(int subCategoryId) {
    return subCategoryRepo.getSubCategoryById(subCategoryId);
  }

  /**
   * Retrieves all subcategories associated with a specific category ID.
   *
   * @param categoryId the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  public List<SubCategory> getSubCategoriesByCategoryId(int categoryId) {
    return subCategoryRepo.getSubCategoriesByCategoryId(categoryId);
  }
}
