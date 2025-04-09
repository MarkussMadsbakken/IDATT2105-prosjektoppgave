package edu.ntnu.stud.model.request;

/**
 * Represents a category of items in the system.
 */
public class CategoryRequest {
  private String name;
  private String description;
  private String icon;

  /**
   * Constructor for CategoryRequest.
   *
   * @param name        the name of the category
   * @param description a description of the category
   * @param icon        an icon representing the category
   */
  public CategoryRequest(String name, String description, String icon) {
    this.name = name;
    this.description = description;
    this.icon = icon;
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
}
