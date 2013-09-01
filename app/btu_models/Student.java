package btu_models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class Student extends Model {
    @Id
    private Integer studentId;

    private String name;

    private String cardId;
}
