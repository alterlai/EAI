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
            Set<ActiveMQQueue> queues = ds.getQueues();
            Destination destination = session.createTopic("INFOBORD.JSON");
            MessageConsumer consumer = session.createConsumer(destination, selector);
            consumer.setMessageListener(new QueueListener());

        } catch (Exception e){

        }
//            for (ActiveMQQueue queue: queues) {
//                if (queue.getQueueName().startsWith("LIJN")) {
//                    // Create the destination (Topic or Queue)
//                    Destination destination = session.createQueue(queue.getQueueName());
//
//                    // Create a MessageConsumer from the Session to the Topic or Queue
//                    MessageConsumer consumer = session.createConsumer(destination);
//
//                    consumer.setMessageListener(new QueueListener());
//                    System.out.println("Listener for " + queue.getQueueName() + "gestart");
//                }
//            }
//            System.out.println("Closing session");
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