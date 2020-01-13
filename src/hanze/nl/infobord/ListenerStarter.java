package hanze.nl.infobord;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import java.util.HashSet;
import java.util.Set;

public class ListenerStarter implements Runnable, ExceptionListener {
    private boolean queue = true;
    private boolean getAll = false;
    private static int counter = 0;
    private int id;
    private String selector;

    public ListenerStarter(String selector) {
        this.selector = selector;
        this.id = counter++;
    }

    @Override
    public void run() {
        InfoBord infobord = InfoBord.getInfoBord();
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

            // Create a Connection
            ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Get all available topics
            DestinationSource ds = connection.getDestinationSource();
            Destination destination = session.createTopic("INFOBORD.JSON");
            MessageConsumer consumer = session.createConsumer(destination, selector);
            consumer.setMessageListener(new QueueListener());

        } catch (Exception e){

        }
}

    @Override
    public void onException(JMSException e) {
        System.err.println("Fout bij het starten van de Listener");
        e.printStackTrace();
    }
//TODO	Implementeer de starter voor de messagelistener:
//		Zet de verbinding op met de messagebroker en start de listener met 
//		de juiste selector.

}