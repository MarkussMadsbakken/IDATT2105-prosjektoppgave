package edu.ntnu.stud.model;

/**
 * Represents a subcategory of items in the system.
 */
public class SubCategory {
  private int id;
  private String name;
  private String description;
  private int parentId;

  /**
   * Default constructor for SubCategory.
   */
  public SubCategory() {
  }

  /**
   * Constructor for SubCategory.
   *
   * @param id          the ID of the subcategory
   * @param name        the name of the subcategory
   * @param description a description of the subcategory
   * @param parentId   the ID of the parent category
   */
  public SubCategory(int id, String name, String description, int parentId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.parentId = parentId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int categoryId) {
    this.parentId = categoryId;
  }
}
