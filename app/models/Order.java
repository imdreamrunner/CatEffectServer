package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="order_detail")
public class Order extends Model {
    @Id
    private Integer orderId;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private Integer subtotal;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    private Integer status;
    private Date createTime;
    private Date serveTime;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public static Finder<Integer, Order> find = new Finder<Integer, Order>(
            Integer.class, Order.class
    );
}
