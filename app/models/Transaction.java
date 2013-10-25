package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Transaction extends Model {
    @Id
    private Integer transactionId;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    private Integer type; // 0 for top-up, 1 for payments.
    private Integer amount; // This is a positive integer.
    private Date time;
    @OneToOne(mappedBy = "transaction")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Integer getTransactionId() {return transactionId;}
    @JsonIgnore
    public Account getAccount() {return account;}
    public Integer getAccountId() {
        return account != null ? account.getAccountId() : null;
    }
    public Integer getAccountType() {
        return account != null ? account.getType() : null;
    }
    public Integer getType() {return type;}
    public Integer getAmount() {return amount;}
    public Date getTime() {return time;}
    public Order getOrder() {return order;}
    @JsonIgnore
    public Manager getManager() {return manager;}
    public Integer getManagerId() {
        return manager != null ? manager.getManagerId() : null;
    }

    public void setAccount(Account newAccount) {account = newAccount;}
    public void setType(Integer newType) {type = newType;}
    public void setAmount(Integer newAmount) {amount = newAmount;}
    public void setTime(Date newTime) {time = newTime;}
    public void setOrder(Order newOrder) {order = newOrder;}
    public void setManager(Manager newManager) {manager = newManager;}

    public static Model.Finder<Integer, Transaction> find
            = new Model.Finder<Integer, Transaction>(
                Integer.class, Transaction.class
    );
}
