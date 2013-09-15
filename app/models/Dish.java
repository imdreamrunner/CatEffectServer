package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class Dish extends Model {
    @Id
    private Integer dishId;
    private String name;
    private String image;

    @Lob
    private String description;
    private Integer stallId;
    private Integer categoryId;
    private Integer sort = 0;
    private Integer listPrice;
    private Integer price;
    private Boolean allowDiscount = true; // Whether join other stall's discounts.
    private String options;

    public Dish(String newName, String newImage, String newDescription, Integer newStallId, Integer newCategoryId, 
        Integer newListPrice, Integer newPrice, String newOptions) throws CatException {
        if (newStallId == null) {
            throw new CatException(6001, "must input stallId");
        }
        if (newCategoryId == null) {
            throw new CatException(6002, "must input categoryId");
        }
        if (newName == null) {
            throw new CatException(5003, "must input name of the dish");
        }
        if (newPrice == null) {
            throw new CatException(5004, "must input price of the dish");
        }
        name = newName;
        image = newImage;
        description = newDescription;
        stallId = newStallId;
        categoryId = newCategoryId;
        listPrice = newListPrice;
        price = newPrice;
        options = newOptions;
    }

    public static Finder<Integer, Dish> find = new Finder<Integer, Dish>(
            Integer.class, Dish.class
    );

    public Integer getdishId() { return dishId; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Integer getStallId() { return stallId; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getSort() { return sort; }
    public Integer getListPrice() { return listPrice; }
    public Integer getPrice() { return price; }
    public Boolean getAllowDiscount() { return allowDiscount; }
    public String getOptions() { return options; }

    public void setName(String newName) { name = newName;};
    public void setImage(String newImage) { image = newImage;};
    public void setDescription(String newDescription) { description = newDescription;};
    public void setStallId(Integer newStallId) { stallId = newStallId;};
    public void setCategoryId(Integer newCategoryId) { categoryId = newCategoryId;};
    public void setSort(Integer newSort) { sort = newSort;};
    public void setListPrice(Integer newListPrice) { listPrice = newListPrice;};
    public void setPrice(Integer newPrice) { price = newPrice;};
    public void setAllowDiscount(Boolean newAllowDiscount) { allowDiscount = newAllowDiscount;};
    public void setOptions(String newOptions) { options = newOptions;};


}