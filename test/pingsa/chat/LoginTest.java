package pingsa.chat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    Login login;

    // ===== Create a fresh Login object before every test =====
    @Before
    public void setUp() {
        login = new Login();
        login.storeUserDetails("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
    }

    // ===== Test login with correct details =====
    @Test
    public void testLoginSuccess() {
        boolean result = login.checkLogin("kyl_1", "Ch&&sec@ke99!");
        assertTrue(result);
    }

    // ===== Test login with wrong password =====
    @Test
    public void testLoginWrongPassword() {
        boolean result = login.checkLogin("kyl_1", "wrongpassword");
        assertFalse(result);
    }

    // ===== Test login with wrong username =====
    @Test
    public void testLoginWrongUsername() {
        boolean result = login.checkLogin("wrong_user", "Ch&&sec@ke99!");
        assertFalse(result);
    }

    // ===== Test login with both wrong =====
    @Test
    public void testLoginBothWrong() {
        boolean result = login.checkLogin("abc_1", "WrongPass1!");
        assertFalse(result);
    }

    // ===== Test login status message when successful =====
    @Test
    public void testReturnLoginStatusSuccess() {
        String status = login.returnLoginStatus("kyl_1", "Ch&&sec@ke99!");
        assertTrue(status.contains("Kyle"));
        assertTrue(status.contains("Smith"));
        assertTrue(status.contains("great to see you again"));
    }

    // ===== Test login status message when failed =====
    @Test
    public void testReturnLoginStatusFailed() {
        String status = login.returnLoginStatus("kyl_1", "wrongpassword");
        assertEquals("Username or password incorrect, please try again.", status);
    }

    // ===== Test login before any user is stored =====
    @Test
    public void testLoginBeforeUserStored() {
        Login emptyLogin = new Login();
        boolean result = emptyLogin.checkLogin("kyl_1", "Ch&&sec@ke99!");
        assertFalse(result);
    }
}