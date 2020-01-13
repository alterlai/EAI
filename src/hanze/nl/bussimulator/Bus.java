package hanze.nl.bussimulator;

import hanze.nl.bussimulator.Halte.Positie;

public class Bus{

	private Bedrijven bedrijf;
	private Producer producer;
	private Lijnen lijn;
	private int halteNummer;
	private int tijdTotVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;
	
	Bus(Lijnen lijn, Bedrijven bedrijf, int richting, Producer producer){
		this.lijn=lijn;
		this.producer = producer;
		this.bedrijf=bedrijf;
		this.richting=richting;
		this.halteNummer = -1;
		this.tijdTotVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}
	
	void setbusID(int starttijd){
		this.busID=starttijd+lijn.name()+richting;
	}
	
	private void gaNaarVolgendeHalte(){
		Positie volgendeHalte = lijn.getHalte(halteNummer+richting).getPositie();
		tijdTotVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}

	boolean isBijEindHalte() {
		return (halteNummer>=lijn.getLengte()-1) || (halteNummer == 0);
	}

	void printOnRouteInformatie() {
		if (isBijEindHalte()) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n",
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
		}
		if (halteNummer == -1 ) {
			System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n",
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
		}
		else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n",
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer));
		}
	}
	
	private void start() {
		halteNummer = (richting==1) ? 0 : lijn.getLengte()-1;
		gaNaarVolgendeHalte();
	}
	
	void move() {
		bijHalte=false;
		if (halteNummer == -1) {
			start();
		}
		else {
			tijdTotVolgendeHalte--;
			if (tijdTotVolgendeHalte == 0) {
				halteNummer += richting;
				gaNaarVolgendeHalte();
			}
		}
	}
	
	void sendETAs(int nu){
		Bericht bericht = new Bericht().forBusAtTime(this, nu);
		producer.sendMessageToBroker(bericht);
	}
	
	void sendLastETA(int nu){
		Bericht bericht = new Bericht().lastEtaForBusAtTime(this, nu);
		producer.sendMessageToBroker(bericht);
	}

	Bedrijven getBedrijf() {
		return bedrijf;
	}

	Lijnen getLijn() {
		return lijn;
	}

	int getHalteNummer() {
		return halteNummer;
	}

	int getTijdTotVolgendeHalte() {
		return tijdTotVolgendeHalte;
	}

	int getRichting() {
		return richting;
	}

	boolean isBijHalte() {
		return bijHalte;
	}

	String getBusID() {
		return busID;
	}
}
