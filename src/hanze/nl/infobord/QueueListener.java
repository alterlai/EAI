package hanze.nl.infobord;

import java.io.IOException;

import javax.jms.*;

public class QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            boolean newMessage=true;
            //           consumer.setMessageListener(new HelloWorldListener("Consumer"));
            // Wait for a message
            while (newMessage) {
                newMessage = false;
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;

                    String text = textMessage.getText();
                    InfoBord.verwerkBericht(text);
                    InfoBord.getInfoBord().setRegels();
                    newMessage = true;
                } else {
                    System.out.println("Consumer): Received non-Textmessage object");
                }
            }
        } catch (Exception e) {
            System.err.println("Error while consuming message");
            e.printStackTrace();
        }
    }
//TODO 	implementeer de messagelistener die het bericht ophaald en
//		doorstuurd naar verwerkBericht van het infoBord.
//		Ook moet setRegels aangeroepen worden.
}

