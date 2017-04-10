package tech.milind.cleanwatercrowdsourcing;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import tech.milind.cleanwatercrowdsourcing.model.Model;
import tech.milind.cleanwatercrowdsourcing.model.User;
import static org.junit.Assert.assertEquals;


/**
 * Created by SuperLinguini on 4/9/2017.
 */

public class MilindLoginUnitTest {
    private Model model;

    @Before
    public void setUp() {
        model = Model.getInstance();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNonexistentUserLogin() {
        model.login("username", "password");
    }

    @Test(expected = NoSuchElementException.class)
    public void testWrongPasswordLogin() {
        model.register("username", "password");
        model.login("username", "wrongpassword");
    }

    @Test
    public void testExistingUserLogin() {
        model.register("username", "password");
        User expected = new User("username", "password");
        model.login("username", "password");
        User currentUser = model.getCurrentUser();
        assertEquals(expected.getUsername(), currentUser.getUsername());
        assertEquals(expected.getName(), currentUser.getName());
        assertEquals(expected.getHomeAddress(), currentUser.getHomeAddress());
        assertEquals(expected.getEmailAddress(), currentUser.getEmailAddress());
        assertEquals(expected.getUserType(), currentUser.getUserType());
    }

    @Test
    public void testMultipleUserLogin() {
        model.register("onepunch", "man");
        model.register("awesomedude24", "12345");
        model.register("goodcop", "badpassword");
        User expected = new User("onepunch", "man");
        model.login("onepunch", "man");
        User currentUser = model.getCurrentUser();
        assertEquals(expected.getUsername(), currentUser.getUsername());
        assertEquals(expected.getName(), currentUser.getName());
        assertEquals(expected.getHomeAddress(), currentUser.getHomeAddress());
        assertEquals(expected.getEmailAddress(), currentUser.getEmailAddress());
        assertEquals(expected.getUserType(), currentUser.getUserType());

        expected = new User("awesomedude24", "12345");
        model.login("awesomedude24", "12345");
        currentUser = model.getCurrentUser();
        assertEquals(expected.getUsername(), currentUser.getUsername());
        assertEquals(expected.getName(), currentUser.getName());
        assertEquals(expected.getHomeAddress(), currentUser.getHomeAddress());
        assertEquals(expected.getEmailAddress(), currentUser.getEmailAddress());
        assertEquals(expected.getUserType(), currentUser.getUserType());

        expected = new User("goodcop", "badpassword");
        model.login("goodcop", "badpassword");
        currentUser = model.getCurrentUser();
        assertEquals(expected.getUsername(), currentUser.getUsername());
        assertEquals(expected.getName(), currentUser.getName());
        assertEquals(expected.getHomeAddress(), currentUser.getHomeAddress());
        assertEquals(expected.getEmailAddress(), currentUser.getEmailAddress());
        assertEquals(expected.getUserType(), currentUser.getUserType());
    }
}
