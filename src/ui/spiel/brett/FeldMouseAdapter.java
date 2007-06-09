package ui.spiel.brett;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import ui.GUIController;
import applikation.client.controller.Controller;

/**
 * MouseEventListener, der auf die Klicks der Felder achtet.
 */
public class FeldMouseAdapter extends MouseAdapter {
	Controller controller;
	private HashMap<Feld2d, Figur2d> felder = new HashMap<Feld2d, Figur2d>();
	private Boolean aktiv;

	public FeldMouseAdapter(GUIController controller) {
		this.controller = controller;
		controller.registriereFeldMouseAdapter(this);
		aktiv = false;
	}

	/**
	 * Das {@link Feld2d} auf das geklickt wird an den Controller weiterleiten
	 * und falls eine {@link Figur2d} auf dem Feld ist, diese als ausgewählt
	 * markieren.
	 *
	 * @param e
	 *            MouseEvent der das angeklickte Feld enthält
	 */
	public void mouseClicked(MouseEvent e) {
		if (aktiv) {
			controller.feldGewaehlt(((Feld2d) e.getComponent()).feld);

			// TODO: Falls die Figur schon gewählt ist, wieder ent-wählen
			// TODO: sollten solche Logik nicht im automaten passieren? (-reto)
			Figur2d figur = getFigur2d(((Feld2d) e.getComponent()));
			if (figur != null) {
				figur.setzeAusgewaehlt();
			}
		}
	}

	public void addFigur(Feld2d feld2d, Figur2d figur2d) {
		this.felder.put(feld2d, figur2d);

	}

	public Figur2d getFigur2d(Feld2d feld2d) {
		return this.felder.get(feld2d);
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
    }
}
