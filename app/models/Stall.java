package models;

import javax.persistence.*;
import play.db.ebean.*;

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
}
