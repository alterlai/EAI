package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.persistence.XmlMap;

import java.util.ArrayList;

public class Bericht {
    private String lijnNaam;
    private String eindpunt;
    private String bedrijf;
    private String busID;
    private int tijd;
    private ArrayList<ETA> ETAs;

    Bericht() {
        this.eindpunt = "";
        this.ETAs = new ArrayList<>();
    }

    Bericht forBusAtTime(Bus bus, int nu) {
        int i;
        this.lijnNaam = bus.getLijn().name();
        this.bedrijf = bus.getBedrijf().name();
        this.busID = bus.getBusID();
		this.tijd = nu;
        if (bus.isBijHalte()) {
            ETA eta = new ETA(
                    bus.getLijn().getHalte(bus.getHalteNummer()).name(),
                    bus.getLijn().getRichting(bus.getHalteNummer()),
                    0);
            this.ETAs.add(eta);
        }
        Halte.Positie eerstVolgende = bus.getLijn().getHalte(bus.getHalteNummer() + bus.getRichting()).getPositie();
        int tijdNaarHalte = bus.getTijdTotVolgendeHalte() + nu;
        for (i = bus.getHalteNummer() + bus.getRichting(); !(i >= bus.getLijn().getLengte()) && !(i < 0); i = i + bus.getRichting()) {
            tijdNaarHalte += bus.getLijn().getHalte(i).afstand(eerstVolgende);
            ETA eta = new ETA(bus.getLijn().getHalte(i).name(), bus.getLijn().getRichting(i), tijdNaarHalte);
            this.ETAs.add(eta);
            eerstVolgende = bus.getLijn().getHalte(i).getPositie();
        }
        this.eindpunt = bus.getLijn().getHalte(i - bus.getRichting()).name();
        return this;
    }

    Bericht lastEtaForBusAtTime(Bus bus, int nu) {
		this.lijnNaam = bus.getLijn().name();
		this.bedrijf = bus.getBedrijf().name();
		this.busID = bus.getBusID();
		this.tijd = nu;
		String eindpunt = bus.getLijn().getHalte(bus.getHalteNummer()).name();
		ETA eta = new ETA(eindpunt, bus.getLijn().getRichting(bus.getHalteNummer()), 0);
		this.ETAs.add(eta);
		this.eindpunt = eindpunt;
		return this;
	}

    public String getXMLJackson() {
        XStream xStream = new XStream();
        xStream.alias("ETA", ETA.class);
        xStream.alias("Bericht", Bericht.class);
		return xStream.toXML(this);
    }
}
