package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;
import utils.CatException;

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
    @ManyToOne
    private Stall stall;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    private Integer status; // 1 for paid, 2 for ready, 3 for served.
    private Date createTime;
    private Date serveTime;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    private Integer feedback1, feedback2, feedback3;
    // value could be 1, 2 or 3.

    public static Finder<Integer, Order> find = new Finder<Integer, Order>(
            Integer.class, Order.class
    );

    public Order() {
        createTime = new Date();
        status = 0;
    }

    public Integer getOrderId() {return orderId;}
    @JsonIgnore
    public Stall getStall() {return stall;}
    public Integer getStallId() {return stall != null ? stall.getStallId() : null; }
    @JsonIgnore
    public Account getAccount() {return account;}
    public Integer getAccountId() { return account != null ? account.getAccountId() : null; }
    public Integer getSubtotal() {return subtotal;}
    public Transaction getTransaction() {return transaction;}
    public Integer getStatus() {return status;}
    public Date getCreateTime() {return createTime;}
    public Date getServeTime() {return serveTime;}
    public List<OrderItem> getOrderItems() {return orderItems;}

    public void setOrderId(Integer newOrderId) {orderId = newOrderId;}
    public void setStall(Stall newStall) {stall = newStall;}
    public void setStall(Integer newStallId) {
        Stall stall = Stall.find.byId(newStallId);
        setStall(stall);
    }
    public void setAccount(Account newAccount) {account = newAccount;}
    public void setAccount(Integer newAccountId) throws CatException {
        Account newAccount = Account.find.byId(newAccountId);
        if (newAccount == null) {
            throw new CatException(6001, "Account not found");
        }
        setAccount(newAccount);
    }
    public void setSubtotal(Integer newSubtotal) {subtotal = newSubtotal;}
    public void setTransaction(Transaction newTransaction) {transaction = newTransaction;}
    public void setTransaction(Integer newTransactionId) {
        Transaction newTransaction = Transaction.find.byId(newTransactionId);
        setTransaction(newTransaction);
    }
    public void setStatus(Integer newStatus) {status = newStatus;}
    public void setServeTime(Date newServeTime) {serveTime = newServeTime;}



}
