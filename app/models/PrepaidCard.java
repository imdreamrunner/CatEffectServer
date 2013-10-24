package models;

import utils.CatException;
import utils.RandomString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrepaidCard {
    @Id
    private Integer prepaidCardId;
    private String token;

    public PrepaidCard() throws CatException {
        token = RandomString.generate(16);
        if (token == null) {
            throw new CatException(5001, "Token does not exist.");
        }
    }

    public Integer getPrepaidCardId() {return prepaidCardId;}
    public String getToken() {return token;}
}
