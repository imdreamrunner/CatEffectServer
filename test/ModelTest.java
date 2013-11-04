import models.Account;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.fest.assertions.Assertions.assertThat;


public class ModelTest {
    @Test
    public void testAccount() {
        Account account = new Account();
        assertNotNull(account);
        account.setType(1);
        assertThat(account.getType()).isEqualTo(1);
    }
}
