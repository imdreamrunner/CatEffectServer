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
}
