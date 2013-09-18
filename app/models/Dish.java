package models;

import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.*;

@Entity
public class Dish extends Model {
    @Id
    private Integer dishId;
    private String name;
    private String image;
    @Lob
    private String description;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    private Integer sort = 0;
    private Integer listPrice;
    private Integer price;
    private Integer academicPrice;
    private String options;

    public static Finder<Integer, Dish> find = new Finder<Integer, Dish>(
            Integer.class, Dish.class
    );

    public Integer getdishId() { return dishId; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public Integer getSort() { return sort; }
    public Integer getListPrice() { return listPrice; }
    public Integer getPrice() { return price; }
    public Integer getAcademicPrice() { return academicPrice; }
    public String getOptions() { return options; }

    public void setName(String newName) { name = newName;}
    public void setImage(String newImage) { image = newImage;}
    public void setDescription(String newDescription) { description = newDescription;}
    public void setCategory(Category newCategory) {category = newCategory; }
    public void setCategory(Integer newCategoryId) throws CatException {
        Category newCategory = Category.find.byId(newCategoryId);
        if (newCategory == null) {
            throw new CatException(5001, "Category not found.");
        }
        setCategory(newCategory);
    }
    public void setSort(Integer newSort) { sort = newSort;}
    public void setListPrice(Integer newListPrice) { listPrice = newListPrice;}
    public void setPrice(Integer newPrice) { price = newPrice;}
    public void setAcademicPrice(Integer newAcademicPrice) { academicPrice = newAcademicPrice;}
    public void setOptions(String newOptions) { options = newOptions;}


}