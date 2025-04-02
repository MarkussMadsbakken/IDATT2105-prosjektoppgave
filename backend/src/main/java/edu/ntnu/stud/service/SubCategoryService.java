package edu.ntnu.stud.service;

import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.repo.SubCategoryRepo;
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

  /**
   * Adds a new subcategory to the database.
   *
   * @param subCategory the subcategory to be added
   */
  public void addSubCategory(SubCategory subCategory) {
    // Validate the subcategory object before adding it to the database
    // Validate the parrentId to ensure it exists in the categories table
    subCategoryRepo.addCategory(subCategory);
  }

  /**
   * Updates an existing subcategory in the database.
   *
   * @param subCategory the subcategory to be updated
   */
  public void updateSubCategory(SubCategory subCategory) {
    // Validate the subcategory object before updating it in the database
    // Validate the parrentId to ensure it exists in the categories table
    subCategoryRepo.updateCategory(subCategory);
  }

  /**
   * Deletes a subcategory from the database.
   *
   * @param subCategoryId the ID of the subcategory to be deleted
   */
  public void deleteSubCategory(int subCategoryId) {
    subCategoryRepo.deleteCategory(subCategoryId);
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
    return subCategoryRepo.getCategoryById(subCategoryId);
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
