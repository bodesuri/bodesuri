package ui.spiel.brett.felder;

import java.awt.Point;

import javax.swing.Icon;

import ui.ressourcen.Icons;
import ui.spiel.brett.FigurenManager;
import applikation.client.pd.Feld;

/**
 * JLabel, wird zur Darstellung der normalen Felder verwendet.
 */
public class NormalesFeld2d extends Feld2d {

	public NormalesFeld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, FigurenManager figurenManager) {
		super(p, feld, mouseAdapter, Icons.FELD_NORMAL, figurenManager);
	}


	public Icon getAktivesIcon() {
		return Icons.FELD_AUSWAHL;
	}

	public Icon getPassivesIcon() {
		return Icons.FELD_NORMAL;

	}
}
