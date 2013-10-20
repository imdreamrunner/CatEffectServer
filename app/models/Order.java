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

    private Integer feedback1, feedback2, feedback3;
    // value could be 1, 2 or 3.

    public static Finder<Integer, Order> find = new Finder<Integer, Order>(
            Integer.class, Order.class
    );

    public Integer getOrderId() {return orderId;}
    public Account getAccount() {return account;}
    public Integer getSubtotal() {return subtotal;}
    public Transaction getTransaction() {return transaction;}
    public Integer getStatus() {return status;}
    public Date getCreateTime() {return createTime;}
    public Date getServeTime() {return serveTime;}
    public List<OrderItem> getOrderItems() {return orderItems;}

    public void setOrderId(Integer newOrderId) {orderId = newOrderId;}
    public void setAccount(Account newAccount) {account = newAccount;}
    public void setSubtotal(Integer newSubtotal) {subtotal = newSubtotal;}
    public void setTransaction(Transaction newTransaction) {transaction = newTransaction;}
    public void setStatus(Integer newStatus) {status = newStatus;}
    public void setCreateTime(Date newCreateTime) {createTime = newCreateTime;}
    public void setServeTime(Date newServeTime) {serveTime = newServeTime;}



}
