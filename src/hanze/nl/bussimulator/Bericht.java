package hanze.nl.bussimulator;

import java.util.ArrayList;

public class Bericht {
	String lijnNaam;
	String eindpunt;
	String bedrijf;
	String busID;
	int tijd;
	ArrayList<ETA> ETAs;
	
	Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<ETA>();
	}


	public String getXML() {
		StringBuilder message = new StringBuilder("<Bericht>\n"
				+ "<lijnNaam>" + this.lijnNaam + "</lijnNaam>"
				+ "<eindpunt>" + this.eindpunt + "</eindpunt>"
				+ "<bedrijf>" + this.bedrijf + "</bedrijf>"
				+ "<busID>" + this.busID + "</busID>"
				+ "<tijd>" + this.tijd + "</tijd>"
				+ "<ETAs>");
		for(ETA eta : this.ETAs) {
			message.append("<ETA>")
					.append("<halteNaam>").append(eta.halteNaam)
					.append("</halteNaam>")
					.append("<richting>").append(eta.richting).append("</richting>")
					.append("<aankomsttijd>").append(eta.aankomsttijd).append("</aankomsttijd>")
					.append("</ETA>");
		}
		message.append("</ETAs>" + "</Bericht>");
		return message.toString();
	}
}
