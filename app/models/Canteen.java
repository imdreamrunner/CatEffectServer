package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Canteen extends Model {
    @Id
    private Integer canteenId;

    private String name;

    private Integer sort = 0;

    public Integer getCanteenId() {
        return canteenId;
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
}
