package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Transaction {
    @Id
    private Integer transactionId;

    private Integer accountId;

    private Integer type; // 0 for top-up, 1 for payments.

    private Integer amount; // This is a positive integer.

    private Date time;

    private Integer managerId = 0; // When top-up: OFS Staff Manager ID. 0 for payment
}
