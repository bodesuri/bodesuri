package ui.spiel.karten;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.GUIController;
import applikation.client.controller.Controller;

public class KarteMouseAdapter extends MouseAdapter {
	private Controller controller;

	private Boolean aktiv;

	private KartenAuswahlView kartenAuswahlView;
	private KartenAuswahl kartenAuswahl;

	public KarteMouseAdapter(GUIController controller,
	                         KartenAuswahlView kartenAuswahlView,
	                         KartenAuswahl kartenAuswahl) {
		this.controller = controller;
		this.kartenAuswahlView = kartenAuswahlView;
		this.kartenAuswahl = kartenAuswahl;

		controller.registriereKarteMouseAdapter(this);
		aktiv = false;
	}

	public void mouseClicked(MouseEvent evt) {
		if (aktiv) {
			KarteView karteView = (KarteView) evt.getComponent();
			kartenAuswahl.setPosition(karteView.getPosition());
			
			kartenAuswahlView.getDeckView().add(kartenAuswahl);
			kartenAuswahlView.getDeckView().updateUI();
			
			kartenAuswahlView.getKarteGewaehltView().zeigeKarte(karteView.getKarte());
			
			controller.karteGewaehlt(karteView.getKarte());
		}
	}

	public void aktiv(Boolean aktiv) {
		this.aktiv = aktiv;
		if (!aktiv) {
			kartenAuswahlView.getDeckView().remove(kartenAuswahl);
			kartenAuswahlView.getDeckView().updateUI();
			
			kartenAuswahlView.getKarteGewaehltView().zeigeKeineKarte();
		}
	}
}