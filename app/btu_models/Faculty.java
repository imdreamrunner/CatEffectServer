package btu_models;

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
}
