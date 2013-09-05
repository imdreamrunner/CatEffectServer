package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
@Table(name="order_detail")
public class Order extends Model {
    @Id
    private Integer orderId;

    private Integer accountId;

    private Integer originalSubtotal;

    private Integer discount = 0;

    private Integer subtotal; // Discounted subtotal.

    private Integer transactionId;

    private Integer status;

    private Date createTime;

    private Date serveTime;
}
