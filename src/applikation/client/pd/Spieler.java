package applikation.client.pd;

import java.util.List;
import java.util.Observable;

import pd.spieler.Figur;
import pd.spieler.SpielerFarbe;

public class Spieler extends Observable {
	private boolean amZug;
	private boolean hatAufgebeben;
	public pd.spieler.Spieler spieler;

	private Karten karten;

	public Spieler(pd.spieler.Spieler spieler) {
		this.spieler = spieler;
		this.amZug = false;
		this.hatAufgebeben = false;
		this.karten = new Karten(spieler.getKarten());
	}

	public boolean kannZiehen() {
		return spieler.kannZiehen();
	}

	public Karten getKarten() {
		return karten;
	}

	public pd.spieler.Spieler getSpieler() {
		return spieler;
	}

	public String getName() {
	    return spieler.getName();
    }

	public List<Figur> getFiguren() {
	    return spieler.getFiguren();
    }

	public SpielerFarbe getFarbe() {
		return spieler.getFarbe();
	}

	public Boolean getAmZug() {
		return amZug;
	}

	public void setAmZug(boolean amZug) {
		this.amZug = amZug;
		setChanged();
		notifyObservers(amZug);
	}

	public boolean getHatAufgebeben() {
    	return hatAufgebeben;
    }

	public void setHatAufgebeben(boolean hatAufgebeben) {
    	this.hatAufgebeben = hatAufgebeben;
		setChanged();
		notifyObservers();
    }

	public String toString() {
		return spieler.toString();
	}
}
