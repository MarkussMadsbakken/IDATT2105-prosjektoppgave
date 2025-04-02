package edu.ntnu.stud.controller;

import edu.ntnu.stud.model.Category;
import edu.ntnu.stud.model.SubCategory;
import edu.ntnu.stud.service.CategoryService;
import edu.ntnu.stud.service.SubCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for managing Categories and subcategories entities.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
  
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private SubCategoryService subCategoryService;

  /**
   * Retrieves a category by its ID.
   *
   * @param id the ID of the category to retrieve
   * @return the category with the specified ID, or a 404 Not Found status if not found
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
  @GetMapping("/subcategories/under/{id}")
  public ResponseEntity<List<SubCategory>> getSubCategoriesByCategoryId(@PathVariable int id) {
    List<SubCategory> subCategories = subCategoryService.getSubCategoriesByCategoryId(id);
    if (subCategories != null && !subCategories.isEmpty()) {
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

}
