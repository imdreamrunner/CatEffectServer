package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class Dish extends Model {
    @Id
    private Integer dishId;

    private String name;

    private String image;

    @Lob
    private String description;

    private Integer stallId;

    private Integer categoryId;

    private Integer sort = 0;

    private Integer listPrice;

    private Integer price;

    private Boolean allowDiscount = true; // Whether join other stall's discounts.

    private String options;
}