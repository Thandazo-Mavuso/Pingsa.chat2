
package pingsa.chat;
  
import java.util.Scanner;
import java.util.regex.Pattern;

public class PingSAchat {

    
    static String storedUsername = "";
    static String storedPassword = "";
    static String storedFirstName = "";
    static String storedLastName = "";

    
    static int sentMessages = 0;
    static int totalMessages = 0;

    
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()].*");

        return password.length() >= 8 && hasUppercase && hasNumber && hasSpecial;
    }

    public static boolean checkCellPhoneNumber(String cellPhone) {
        return Pattern.matches("^\\+27\\d{9}$", cellPhone);
    }

    // ===== REGISTER =====
    public static String registerUser(String username,
                                      String password,
                                      String cellPhone,
                                      String firstName,
                                      String lastName) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted";
        }

        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell phone number incorrectly formatted";
        }

        storedUsername = username;
        storedPassword = password;
        storedFirstName = firstName;
        storedLastName = lastName;

        return "User successfully registered";
    }

    
    public static boolean loginUser(String username, String password) {
        return username.equals(storedUsername)
                && password.equals(storedPassword);
    }

    public static String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome " + storedFirstName + " " + storedLastName
                    + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

   
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== Welcome to PingSA.chat =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    System.out.print("Enter cellphone (+27...): ");
                    String cellPhone = scanner.nextLine();

                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();

                    System.out.println(
                            registerUser(username, password, cellPhone, firstName, lastName)
                    );
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    boolean loginStatus = loginUser(loginUsername, loginPassword);

                    System.out.println(returnLoginStatus(loginStatus));

                    if (loginStatus) {
                        chatSystem(scanner);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using PingSA.chat");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    
    public static void chatSystem(Scanner scanner) {

        System.out.print("How many messages do you want to send? ");
        totalMessages = scanner.nextInt();
        scanner.nextLine();

        sentMessages = 0;

        while (sentMessages < totalMessages) {

            System.out.println("\n===== CHAT MENU =====");
            System.out.println("1. Send Message");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:

                    System.out.print("Enter recipient (+27...): ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter message: ");
                    String text = scanner.nextLine();

                    if (!recipient.startsWith("+27") || recipient.length() != 12) {
                        System.out.println("Cell number incorrectly formatted.");
                        break;
                    }

                    if (text.length() > 250) {
                        System.out.println("Message too long (max 250 characters).");
                        break;
                    }

                    System.out.println("Message successfully sent!");
                    System.out.println("To: " + recipient);
                    System.out.println("Message: " + text);

                    sentMessages++;
                    break;

                case 2:
                    System.out.println("Coming Soon ");
                    break;

                case 3:
                    System.out.println("Exiting chat, Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }

        System.out.println("Message limit reached.");
    }
}