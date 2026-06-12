package pingsa.chat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    // ===== Reset static arrays before every test =====
    @Before
    public void setUp() {
        Message.sentMessages.clear();
        Message.disregardedMessages.clear();
        Message.storedMessages.clear();
        Message.storedRecipients.clear();
        Message.storedIDs.clear();
        Message.storedHashes.clear();
        Message.messageHashes.clear();
        Message.messageIDs.clear();
        Message.totalMessages = 0;
    }

    // ===== Message ID tests =====
    @Test
    public void testMessageIDIsGenerated() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertNotNull(msg.getMessageID());
    }

    @Test
    public void testMessageIDLength() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertTrue(msg.checkMessageID());
    }

    // ===== Recipient validation tests =====
    @Test
    public void testValidRecipient() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient_noCountryCode() {
        Message msg = new Message(1, "0834557896", "Did you get the cake?");
        assertEquals("Cell phone number incorrectly formatted.", msg.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient_tooShort() {
        Message msg = new Message(1, "+2783455", "Did you get the cake?");
        assertEquals("Cell phone number incorrectly formatted.", msg.checkRecipientCell());
    }

    // ===== Message length tests =====
    @Test
    public void testMessageWithinLimit() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageTooLong() {
        String longMsg = new String(new char[251]).replace("\0", "A");
        Message msg = new Message(1, "+27834557896", longMsg);
        assertEquals("Please enter a message of less than 250 characters.", msg.checkMessageLength());
    }

    // ===== Message hash tests =====
    @Test
    public void testHashContainsFirstWord() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertTrue(msg.getMessageHash().contains("DID"));
    }

    @Test
    public void testHashContainsLastWord() {
        Message msg = new Message(1, "+27834557896", "Did you get the cake?");
        assertTrue(msg.getMessageHash().contains("CAKE?"));
    }

    // ===== Array population tests =====
    @Test
    public void testSentMessagesArrayPopulated() {
        Message.sentMessages.add("Did you get the cake?");
        assertEquals(1, Message.sentMessages.size());
        assertEquals("Did you get the cake?", Message.sentMessages.get(0));
    }

    @Test
    public void testDisregardedMessagesArrayPopulated() {
        Message.disregardedMessages.add("Yohoooo, I am at your gate.");
        assertEquals(1, Message.disregardedMessages.size());
        assertEquals("Yohoooo, I am at your gate.", Message.disregardedMessages.get(0));
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        Message.storedMessages.add("Where are you? You are late!");
        Message.storedRecipients.add("+27838884567");
        assertEquals(1, Message.storedMessages.size());
        assertEquals("+27838884567", Message.storedRecipients.get(0));
    }

    // ===== Search tests =====
    @Test
    public void testSearchByMessageID_found() {
        Message.storedMessages.add("Did you get the cake?");
        Message.storedRecipients.add("+27834557896");
        Message.storedIDs.add("1234567890");
        Message.storedHashes.add("12:1:DIDCAKE?");

        String result = Message.searchByMessageID("1234567890");
        assertTrue(result.contains("+27834557896"));
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchByMessageID_notFound() {
        assertEquals("Message ID not found.", Message.searchByMessageID("9999999999"));
    }

    @Test
    public void testSearchByRecipient_found() {
        Message.storedMessages.add("Did you get the cake?");
        Message.storedRecipients.add("+27834557896");
        Message.storedIDs.add("1234567890");
        Message.storedHashes.add("12:1:DIDCAKE?");

        String result = Message.searchByRecipient("+27834557896");
        assertTrue(result.contains("Did you get the cake?"));
    }

    @Test
    public void testSearchByRecipient_notFound() {
        assertEquals("No messages found for that recipient.",
                Message.searchByRecipient("+27800000000"));
    }

    // ===== Delete tests =====
    @Test
    public void testDeleteByHash_found() {
        Message.storedMessages.add("Did you get the cake?");
        Message.storedRecipients.add("+27834557896");
        Message.storedIDs.add("1234567890");
        Message.storedHashes.add("12:1:DIDCAKE?");

        assertEquals("Message successfully deleted.", Message.deleteByHash("12:1:DIDCAKE?"));
        assertEquals(0, Message.storedMessages.size());
    }

    @Test
    public void testDeleteByHash_notFound() {
        assertEquals("Hash not found.", Message.deleteByHash("00:0:FAKEHASH"));
    }

    // ===== Longest message test =====
    @Test
    public void testGetLongestStoredMessage() {
        Message.storedMessages.add("Hi");
        Message.storedMessages.add("Where are you? You are late! I have asked you to be on time.");
        String result = Message.getLongestStoredMessage();
        assertTrue(result.contains("Where are you?"));
    }

    // ===== Full report test =====
    @Test
    public void testFullStoredReport() {
        Message.storedMessages.add("Did you get the cake?");
        Message.storedRecipients.add("+27834557896");
        Message.storedIDs.add("1234567890");
        Message.storedHashes.add("12:1:DIDCAKE?");

        String report = Message.fullStoredReport();
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("+27834557896"));
    }
}