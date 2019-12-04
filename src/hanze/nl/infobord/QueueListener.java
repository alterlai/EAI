package hanze.nl.infobord;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class QueueListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //todo
    }
//TODO 	implementeer de messagelistener die het bericht ophaald en
//		doorstuurd naar verwerkBericht van het infoBord.
//		Ook moet setRegels aangeroepen worden.
}

