
package pingsa.chat;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PingSAchat {

    static String storedUsername  = "";
    static String storedPassword  = "";
    static String storedFirstName = "";
    static String storedLastName  = "";
    static int sentMessages  = 0;
    static int totalMessages = 0;

    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber    = password.matches(".*\\d.*");
        boolean hasSpecial   = password.matches(".*[!@#$%^&*()].*");
        return password.length() >= 8 && hasUppercase && hasNumber && hasSpecial;
    }

    public static boolean checkCellPhoneNumber(String cellPhone) {
        return Pattern.matches("^\\+27\\d{9}$", cellPhone);
    }

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

        storedUsername  = username;
        storedPassword  = password;
        storedFirstName = firstName;
        storedLastName  = lastName;

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
            System.out.println("3. Stored Messages");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    System.out.print("Enter recipient (+27...): ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter message: ");
                    String text = scanner.nextLine();

                
                    if (!recipient.matches("^\\+27\\d{9}$")) {
                        System.out.println("Cell number incorrectly formatted.");
                        break;
                    }
                    if (text.length() > 250) {
                        System.out.println("Message too long (max 250 characters).");
                        break;
                    }
                    Message msg = new Message(sentMessages + 1, recipient, text);
                    System.out.println(msg.checkRecipientCell());
                    System.out.println(msg.checkMessageLength());

                    String result = msg.sentMessage();
                    System.out.println(result);
                    msg.printMessageDetails();

                    sentMessages++;
                    break;

                case 2:
                    
                    if (Message.sentMessages.isEmpty()) {
                        System.out.println("No messages sent yet.");
                    } else {
                        System.out.println("\n===== Sent Messages =====");
                        for (int i = 0; i < Message.sentMessages.size(); i++) {
                            System.out.println((i + 1) + ". " + Message.sentMessages.get(i));
                        }
                    }
                    break;

                case 3:
               
                    storedMessagesMenu(scanner);
                    break;

                case 4:
                    System.out.println("Exiting chat, See ya!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }

        System.out.println("Message limit reached.");
    }

    public static void storedMessagesMenu(Scanner scanner) {

        System.out.println("\n===== STORED MESSAGES MENU =====");
        System.out.println("a. Display sender and recipient of all stored messages");
        System.out.println("b. Display the longest stored message");
        System.out.println("c. Search by message ID");
        System.out.println("d. Search messages for a recipient");
        System.out.println("e. Delete a message by message hash");
        System.out.println("f. Display full report of all stored messages");
        System.out.println("g. Read messages from JSON file");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {

            case "a":
                if (Message.storedRecipients.isEmpty()) {
                    System.out.println("No stored messages.");
                } else {
                    System.out.println("\n===== Senders and Recipients =====");
                    for (int i = 0; i < Message.storedRecipients.size(); i++) {
                        System.out.println("Sender   : " + storedFirstName + " " + storedLastName);
                        System.out.println("Recipient: " + Message.storedRecipients.get(i));
                        System.out.println("Message  : " + Message.storedMessages.get(i));
                        System.out.println("----------");
                    }
                }
                break;

            case "b":
                System.out.println(Message.getLongestStoredMessage());
                break;

            case "c":
                System.out.print("Enter message ID: ");
                String searchID = scanner.nextLine();
                System.out.println(Message.searchByMessageID(searchID));
                break;

            case "d":
                System.out.print("Enter recipient number (+27...): ");
                String searchRecipient = scanner.nextLine();
                System.out.println(Message.searchByRecipient(searchRecipient));
                break;

            case "e":
                System.out.print("Enter message hash: ");
                String hash = scanner.nextLine();
                System.out.println(Message.deleteByHash(hash));
                break;

            case "f":
                System.out.println(Message.fullStoredReport());
                break;

            case "g":
                System.out.println(Message.readStoredMessagesFromFile());
                break;

            default:
                System.out.println("Invalid option");
        }
    }
}