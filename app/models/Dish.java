package models;

import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.*;
import java.util.Calendar;

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
    private Integer price;
    private Integer promotionalPrice;
    private Integer promotionStart, promotionEnd;
    private String options;

    public static Finder<Integer, Dish> find = new Finder<Integer, Dish>(
            Integer.class, Dish.class
    );

    public Integer getDishId() { return dishId; }
    public String getName() { return name; }
    public String getImage() { return image; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public Integer getSort() { return sort; }
    public Integer getPromotionalPrice() {return promotionalPrice;}
    public Integer getPrice() {
        return price;
    }

    public boolean getPromitionActivated() {
        if (promotionStart==null || promotionEnd==null) return false;
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int currentTime = hour*60+minute;
        if (promotionStart <= promotionEnd) 
            return (promotionStart <= currentTime && currentTime <= promotionEnd);
        else
            return (promotionStart <= currentTime || currentTime <= promotionEnd);
        
    }
    public Integer getFinalPrice() { 
        if (getPromitionActivated())
            return promotionalPrice;
        else 
            return price; 
    }

    public String getOptions() { return options; }
    public Integer getPromotionStart() {return promotionStart; }
    public Integer getPromotionEnd() {return promotionEnd; }

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
    public void setPrice(Integer newPrice) { price = newPrice;}
    public void setPromotionalPrice(Integer newPromotionalPrice) {promotionalPrice = newPromotionalPrice; }
    public void setPromotionStart(Integer newPromotionStart) {promotionStart = newPromotionStart; }
    public void setPromotionEnd(Integer newPromotionEnd) {promotionEnd = newPromotionEnd; }
    public void setOptions(String newOptions) { options = newOptions;}
    public void setPromotionPeriod(int newPromotionStart, int newPromotionEnd, int newPromotionalPrice) {
        promotionStart = newPromotionStart;
        promotionEnd = newPromotionEnd;
        setPromotionalPrice(newPromotionalPrice);
    }


}