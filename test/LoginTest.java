package pingsa.chat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testCheckLogin() {

        Login login = new Login();

        login.storeUserDetails("admin", "1234", "John", "Doe");

        boolean result = login.checkLogin("admin", "1234");

        assertTrue(result);
    }
}
