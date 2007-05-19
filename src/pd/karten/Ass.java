/**
 * @(#) Ass.java
 */

package pd.karten;

import pd.regelsystem.RegelVeroderung;
import pd.regelsystem.StartRegel;
import pd.regelsystem.VorwaertsRegel;

public class Ass extends Karte{
	private static final long serialVersionUID = 1L;

	public Ass(KartenFarbe farbe, int deck) {
		super("Ass", farbe, deck);
		RegelVeroderung regelVeroderung = new RegelVeroderung();
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(1));
		regelVeroderung.fuegeHinzu(new VorwaertsRegel(11));
		regelVeroderung.fuegeHinzu(new StartRegel());
		setRegel(regelVeroderung);
		/* TODO: ein weiteres Attribut hinzufügen oder setWert(string)*/
		setWert(1);
	}
}
