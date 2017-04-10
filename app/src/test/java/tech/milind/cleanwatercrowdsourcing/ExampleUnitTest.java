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
    private Security security;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        security = new Security();
    }

    @Test
    public void testNonexistentUser() {
        thrown.expect(NoSuchElementException.class);
        security.findUser("e");
    }

    @Test
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

    @Test
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