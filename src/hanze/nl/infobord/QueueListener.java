package hanze.nl.infobord;

import java.io.IOException;

import javax.jms.*;

public class QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println(text);
                InfoBord infobord = InfoBord.getInfoBord();
                infobord.verwerkBericht(text);
                infobord.setRegels();
            } else {
                System.out.println("Consumer): Received non-Textmessage object");
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

