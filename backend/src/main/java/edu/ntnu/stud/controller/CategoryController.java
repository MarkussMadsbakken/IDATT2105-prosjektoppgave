package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.SubCategoryRequest;
import edu.ntnu.stud.model.base.Category;
import edu.ntnu.stud.model.base.DefaultResponse;
import edu.ntnu.stud.model.base.SubCategory;
import edu.ntnu.stud.model.request.CategoryRequest;
import edu.ntnu.stud.service.CategoryService;
import edu.ntnu.stud.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing Categories and subcategories entities.
 */
@Tag(name = "Category", 
    description = "Endpoints for managing categories and subcategories")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;
  @Autowired
  private SubCategoryService subCategoryService;

  /**
   * Retrieves a category by its ID.
   *
   * @param id the ID of the category to retrieve
   * @return the category with the specified ID, or a 404 Not Found status if not
   *         found
   */
  @Operation(summary = "Retrieve a category by ID", 
      description = "Fetches a category by its unique identifier.")
  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
    Category category = categoryService.getCategoryById(id);
    if (category != null) {
      return ResponseEntity.ok(category);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  @Operation(summary = "Retrieve all categories", 
      description = "Fetches all categories from the database.")
  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    if (categories != null && !categories.isEmpty()) {
      return ResponseEntity.ok(categories);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves all subcategories associated with a specific subcategory ID.
   *
   * @param id the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  @Operation(summary = "Retrieve subcategory by ID", 
      description = "Fetches a subcategory by its unique identifier.")
  @GetMapping("/subcategories/{id}")
  public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable int id) {
    SubCategory subCategory = subCategoryService.getSubCategoryById(id);
    if (subCategory != null) {
      return ResponseEntity.ok(subCategory);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves all subcategories associated with a specific category ID.
   *
   * @param id the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  @Operation(summary = "Retrieve subcategories by category ID", 
      description = "Fetches all subcategories associated with a specific category ID.")
  @GetMapping("/{id}/subcategories")
  public ResponseEntity<List<SubCategory>> getSubCategoriesByCategoryId(@PathVariable int id) {
    List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(id);
    if (subCategories != null && !subCategories.isEmpty()) {
      return ResponseEntity.ok(subCategories);
    } else if (subCategories != null) {
      return ResponseEntity.ok(subCategories);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Retrieves all subcategories from the database.
   *
   * @return a list of all subcategories
   */
  @Operation(summary = "Retrieve all subcategories", 
      description = "Fetches all subcategories from the database.")
  @GetMapping("/subcategories")
  public ResponseEntity<List<SubCategory>> getAllSubCategories() {
    List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
    if (subCategories != null && !subCategories.isEmpty()) {
      return ResponseEntity.ok(subCategories);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  /**
   * Adds a new category to the database.
   *
   * @param category the category to add
   */
  @Operation(summary = "Add a new category", description = "Adds a new category to the database.")
  @PostMapping
  public ResponseEntity<DefaultResponse> addCategory(@RequestBody CategoryRequest category,
      @RequestHeader("Authorization") String token) {
    categoryService.addCategory(category, token);
    return ResponseEntity.ok().body(new DefaultResponse("Category added successfully.", 
      "CategoryAddSuccessfull"));
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to update
   */
  @Operation(summary = "Update an existing category", 
      description = "Updates an existing category in the database.")
  @PutMapping
  public ResponseEntity<DefaultResponse> updateCategory(@RequestBody Category category,
      @RequestHeader("Authorization") String token) {
    categoryService.updateCategory(category, token);
    return ResponseEntity.ok().body(new DefaultResponse("Category updated successfully.",
      "CategoryUpdateSuccessfull"));
  }

  /**
   * Deletes a category from the database.
   *
   * @param id the ID of the category to delete
   */
  @Operation(summary = "Delete a category", 
      description = "Deletes a category from the database by its ID.")
  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse> deleteCategory(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    categoryService.deleteCategory(id, token);
    return ResponseEntity.ok().body(new DefaultResponse("Category deleted successfully.",
      "CategoryDeleteSuccessfull"));
  }

  /**
   * Adds a new subcategory to the database.
   *
   * @param subCategory the subcategory to add
   */
  @Operation(summary = "Add a new subcategory", 
      description = "Adds a new subcategory to the database.")
  @PostMapping("/subcategories")
  public ResponseEntity<DefaultResponse> addSubCategory(@RequestBody SubCategoryRequest subCategory,
      @RequestHeader("Authorization") String token) {
    subCategoryService.addSubCategory(subCategory, token);
    return ResponseEntity.ok()
        .body(new DefaultResponse("Subcategory added successfully.", "SubCategoryAddSuccessfull"));
  }

  /**
   * Updates an existing subcategory in the database.
   *
   * @param subCategory the subcategory to update
   */
  @Operation(summary = "Update an existing subcategory", 
      description = "Updates an existing subcategory in the database.")
  @PutMapping("/subcategories")
  public ResponseEntity<String> updateSubCategory(@RequestBody SubCategory subCategory,
      @RequestHeader("Authorization") String token) {
    subCategoryService.updateSubCategory(subCategory, token);
    return ResponseEntity.ok("Subcategory updated successfully.");
  }

  /**
   * Deletes a subcategory from the database.
   *
   * @param id the ID of the subcategory to delete
   */
  @Operation(summary = "Delete a subcategory", 
      description = "Deletes a subcategory from the database by its ID.")
  @DeleteMapping("/subcategories/{id}")
  public ResponseEntity<DefaultResponse> deleteSubCategory(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    subCategoryService.deleteSubCategory(id, token);
    return ResponseEntity.ok()
        .body(new DefaultResponse("Subcategory deleted successfully.", 
        "SubCategoryDeleteSuccessfull"));
  }

}
