package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Account extends Model {
    @Id
    private Integer accountId;
    private Integer type; // 0 for prepaid card, 1 for student, 2 for faculty, 3 for staff.
    private Integer relevantId = 0;
    private Integer balance = 0;
    private Date createTime;
    private Date lastUsedTime;
    @OneToMany(mappedBy = "account")
    private List<Order> orders;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Integer getAccountId() {
        return accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer t) {
        type = t;
    }

    public Integer getRelevantId() {
        return relevantId;
    }

    public void setRelevantId(Integer rid) {
        relevantId = rid;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer b) {
        balance = b;
    }

}
