package hanze.nl.bussimulator;

import java.util.Date;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public Producer() {
        try {

            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

            // Create a Connection
            this.connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("BusMessages");

            // Create a MessageProducer from the Session to the Topic or Queue
            this.producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        } catch (Exception e) {
            System.err.println("Unable to connect to broker");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.session.close();
            this.connection.close();
        } catch (JMSException e) {
            System.err.println("unable to disconnect from broker.");
            e.printStackTrace();
        }
    }

    public void sendMessageToBroker(Bericht message) {
        try {
            this.producer.send(this.session.createTextMessage(message.getXML()));
        } catch (Exception e) {
            System.err.println("An error occured while sending XML message");
            e.printStackTrace();
        }
    }
}
