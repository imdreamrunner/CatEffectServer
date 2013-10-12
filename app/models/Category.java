package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;
import utils.CatException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category extends Model {
    @Id
    private Integer categoryId;
    private String name;
    @ManyToOne
    @JoinColumn(name="stall_id")
    private Stall stall;
    private Integer displayOption = 0;
    private Integer sort = 0;
    @OneToMany(mappedBy = "category")
    private List<Dish> dishes;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name; 
    }

    public void setName(String newName) {
        name = newName;
    }

    @JsonIgnore
    public Stall getStall() {
        return stall;
    }

    public Integer getStallId() {
        return stall == null ? null : stall.getStallId();
    }

    public void setStall(Stall newStall) {
        stall = newStall;
    }

    public void setStall(Integer newStallId) throws CatException {
        Stall newStall = Stall.find.byId(newStallId);
        if (newStall == null) {
            throw new CatException(5001, "Stall not found");
        }
        setStall(newStall);
    }

    public Integer getDisplayOption() {
        return displayOption;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDisplayOption(Integer newDisplayOption)  {
        displayOption = newDisplayOption;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer newSort) {
        sort = newSort;
    }

    public static Finder<Integer, Category> find = new Finder<Integer, Category>(
            Integer.class, Category.class
    );

    public Category(String newName, Integer newStallId) throws CatException {
    	if (newStallId == null) {
    		throw new CatException(5001, "must input stallId");
    	}
    	if (newName == null) {
    		throw new CatException(5002, "must input name of the category");
    	}

    	//check is this stall already has this category name
    	//stallID && categoryName
    	Boolean nonDuplicate = (find.where(String.format("stall_id = '%d' && name = '%s'", newStallId,newName)).findRowCount() == 0);
    	if (!nonDuplicate) {
    		throw new CatException(5003, "the stall already has this category");
    	}
    	name = newName;
        setStall(newStallId);
        save();
    }
}
