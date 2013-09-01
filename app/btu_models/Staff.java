package btu_models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import utils.*;

@Entity
public class Staff extends Model {
    @Id
    private Integer staffId;

    private String name;

    private String cardId;
}
