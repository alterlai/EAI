package hanze.nl.infobord;

public class Runner {
	
	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

    public static void main(String[] args) throws Exception {
    	//TODO vul hier de juiste selector in.
    	thread(new ListenerStarter("(HALTE  = 'G') AND (RICHTING = '1')"),false);
    }
}
