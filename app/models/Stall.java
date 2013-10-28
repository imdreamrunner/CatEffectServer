package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;
import utils.CatException;
import javax.persistence.*;
import java.util.List;

@Entity
public class Stall extends Model {
    @Id
    private Integer stallId;
    private String name;
    private Integer sort = 0;
    @Lob
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name="canteen_id")
    private Canteen canteen;

    private Integer prepaidDiscount, studentDiscount, facultyDiscount, staffDiscount;

    @OneToMany(mappedBy = "stall", cascade = CascadeType.REMOVE)
    private List<Manager> managers;
    @OneToMany(mappedBy = "stall", cascade = CascadeType.REMOVE)
    private List<Category> categories;

    public static Finder<Integer, Stall> find = new Finder<Integer, Stall>(
            Integer.class, Stall.class
    );

    public Integer getStallId() { return stallId; }
    public String getName() { return name; }
    public String getDescription() {return description; }
    public Integer getSort() { return sort; }
    public String getImage() { return image; }
    @JsonIgnore
    public Canteen getCanteen() { return canteen; }
    public Integer getCanteenId() {
        return canteen != null ? canteen.getCanteenId() : null;
    }
    @JsonIgnore
    public List<Manager> getManagers() { return managers; }

    public Integer getPrepaidDiscount() {
        return prepaidDiscount;
    }

    public Integer getStudentDiscount() {
        return studentDiscount;
    }

    public Integer getFacultyDiscount() {
        return facultyDiscount;
    }

    public Integer getStaffDiscount() {
        return staffDiscount;
    }

    public void setStallId(Integer theStallId) {stallId = theStallId;}
    public void setName(String n) { name = n; }
    public void setSort(Integer s) {sort = s;}
    public void setDescription(String d) { description = d; }
    public void setImage(String i) { image = i; }
    public void setCanteen(Canteen newCanteen) { canteen = newCanteen; }
    public void setCanteen(Integer newCanteenId) throws CatException {
        Canteen newCanteen = Canteen.find.byId(newCanteenId);
        if (newCanteen == null) {
            throw new CatException(5001, "Canteen does not exist.");
        }
        setCanteen(newCanteen);
    }

    public static boolean checkStallName(String newStallName) {
        if (newStallName == null) {
            return false;
        }
        Integer numberOfStalls = find.where(String.format("name = '%s'", newStallName)).findRowCount();
        return (numberOfStalls == 0);
    }
    public void setPrepaidDiscount(Integer pd) {
        prepaidDiscount = pd;
    }
    public void setStudentDiscount(Integer sd) {
        studentDiscount = sd;
    }
    public void setFacultyDiscount(Integer fd) {
        facultyDiscount = fd;
    }
    public void setStaffDiscount(Integer sd) {
        staffDiscount = sd;
    }
}