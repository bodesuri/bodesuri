package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Karte auswählen muss. Wenn der
 * {@link KarteGewaehltEvent} eintritt, wird der Zustand {@link StartWaehlen}
 * aufgerufen.
 */
public class KarteWaehlen extends ClientZugZustand {
	public KarteWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void entry() {
		controller.aktiviereKarte(true);
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;
		return StartWaehlen.class;
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		controller.zeigeFehlermeldung("Hobla, jetzt musst du zuerst eine Karte wählen, versuchs nochmals!");
		return this.getClass();
	}
}
