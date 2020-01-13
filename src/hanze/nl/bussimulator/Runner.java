package hanze.nl.bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Runner {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	
	private static void addBusToBusStartAndSetID(int starttijd, Bus bus){
		ArrayList<Bus> bussen = new ArrayList<Bus>();
		// Als er al bussen op die starttijd beginnen, haal die op
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}
	
	private static int startBussen(int tijd){
		for (Bus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}
	
	public static void moveBussen(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			if(!bus.isBijEindHalte()) {
				bus.move();
				bus.printOnRouteInformatie();
			}
			else {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}
	
	public static void initBussen(){
		Producer producer = new Producer();

		Bus bus1=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1, producer);
		Bus bus2=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, 1, producer);
		Bus bus3=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, 1, producer);
		Bus bus4=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1, producer);
		Bus bus5=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1, producer);
		Bus bus6=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, 1, producer);
		Bus bus7=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, 1, producer);
		Bus bus8=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, 1, producer);
		Bus bus9=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, 1, producer);
		Bus bus10=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, 1, producer);
		addBusToBusStartAndSetID(3, bus1);
		addBusToBusStartAndSetID(5, bus2);
		addBusToBusStartAndSetID(4, bus3);
		addBusToBusStartAndSetID(6, bus4);
		addBusToBusStartAndSetID(3, bus5);
		addBusToBusStartAndSetID(5, bus6);
		addBusToBusStartAndSetID(4, bus7);
		addBusToBusStartAndSetID(6, bus8);
		addBusToBusStartAndSetID(12, bus9);
		addBusToBusStartAndSetID(10, bus10);
		Bus bus11=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1, producer);
		Bus bus12=new Bus(Lijnen.LIJN2, Bedrijven.ARRIVA, -1, producer);
		Bus bus13=new Bus(Lijnen.LIJN3, Bedrijven.ARRIVA, -1, producer);
		Bus bus14=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1, producer);
		Bus bus15=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1, producer);
		Bus bus16=new Bus(Lijnen.LIJN6, Bedrijven.QBUZZ, -1, producer);
		Bus bus17=new Bus(Lijnen.LIJN7, Bedrijven.QBUZZ, -1, producer);
		Bus bus18=new Bus(Lijnen.LIJN1, Bedrijven.ARRIVA, -1, producer);
		Bus bus19=new Bus(Lijnen.LIJN4, Bedrijven.ARRIVA, -1, producer);
		Bus bus20=new Bus(Lijnen.LIJN5, Bedrijven.FLIXBUS, -1, producer);
		addBusToBusStartAndSetID(3, bus11);
		addBusToBusStartAndSetID(5, bus12);
		addBusToBusStartAndSetID(4, bus13);
		addBusToBusStartAndSetID(6, bus14);
		addBusToBusStartAndSetID(3, bus15);
		addBusToBusStartAndSetID(5, bus16);
		addBusToBusStartAndSetID(4, bus17);
		addBusToBusStartAndSetID(6, bus18);
		addBusToBusStartAndSetID(12, bus19);
		addBusToBusStartAndSetID(10, bus20);
	}


	
	public static void main(String[] args) throws InterruptedException {
		int tijd=0;
		System.out.println("Init bussen");
		initBussen();
		int volgende = Collections.min(busStart.keySet());
		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			System.out.println("De tijd is:" + tijd);
			volgende = (tijd==volgende) ? startBussen(tijd) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			Thread.sleep(interval);
			tijd++;
		}
	}
}
