import models.*;
import org.junit.Assert;
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

    @Test
    public void canteen() {
        Canteen canteen = new Canteen();
        assertNotNull(canteen);
    }

    @Test
    public void category() {
        assertNotNull(Category.class);
    }

    @Test
    public void dish() {
        assertNotNull(Dish.class);
        Dish dish = new Dish();
        Assert.assertNotNull(dish);
    }

    @Test
    public void manager() {
        Manager manager = new Manager();
        assertNotNull(manager);
    }

    @Test
    public void order() {
        Order order = new Order();
        Assert.assertNotNull(order);
    }

    @Test
    public void orderItem() {
        OrderItem orderItem = new OrderItem();
        Assert.assertNotNull(orderItem);
    }

    @Test
    public void prepaidCard() throws CatException {
        PrepaidCard prepaidCard = new PrepaidCard();
        Assert.assertNotNull(prepaidCard);
    }

    @Test
    public void stall() {
        Stall stall = new Stall();
        assertNotNull(stall);
    }

    @Test
    public void transaction() {
        Transaction transaction = new Transaction();
        assertNotNull(transaction);
    }
}
