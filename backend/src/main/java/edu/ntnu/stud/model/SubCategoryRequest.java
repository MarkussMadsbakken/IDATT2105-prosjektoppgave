package edu.ntnu.stud.model;

/**
 * Represents a request to create or update a subcategory in the system.
 */
public class SubCategoryRequest {
  private String name;
  private String description;
  private String icon;
  private int parrentId;

  /**
   * Constructor for SubCategoryRequest.
   *
   * @param name        the name of the subcategory
   * @param description a description of the subcategory
   * @param icon        an icon representing the subcategory
   * @param parrentId  the ID of the parent category
   */
  public SubCategoryRequest(String name, String description, String icon, int parrentId) {
    this.name = name;
    this.description = description;
    this.icon = icon;
    this.parrentId = parrentId;
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

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public int getParrentId() {
    return parrentId;
  }

  public void setParrentId(int categoryId) {
    this.parrentId = categoryId;
  }
}
