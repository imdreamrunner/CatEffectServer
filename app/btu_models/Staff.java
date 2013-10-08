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
}
