package applikation.client.zustaende;

import pd.spieler.Spieler;
import applikation.client.controller.Controller;
import applikation.geteiltes.SpielerInfo;
import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.SpielStartNachricht;
import dienste.automat.zustaende.Zustand;
import dienste.netzwerk.EndPunktInterface;

/**
 * Zustand wennn der Spieler in der Lobby ist. Wenn eine
 * {@link SpielStartNachricht} eintrifft wird der Zustand {@link SpielStart}
 * aufgerufen.
 */
public class Lobby extends ClientZustand {
	public Lobby(Controller controller) {
		this.controller = controller;
	}

	Class<? extends Zustand> chatNachricht(EndPunktInterface absender, String nachricht) {
		System.out.println("Nachricht von " + absender + ": " + nachricht);
		return this.getClass();
	}

    Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsInformation bestaetitigung) {
    	//TODO: Controller(Lobby) über neuen Spieler benachrichtigen.
    	// Soll die SpielerListe auch Observerable sein?
	    return this.getClass();
    }

	Class<? extends Zustand> spielStarten(SpielStartNachricht startNachricht) {
		for (SpielerInfo si : startNachricht.spielInfo.spielers) {
			String name = si.name;
			Spieler neuerSpieler = spielDaten.spiel.fuegeHinzu(name);
            if (name.equals(spielDaten.spielerName)) {
            	spielDaten.spielerIch = neuerSpieler;
            }
            
            spielDaten.spielers.put(neuerSpieler, new applikation.client.pd.Spieler(neuerSpieler));
		}

		return SpielStart.class;
	}
}
