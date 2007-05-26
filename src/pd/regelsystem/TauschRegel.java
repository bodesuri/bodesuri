package pd.regelsystem;

import pd.brett.Feld;
import pd.spieler.Spieler;
import pd.zugsystem.TauschAktion;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

/**
 * Regel für das Tauschen zweier Figuren.
 */
public class TauschRegel extends Regel {
	/**
	 * Validiere Zugeingabe. Der Zug muss mit einer eigenen Figur und der eines
	 * anderen Spielers gemacht werden.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		Spieler spieler = zugEingabe.getSpieler();
		Feld start = zugEingabe.getBewegung().getStart();
		Feld ziel  = zugEingabe.getBewegung().getZiel();
		
		if (start.istFrei() || ziel.istFrei()) {
			throw new RegelVerstoss("Es müssen zwei Figuren getauscht werden.");
		}
		
		if (!(start.istBesetztVon(spieler) ^ ziel.istBesetztVon(spieler))) {
			throw new RegelVerstoss("Es muss eine eigene Figur und " +
			                        "eine andere getauscht werden.");
		}
		
		Zug zug = new Zug();
		zug.fuegeHinzu(new TauschAktion(start, ziel));
		return zug;
	}
}
