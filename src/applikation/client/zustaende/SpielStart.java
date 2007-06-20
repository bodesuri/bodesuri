package applikation.client.zustaende;

import ui.spiel.SpielView;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem das Spiel ({@link SpielView}) gestartet wird. Geht
 * direkt in {@link NichtAmZug} über.
 */
public class SpielStart extends ClientZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		controller.zeigeSpiel(spiel);
		return NichtAmZug.class;
	}
}
