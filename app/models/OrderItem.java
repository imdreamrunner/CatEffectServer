package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class OrderItem extends Model {
    @Id
    private Integer orderItemId;

    private Integer orderId;

    private Integer dishId;

    private Integer listPrice;

    private Integer price;

    private Integer quantity;

    private String note;
}
