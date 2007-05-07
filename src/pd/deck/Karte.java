package pd.deck;

import dienste.serialisierung.CodierbaresObjekt;
import pd.regelsystem.Regel;

public class Karte extends CodierbaresObjekt {
	private static final long serialVersionUID = 1L;
	
	private int wert;
	protected KartenFarbe farbe;
	private Regel regel;

	public Karte() { }
	
	public Karte(KartenFarbe farbe) {
		this.farbe = farbe;
	}
	
	public String toString() {
		return this.getClass().getSimpleName();
	}

    public String getCode() {
	    return "Karte " + farbe.getClass().getSimpleName() + wert;
    }

	public void setWert(int wert) {
		this.wert = wert;
	}

	public int getWert() {
		return wert;
	}

	public void setKartenFarbe(KartenFarbe kartenFarbe) {
		farbe = kartenFarbe;
	}

	public KartenFarbe getKartenFarbe() {
		return farbe;
	}

	public Regel getRegel() {
		return regel;
	}

	protected void setRegel(Regel regel) {
		this.regel = regel;
	}
}
