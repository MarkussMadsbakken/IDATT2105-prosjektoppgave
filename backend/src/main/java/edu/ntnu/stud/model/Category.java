package edu.ntnu.stud.model;

/**
 * Represents a category of items in the system.
 */
public class Category {
  private int id;
  private String name;
  private String description;
  private String icon;

  /**
   * Constructor for Category.
   *
   * @param id          the ID of the category
   * @param name        the name of the category
   * @param description a description of the category
   * @param icon        an icon representing the category
   */
  public Category(int id, String name, String description, String icon) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.icon = icon;
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
}
