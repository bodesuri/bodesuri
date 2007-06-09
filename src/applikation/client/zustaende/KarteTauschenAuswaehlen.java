package applikation.client.zustaende;

import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.KartenTausch;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in dem wir warten bis der Spieler die Karte die er tauschen möchte
 * ausgewählt hat. Tritt {@link KarteGewaehltEvent} ein gehen wir in den Zustand
 * {@link KarteTauschenBekommen} über.
 */
public class KarteTauschenAuswaehlen extends ClientZustand {
	public void entry() {
		controller.kartenAuswahl(true);
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		controller.getSpielerIch().getKarten().remove(event.karte);
		spielDaten.endpunkt.sende(new KartenTausch(event.karte));
		return KarteTauschenBekommen.class;
	}

	Class<? extends Zustand> aufgegeben() {
		controller.zeigeFehlermeldung("Bitte tausche zuerst die Karte mit deinem Partner bevor du aufgibst!");
		return this.getClass();
	}

	public void exit() {
		controller.kartenAuswahl(false);
	}
}
