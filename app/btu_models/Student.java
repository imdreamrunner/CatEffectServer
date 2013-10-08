package btu_models;

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
}
