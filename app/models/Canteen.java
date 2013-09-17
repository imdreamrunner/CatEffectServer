package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Canteen extends Model {
    @Id
    private Integer canteenId;
    private String name;
    private Integer sort = 0;
    @OneToMany(mappedBy = "canteen")
    private List<Stall> stalls;

    public static Finder<Integer, Canteen> find = new Finder<Integer, Canteen>(
            Integer.class, Canteen.class
    );

    public Integer getCanteenId() {
        return canteenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer newSort) {
        sort = newSort;
    }
}
