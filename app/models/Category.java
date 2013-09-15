package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class Category extends Model {
    @Id
    private Integer categoryId;

    private String name;

    private Integer stallId;

    private Integer displayOption = 0;

    private Integer sort = 0;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name; 
    }

    public void setName(String newName) {
        name = newName;
    }

    public Integer getStallId() {
        return stallId;
    }

    public void setStallId(Integer newStallId) {
        stallId = newStallId;
    }

    public Integer getDisplayOption() {
        return displayOption;
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
    	Boolean nonDuplicate = (find.where(String.format("stallId = ‘%d’ && name = '%s'", newStallId,newName)).findRowCount() == 0);
    	if (!nonDuplicate) {
    		throw new CatException(5003, "the stall already has this category");
    	}
    	name = newName;
    	stallId = newStallId;
        save();
    }
}
