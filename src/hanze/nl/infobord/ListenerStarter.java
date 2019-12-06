package hanze.nl.infobord;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public  class ListenerStarter implements Runnable, ExceptionListener {
    private boolean queue=true;
    private boolean getAll=false;
    private static int counter=0;
    private int id;
    private String type="queue";

    public ListenerStarter(Boolean queue, boolean getAll) {
        this.queue=queue;
        this.type = (queue) ? "queue" : "topic";
        this.getAll=getAll;
        this.id=counter++;
    }

    @Override
    public void run() {
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = (queue) ? session.createQueue("Infoborden") :
                    session.createTopic("TOPIC.FOO");

            // Create a MessageConsumer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);

            boolean newMessage=true;
            //           consumer.setMessageListener(new HelloWorldListener("Consumer"));
            // Wait for a message
            while (newMessage) {
                Message message = consumer.receive(2000);

                newMessage=false;
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Consumer("+type+"): " + id + " Received: " + text);
                    newMessage=true&&getAll;
                } else {
                    System.out.println("Consumer("+type+"): " + id + " Received: " + message);
                }
            }
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
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