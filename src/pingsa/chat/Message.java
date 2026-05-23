
package pingsa.chat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    static ArrayList<String> sentMessages =
            new ArrayList<>();

    static int totalMessages = 0;

    public Message(int messageNumber,
                   String recipient,
                   String message) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;

        generateMessageID();

        this.messageHash = createMessageHash();
    }

    // Generate 10-digit ID
    public void generateMessageID() {

        Random random = new Random();

        long number =
                1000000000L +
                (long)(random.nextDouble()
                * 9000000000L);

        messageID = String.valueOf(number);
    }

    public boolean checkMessageID() {

        return messageID.length() == 10;
    }

    public String checkRecipientCell() {

        if (recipient.matches("^\\+27\\d{9}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number incorrectly formatted.";
        }
    }

    // Check message length
    public String checkMessageLength() {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            return "Please enter a message of less than 250 characters.";
        }
    }

    public String createMessageHash() {

        String[] words = message.split(" ");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    // Send/store/disregard
    public String sentMessage() {

        Scanner input = new Scanner(System.in);

        System.out.println("\n1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");

        int option = input.nextInt();
        input.nextLine();

        switch (option) {

            case 1:

                sentMessages.add(message);

                totalMessages++;

                return "Message successfully sent.";

            case 2:

                return "Message successfully stored.";

            case 3:

                return "Press 0 to delete message.";

            default:

                return "Invalid option.";
        }
    }

    // Print full message details
    public void printMessageDetails() {

        System.out.println("\nMessage ID: " + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + message);
    }

    // Print sent messages
    public String printMessages() {

        return sentMessages.toString();
    }

    // Return total messages
    public int returnTotalMessages() {

        return totalMessages;
    }

    // Store message in JSON format
    public String storeMessage() {

        return "{ \"message\": \"" + message + "\" }";
    }

    // Get message hash
    public String getMessageHash() {

        return messageHash;
    }

    // Get message ID
    public String getMessageID() {

        return messageID;
    }

    public String SentMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
