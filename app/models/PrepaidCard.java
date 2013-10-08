package models;

import utils.RandomString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrepaidCard {
    @Id
    private Integer prepaidCardId;
    private String token;

    public PrepaidCard() {
        token = RandomString.generate(16);
    }
}
