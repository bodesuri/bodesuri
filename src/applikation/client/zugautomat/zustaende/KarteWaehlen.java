package applikation.client.zugautomat.zustaende;

import applikation.client.controller.Controller;
import applikation.events.FeldGewaehltEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.ChatNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunkt;

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
		// TODO: Wird erst ausgeführt wenn wir das das nächste Ereignis erhalten
		// und den Automaten mit Step brauchen. -> Bis auf weiteres verschoben nach NichtAmZug
		// controller.kartenAuswahl(true);
	}

	Class<? extends Zustand> chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
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
