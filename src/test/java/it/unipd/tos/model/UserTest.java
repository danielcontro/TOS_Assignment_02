////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserTest {

    private User user;

    private String firstName, lastName;
    private int age;

    @Rule
    public ExpectedException thrown= ExpectedException.none();
    
    @Before
    public void setup() {
        firstName = "Mario";
        lastName = "Rossi";
        age = 21;

        user = new User(firstName, lastName, age);
    }

    @Test
    public void test_getFirstName_validUser_Calculated() {
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void test_getLastName_validUser_Calculated() {
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void test_getFullName_validUser_Calculated() {
        assertEquals(firstName + " " + lastName, user.getFullName());
    }

    @Test
    public void test_getAge_validUser_Calculated() {
        assertEquals(age, user.getAge());
    }

    @Test
    public void test_isUnderage_UserOverage_Calculated() {
        assertFalse(user.isUnderage());
    }

    @Test
    public void test_isUnderage_UserUnderage_Calculated() {
        User UnderageUser = new User(firstName, lastName, 17);
        assertTrue(UnderageUser.isUnderage());
    }
    
    @Test
    public void test_UserConstructor_FirstNameNull_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The first name cannot be null or empty");
        new User(null, lastName, age);
    }

    @Test
    public void test_UserConstructor_LastNameNull_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The last name cannot be null or empty");
        new User(firstName, null, age);
    }

    @Test
    public void test_UserConstructor_FirstNameEmpty_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The first name cannot be null or empty");
        new User("", lastName, age);
    }

    @Test
    public void test_UserConstructor_LastNameEmpty_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The last name cannot be null or empty");
        new User(firstName, "", age);
    }

    @Test
    public void test_UserConstructor_AgeNegative_ExceptionThrown() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The age cannot be negative");
        new User(firstName, lastName, -1);
    }

}
