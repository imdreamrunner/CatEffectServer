package btu_models;

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
}
