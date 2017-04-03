package tech.milind.cleanwatercrowdsourcing;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.*;
import static org.junit.Assert.*;
import tech.milind.cleanwatercrowdsourcing.model.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final int TIMEOUT = 200;
    private Security security;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        security = new Security();
    }

    @Test(timeout = TIMEOUT)
    public void testNonexistentUser() {
        thrown.expect(NoSuchElementException.class);
        security.findUser("newuser");
    }

    @Test(timeout = TIMEOUT)
    public void testExistingUser() {
        security.addUser("test", "pass");
        User expected = new User("test", "pass");
        User found = security.findUser("test");
        assertEquals(expected.getUsername(), found.getUsername());
        assertEquals(expected.getName(), found.getName());
        assertEquals(expected.getHomeAddress(), found.getHomeAddress());
        assertEquals(expected.getEmailAddress(), found.getEmailAddress());
        assertEquals(expected.getUserType(), found.getUserType());
    }

    @Test(timeout = TIMEOUT)
    public void testRemovedUser() {
        thrown.expect(NoSuchElementException.class);
        security.addUser("test", "pass");
        User expected = new User("test", "pass");
        User found = security.findUser("test");
        assertEquals(expected.getUsername(), found.getUsername());
        assertEquals(expected.getName(), found.getName());
        assertEquals(expected.getHomeAddress(), found.getHomeAddress());
        assertEquals(expected.getEmailAddress(), found.getEmailAddress());
        assertEquals(expected.getUserType(), found.getUserType());
        security.removeUser("test");
        security.findUser("test");
    }

}