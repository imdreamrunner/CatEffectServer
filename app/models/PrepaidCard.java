package models;

import org.codehaus.jackson.annotate.JsonIgnore;
import play.db.ebean.Model;
import utils.CatException;
import utils.RandomString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PrepaidCard extends Model {
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

    public static Model.Finder<Integer, PrepaidCard> find
            = new Model.Finder<Integer, PrepaidCard>(
                Integer.class, PrepaidCard.class
    );

    @JsonIgnore
    public Account getAccount() {
        return Account.getAccount(0, prepaidCardId);
    }

    public Integer getAccountId() {
        return getAccount().getAccountId();
    }

    public Integer getBalance() {
        Account account = getAccount();
        return account != null ? account.getBalance() : null;
    }
}
