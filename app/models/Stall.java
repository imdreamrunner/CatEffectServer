package models;

import javax.persistence.*;
import play.db.ebean.*;
import utils.CatException;

@Entity
public class Stall extends Model {
    @Id
    private Integer stallId;

    private String name;

    private Integer sort = 0;

    @Lob
    private String description;

    private String image;

    private Integer canteenId;

    private Integer discount = 0;

    public static Finder<Integer, Stall> find = new Finder<Integer, Stall>(
            Integer.class, Stall.class
    );

    public Integer getStallId() {
        return stallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer s) {
        sort = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String i) {
        image = i;
    }

    public Integer getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Integer cid) {
        canteenId = cid;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer d) throws CatException {
        if (d < 0 && d > 100) {
            throw new CatException(3001, "Value out of range");
        }
        discount = d;
    }

    public static boolean checkStallName(String newStallName) {
        if (newStallName == null) {
            return false;
        }
        Integer numberOfStalls = find.where(String.format("name = '%s'", newStallName)).findRowCount();
        return (numberOfStalls == 0);
    }

    public static boolean checkCanteenId(Integer newCanteenId) {
        /*
    	if (newCanteenId == null) {
    		return false;
    	}
    	//要查的是canteen的table而不是stall的table？
    	Integer numberOfCanteens = find.where(String.format("canteenIdId = '%d'", newCanteenId)).findRowCount();
    	return (numberOfCanteens > 0);
    	*/

        return true;
    }

    public Stall(String newStallName, String newDescription, String newImage, Integer newCanteenId) throws CatException {
    	if (!checkStallName(newStallName )) {
            throw new CatException(2, "Stall name has been taken.");
        }
        if (!checkCanteenId(newCanteenId)) {
        	throw new CatException(4,"Canteen does not exist.");
        }
        if (newStallName != null)name = newStallName;
        if (newDescription != null) description = newDescription;
        if (newImage != null ) image = newImage;
        if (newCanteenId != null) canteenId = newCanteenId;
        save();
    }
}