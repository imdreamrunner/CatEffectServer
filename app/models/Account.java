package models;

import btu_models.Faculty;
import btu_models.Staff;
import btu_models.Student;
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

    public static Finder<Integer, Account> find = new Finder<Integer, Account>(
            Integer.class, Account.class
    );

    public Account() {
        setBalance(0);
        setCreateTime(new Date());
    }

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
        calculateBalance();
        return balance;
    }

    public void calculateBalance() {
        Integer total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getAmount();
        }
        setBalance(total);
        save();
    }

    public void setBalance(Integer b) {
        // If balance is low, reminder shall be sent.
        balance = b;
    }

    public void setCreateTime(Date newCreateTime) {
        createTime = newCreateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUsedTime(Date newLastUsedTime) {
        lastUsedTime = newLastUsedTime;
    }

    public Date getLastUsedTime() {
        return lastUsedTime;
    }

    public static Account getAccount(Integer type, Integer relevantId) {
        List<Account> accounts = find.where("type = " + type + " and relevant_id = " + relevantId).findList();
        if (accounts.size() < 1) {
            Account newAccount = new Account();
            newAccount.setType(type);
            newAccount.setRelevantId(relevantId);
            newAccount.save();
            return newAccount;
        } else {
            return accounts.get(0);
        }
    }

    public PrepaidCard getPrepaidCard() {
        if (type == 0) {
            return PrepaidCard.find.byId(relevantId);
        } else {
            return null;
        }
    }

    public Student getStudent() {
        return type == 1 ? Student.find.byId(relevantId) : null;
    }

    public Faculty getFaculty() {
        return type == 2 ? Faculty.find.byId(relevantId) : null;
    }

    public Staff getStaff() {
        return type == 3 ? Staff.find.byId(relevantId) : null;
    }
}
