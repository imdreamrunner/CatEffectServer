package models;

import javax.persistence.*;
import play.db.ebean.*;

import java.util.Date;

@Entity
public class Account extends Model {
    @Id
    private Integer accountId;

    private Integer type; // 0 for prepaid card, 1 for student, 2 for faculty, 3 for staff.

    private Integer relevantId = 0;

    private Integer balance = 0;

    private Date createTime;

    private Date lastUsedTime;

}
