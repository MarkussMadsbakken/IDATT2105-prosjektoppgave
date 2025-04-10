package edu.ntnu.stud.model.response;

/**
 * Represents a request to create or update a subcategory in the system.
 */
public class SubCategoryRequest {
  private String name;
  private String description;
  private String icon;
  private int parentId;

  /**
   * Constructor for SubCategoryRequest.
   *
   * @param name        the name of the subcategory
   * @param description a description of the subcategory
   * @param icon        an icon representing the subcategory
   * @param parrentId   the ID of the parent category
   */
  public SubCategoryRequest(String name, String description, int parrentId) {
    this.name = name;
    this.description = description;
    this.parentId = parrentId;
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
