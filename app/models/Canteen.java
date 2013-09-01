package models;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Canteen extends Model {
    @Id
    private Integer canteenId;

    private String name;

    private Integer sort = 0;
}
