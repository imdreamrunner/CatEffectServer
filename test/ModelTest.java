import models.Account;
import org.junit.Test;
import utils.CatException;

import static junit.framework.Assert.assertNotNull;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;


public class ModelTest {
    @Test
    public void account() {
        Account account = new Account();
        assertNotNull(account);
        try {
            account.setType(1);
        } catch (CatException e) {
            fail("Cannot set type");
        }
        assertThat(account.getType()).isEqualTo(1);
    }

    @Test
    public void accountTypeLower() {
        Account account = new Account();
        try {
            account.setType(-1);
            fail("Invalid type.");
        } catch (CatException e) {
        }
    }

    @Test
    public void accountTypeUpper() {
        Account account = new Account();
        try {
            account.setType(4);
            fail("Invalid type.");
        } catch (CatException e) {
        }
    }
}
