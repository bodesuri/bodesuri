package ui.spiel.brett.felder;

import java.awt.Point;

import pd.brett.Feld;
import ui.ressourcen.Icons;

/**
 * Feld Bank, welches jede Spielfigur am Anfang besucht haben muss,
 * bevor er auf die weiteren normalen Feldern weiter ziehe kann
 */
public class SpielerFeld2d extends Feld2d {

	public SpielerFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter) {
		super(p, feld, mouseAdapter, Icons.FELD_BANK);
	}
	
	@Override
	public void setAusgewaehlt(boolean istAusgewaehlt) {
		if (istAusgewaehlt) {
			icon = Icons.FELD_AUSWAHL;
			setIcon(icon);
		} else {
			icon = Icons.FELD_BANK;
			setIcon(icon);
		}
		updateUI();
	}
}