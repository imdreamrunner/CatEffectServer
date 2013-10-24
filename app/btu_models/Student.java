package btu_models;

import models.Account;
import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student extends Model {
    @Id
    private Integer studentId;
    private String name;
    private String email;
    private String cardId;

    public static Model.Finder<Integer, Student> find
            = new Model.Finder<Integer, Student>(
            "btu", Integer.class, Student.class
    );

    public Integer getStudentId() { return studentId; }
    public String getName() { return name; };
    public String getEmail() { return email; };
    public String getCardId() { return cardId; };

    public void setStudentId(Integer newStudentId) {
        studentId = newStudentId;
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
        return Account.getAccount(1, studentId);
    }
}
