
import pingsa.chat.Message;



class MessageTest {

    public static void main(String[] args) {

        Message msg = new Message(1, "+27812345678", "Hello John");

        System.out.println(msg.getMessageID());
        System.out.println(msg.checkRecipientCell());
        System.out.println(msg.checkMessageLength());
        System.out.println(msg.getMessageHash());
        System.out.println(msg.sentMessage());
        System.out.println(msg.printMessages());
        System.out.println(msg.returnTotalMessages());
        System.out.println(msg.storeMessage());
    }
}
