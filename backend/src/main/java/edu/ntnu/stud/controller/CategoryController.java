package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Category;
import edu.ntnu.stud.model.CategoryRequest;
import edu.ntnu.stud.model.DefaultResponse;
import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.model.SubCategoryRequest;
import edu.ntnu.stud.service.CategoryService;
import edu.ntnu.stud.service.JWTService;
import edu.ntnu.stud.service.SubCategoryService;
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
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;
  @Autowired
  private SubCategoryService subCategoryService;
  @Autowired
  private JWTService jwtService;

  /**
   * Retrieves a category by its ID.
   *
   * @param id the ID of the category to retrieve
   * @return the category with the specified ID, or a 404 Not Found status if not
   *         found
   */
  @GetMapping("/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
    Category category = categoryService.getCategoryById(id);
    if (category != null) {
      return ResponseEntity.ok(category);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves all categories from the database.
   *
   * @return a list of all categories
   */
  @GetMapping
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  /**
   * Retrieves all subcategories associated with a specific subcategory ID.
   *
   * @param id the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  @GetMapping("/subcategories/{id}")
  public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable int id) {
    SubCategory subCategory = subCategoryService.getSubCategoryById(id);
    if (subCategory != null) {
      return ResponseEntity.ok(subCategory);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves all subcategories associated with a specific category ID.
   *
   * @param id the ID of the category
   * @return a list of subcategories associated with the specified category ID
   */
  @GetMapping("/{id}/subcategories")
  public ResponseEntity<List<SubCategory>> getSubCategoriesByCategoryId(@PathVariable int id) {
    List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(id);
    if (subCategories != null && !subCategories.isEmpty()) {
      return ResponseEntity.ok(subCategories);
    } else if (subCategories != null) {
      return ResponseEntity.ok(subCategories);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Retrieves all subcategories from the database.
   *
   * @return a list of all subcategories
   */
  @GetMapping("/subcategories")
  public ResponseEntity<List<SubCategory>> getAllSubCategories() {
    List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
    return ResponseEntity.ok(subCategories);
  }

  /**
   * Adds a new category to the database.
   *
   * @param category the category to add
   */
  @PostMapping
  public ResponseEntity<DefaultResponse> addCategory(@RequestBody CategoryRequest category,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403)
          .body(new DefaultResponse("Error: You are not authorized to add categories.", "CategoryAddFailed"));
    }
    categoryService.addCategory(category);
    return ResponseEntity.ok().body(new DefaultResponse("Category added successfully.", "CategoryAddSuccessfull"));
  }

  /**
   * Updates an existing category in the database.
   *
   * @param category the category to update
   */
  @PutMapping
  public ResponseEntity<DefaultResponse> updateCategory(@RequestBody Category category,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403)
          .body(new DefaultResponse("Error: You are not authorized to update categories.", "CategoryUpdateFailed"));
    }
    categoryService.updateCategory(category);
    return ResponseEntity.ok().body(new DefaultResponse("Category updated successfully.", "CategoryUpdateSuccessfull"));
  }

  /**
   * Deletes a category from the database.
   *
   * @param id the ID of the category to delete
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<DefaultResponse> deleteCategory(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403)
          .body(new DefaultResponse("Error: You are not authorized to delete categories.", "CategoryDeleteFailed"));
    }
    categoryService.deleteCategory(id);
    return ResponseEntity.ok().body(new DefaultResponse("Category deleted successfully.", "CategoryDeleteSuccessfull"));
  }

  /**
   * Adds a new subcategory to the database.
   *
   * @param subCategory the subcategory to add
   */
  @PostMapping("/subcategories")
  public ResponseEntity<String> addSubCategory(@RequestBody SubCategoryRequest subCategory,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403).body("Error: You are not authorized to add subcategories.");
    }
    subCategoryService.addSubCategory(subCategory);
    return ResponseEntity.ok("Subcategory added successfully.");
  }

  /**
   * Updates an existing subcategory in the database.
   *
   * @param subCategory the subcategory to update
   */
  @PutMapping("/subcategories")
  public ResponseEntity<String> updateSubCategory(@RequestBody SubCategory subCategory,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403)
          .body("Error: You are not authorized to update subcategories.");
    }
    subCategoryService.updateSubCategory(subCategory);
    return ResponseEntity.ok("Subcategory updated successfully.");
  }

  /**
   * Deletes a subcategory from the database.
   *
   * @param id the ID of the subcategory to delete
   */
  @PostMapping("/subcategories/delete/{id}")
  public ResponseEntity<String> deleteSubCategory(@PathVariable int id,
      @RequestHeader("Authorization") String token) {
    boolean isAdmin = jwtService.extractIsAdmin(token.substring(7));
    if (!isAdmin) {
      return ResponseEntity.status(403)
          .body("Error: You are not authorized to delete subcategories.");
    }
    subCategoryService.deleteSubCategory(id);
    return ResponseEntity.ok("Subcategory deleted successfully.");
  }

}
