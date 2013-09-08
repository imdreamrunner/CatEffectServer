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
        Integer numOfStalls = find.where(String.format("name = '%s'", newStallName)).findRowCount();
        return (numOfStalls == 0);
    }

    public Stall(String newStallName, String newDescription, String newImage, Integer newCanteenId) {

    }
}









