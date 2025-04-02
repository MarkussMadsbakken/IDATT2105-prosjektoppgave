package edu.ntnu.stud.model;

/**
 * Represents a subcategory of items in the system.
 */
public class SubCategory {
  private int id;
  private String name;
  private String description;
  private String icon;
  private int parrentId;

  /**
   * Constructor for SubCategory.
   *
   * @param id          the ID of the subcategory
   * @param name        the name of the subcategory
   * @param description a description of the subcategory
   * @param icon        an icon representing the subcategory
   * @param categoryId  the ID of the parent category
   */
  public SubCategory(int id, String name, String description, String icon, int categoryId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.icon = icon;
    this.parrentId = categoryId;
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
