
package pingsa.chat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;

    // ===== Arrays to store messages =====
    static ArrayList<String> sentMessages      = new ArrayList<>();
    static ArrayList<String> disregardedMessages = new ArrayList<>();
    static ArrayList<String> storedMessages    = new ArrayList<>();
    static ArrayList<String> messageHashes     = new ArrayList<>();
    static ArrayList<String> messageIDs        = new ArrayList<>();

    // Parallel lists to support searching by ID / recipient
    static ArrayList<String> storedRecipients  = new ArrayList<>();
    static ArrayList<String> storedIDs         = new ArrayList<>();
    static ArrayList<String> storedHashes      = new ArrayList<>();

    static int totalMessages = 0;

    // ===== Constructor =====
    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.message       = message;

        generateMessageID();
        this.messageHash = createMessageHash();
    }

    // ===== Generate 10-digit message ID =====
    public void generateMessageID() {
        Random random = new Random();
        long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        messageID = String.valueOf(number);
    }

    // ===== Validation =====
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

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Please enter a message of less than 250 characters.";
        }
    }

    // ===== Create message hash =====
    public String createMessageHash() {
        String[] words   = message.split(" ");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1];

        return messageID.substring(0, 2)
                + ":"
                + messageNumber
                + ":"
                + firstWord.toUpperCase()
                + lastWord.toUpperCase();
    }

    // ===== Send / Store / Disregard =====
    public String sentMessage() {

        Scanner input = new Scanner(System.in);

        System.out.println("\n1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Choose option: ");

        int option = input.nextInt();
        input.nextLine();

        switch (option) {

            case 1:
                sentMessages.add(message);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                totalMessages++;
                return "Message successfully sent.";

            case 2:
                storedMessages.add(message);
                storedRecipients.add(recipient);
                storedIDs.add(messageID);
                storedHashes.add(messageHash);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                storeMessage();          // Write to JSON file
                totalMessages++;
                return "Message successfully stored.";

            case 3:
                disregardedMessages.add(message);
                return "Message disregarded.";

            default:
                return "Invalid option.";
        }
    }

    // ===== Save message to JSON file =====
    public String storeMessage() {
        String json = "{\n"
                + "  \"messageID\": \""   + messageID   + "\",\n"
                + "  \"recipient\": \""   + recipient   + "\",\n"
                + "  \"message\": \""     + message     + "\",\n"
                + "  \"messageHash\": \"" + messageHash + "\"\n"
                + "}\n";

        try (FileWriter fw = new FileWriter("stored_messages.json", true)) {
            fw.write(json);
        } catch (IOException e) {
            return "Error saving message to file: " + e.getMessage();
        }
        return "Message successfully stored to file.";
    }

    // ===== Display full message details =====
    public void printMessageDetails() {
        System.out.println("\nMessage ID   : " + messageID);
        System.out.println("Message Hash : " + messageHash);
        System.out.println("Recipient    : " + recipient);
        System.out.println("Message      : " + message);
    }

    // ===== Display all sent messages =====
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append((i + 1)).append(". ").append(sentMessages.get(i)).append("\n");
        }
        return sb.toString();
    }

    // ===== Display longest stored message =====
    public static String getLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        String longest = "";
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return "Longest message: " + longest;
    }

    // ===== Search by message ID =====
    public static String searchByMessageID(String id) {
        for (int i = 0; i < storedIDs.size(); i++) {
            if (storedIDs.get(i).equals(id)) {
                return "Recipient: " + storedRecipients.get(i)
                        + "\nMessage: "  + storedMessages.get(i);
            }
        }
        return "Message ID not found.";
    }

    // ===== Search all messages for a recipient =====
    public static String searchByRecipient(String number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < storedRecipients.size(); i++) {
            if (storedRecipients.get(i).equals(number)) {
                sb.append("Message: ").append(storedMessages.get(i)).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages found for that recipient." : sb.toString();
    }

    // ===== Delete message by hash =====
    public static String deleteByHash(String hash) {
        int index = storedHashes.indexOf(hash);
        if (index == -1) {
            return "Hash not found.";
        }
        storedMessages.remove(index);
        storedRecipients.remove(index);
        storedIDs.remove(index);
        storedHashes.remove(index);
        return "Message successfully deleted.";
    }

    // ===== Full report of all stored messages =====
    public static String fullStoredReport() {
        if (storedMessages.isEmpty()) {
            return "No stored messages to display.";
        }
        StringBuilder sb = new StringBuilder("===== Stored Messages Report =====\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            sb.append("\n--- Message ").append(i + 1).append(" ---\n");
            sb.append("ID       : ").append(storedIDs.get(i)).append("\n");
            sb.append("Hash     : ").append(storedHashes.get(i)).append("\n");
            sb.append("Recipient: ").append(storedRecipients.get(i)).append("\n");
            sb.append("Message  : ").append(storedMessages.get(i)).append("\n");
        }
        return sb.toString();
    }

    // ===== Read JSON file into array and display =====
    public static String readStoredMessagesFromFile() {
        ArrayList<String> fileMessages = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("stored_messages.json"))) {
            String line;
            String currentID = "", currentRecipient = "", currentMessage = "", currentHash = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Parse each JSON field
                if (line.contains("\"messageID\"")) {
                    currentID = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"recipient\"")) {
                    currentRecipient = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"message\"")) {
                    currentMessage = line.substring(line.indexOf(":") + 1)
                            .replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"messageHash\"")) {
                    currentHash = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.equals("}")) {
                    // End of one message block — add to list
                    String entry = "ID: " + currentID
                            + " | Recipient: " + currentRecipient
                            + " | Message: " + currentMessage
                            + " | Hash: " + currentHash;
                    fileMessages.add(entry);
                    currentID = ""; currentRecipient = "";
                    currentMessage = ""; currentHash = "";
                }
            }

            if (fileMessages.isEmpty()) {
                return "No messages found in file.";
            }

            sb.append("===== Messages from JSON File =====\n");
            for (int i = 0; i < fileMessages.size(); i++) {
                sb.append((i + 1)).append(". ").append(fileMessages.get(i)).append("\n");
            }
            return sb.toString();

        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    // ===== Getters =====
    public int returnTotalMessages()  { return totalMessages; }
    public String getMessageHash()    { return messageHash;   }
    public String getMessageID()      { return messageID;     }
    public String getRecipient()      { return recipient;     }
    public String getMessage()        { return message;       }
}