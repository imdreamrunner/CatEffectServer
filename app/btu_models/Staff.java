package btu_models;

import models.Account;
import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Staff extends Model {
    @Id
    private Integer staffId;
    private String name;
    private String email;
    private String cardId;

    public static Model.Finder<Integer, Staff> find
            = new Model.Finder<Integer, Staff>(
            "btu", Integer.class, Staff.class
    );

    public Integer getStaffId() { return staffId; }
    public String getName() { return name; };
    public String getEmail() { return email; };
    public String getCardId() { return cardId; };

    public void setStaffId(Integer newStaffId) {
        staffId = newStaffId;
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setEmail(String newEmail) {
        email = newEmail;
    }
    public void setCardId(String newCardId) {
        cardId = newCardId;
    }

    @JsonIgnore
    public Account getAccount() {
        return Account.getAccount(3, staffId);
    }
}
