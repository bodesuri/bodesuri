package applikation.server.zustaende;

import pd.spiel.spieler.Partnerschaft;
import applikation.nachrichten.SpielFertigNachricht;
import dienste.automat.zustaende.PassiverZustand;
import dienste.automat.zustaende.Zustand;

public class ZugAbschluss extends ServerZustand implements PassiverZustand {
	public Class<? extends Zustand> handle() {
		if (spiel.istFertig()) {
			Partnerschaft gewinner = spiel.getGewinner();
			spiel.broadcast(new SpielFertigNachricht(gewinner));

			return ServerStoppen.class;
		} else if (spiel.runde.isFertig()) {
			return StartRunde.class;
		} else { /* ganz noraml weiter zum nächsten zug */
			return StarteZug.class;
		}
	}
}
