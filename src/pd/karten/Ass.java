package pd.karten;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.StartRegel;
import pd.regelsystem.VorwaertsRegel;

public class Ass extends Karte {
	public Ass(KartenFarbe farbe, int deck) {
		super("Ass", farbe, deck);
		RegelVeroderung r = new RegelVeroderung();
		r.fuegeHinzu(new VorwaertsRegel(1));
		r.fuegeHinzu(new VorwaertsRegel(11));
		r.fuegeHinzu(new StartRegel());
		setRegel(r);
	}
}
