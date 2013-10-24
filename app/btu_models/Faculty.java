package btu_models;

import models.Account;
import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Faculty extends Model {
    @Id
    private Integer facultyId;
    private String name;
    private String email;
    private String cardId;

    public static Model.Finder<Integer, Faculty> find
            = new Model.Finder<Integer, Faculty>(
            "btu", Integer.class, Faculty.class
    );

    public Integer getFacultyId() { return facultyId; }
    public String getName() { return name; };
    public String getEmail() { return email; };
    public String getCardId() { return cardId; };

    public void setFacultyId(Integer newFacultyId) {
        facultyId = newFacultyId;
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
        return Account.getAccount(2, facultyId);
    }
}
