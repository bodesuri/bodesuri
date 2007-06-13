package applikation.nachrichten;

import applikation.geteiltes.SpielInfo;
import dienste.netzwerk.Nachricht;

/**
 * Nachricht die dem Spieler den Beitritt zum Spiel bestätigt.
 */
public class BeitrittsBestaetigung extends Nachricht {
	/**
	 * Name der bereits
	 */
	public final SpielInfo spielInfo;

	/**
	 * @param spielInfo
	 */
	public BeitrittsBestaetigung(SpielInfo spielInfo) {
		this.spielInfo = spielInfo;
	}
}
