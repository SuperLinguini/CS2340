package tech.milind.cleanwatercrowdsourcing.model;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import tech.milind.cleanwatercrowdsourcing.model.Security;
import tech.milind.cleanwatercrowdsourcing.model.User;

/**
 * Created by gunoupark on 03/04/2017.
 */
public class SecurityTest {
    Security security;
    String username;
    String password;

    @Before
    public void setUp() throws Exception {
        security = new Security();
        username = "new_user";
        password = "password";
    }

    @Test
    public void addUser() throws Exception {
        User user1 = security.addUser(username, password);
        assertEquals(security.findUser(username), user1);
        try {
            security.addUser(username, password);
            fail("Expected an NoSuchElementException to be thrown");
        }
        catch (NoSuchElementException e) {
            assertEquals("User already existed", e.getMessage());
        }
        try {
            security.addUser(username, "");
            fail("Expected an NoSuchElementException to be thrown");
        }
        catch (NoSuchElementException e) {
            assertEquals("User already existed", e.getMessage());
        }
        try {
            security.addUser("", "");
            fail("Expected an NoSuchElementException to be thrown");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Username must contain at least one character. Please try again.",
                    e.getMessage());
        }
        User user2 = security.addUser("another_user", "");
        assertEquals(security.findUser("another_user"), user2);
    }

}