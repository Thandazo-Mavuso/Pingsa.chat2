
package pingsa.chat;

public class Login {
    private String storedUsername;
    private String storedPassword;
    private String storedFirstName;
    private String storedLastName;
    public void storeUserDetails(String username, String password, String firstName, String lastName) {
        this.storedUsername = username;
        this.storedPassword = password;
        this.storedFirstName = firstName;
        this.storedLastName = lastName;
    }

    public boolean checkLogin(String username, String password) {
        if (storedUsername == null) {
            return false;
        }
        return storedUsername.equals(username) && storedPassword.equals(password);
    }

    public String returnLoginStatus(String username, String password) {
        if (checkLogin(username, password)) {
            return "Welcome " + storedFirstName + ", " + storedLastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
