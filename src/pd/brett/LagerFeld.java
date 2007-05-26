package pd.brett;

import pd.spieler.Spieler;

/**
 * Lagerfeld, auf dem die Figur des Spieler am Anfang steht.
 * 
 * Das nächste Feld ist das {@link BankFeld}.
 */
public class LagerFeld extends SpielerFeld {
	private static final long serialVersionUID = 1L;

	/**
	 * @see SpielerFeld#SpielerFeld(int, Spieler)
	 */
	public LagerFeld(int nummer, Spieler spieler) {
		super(nummer, spieler);
	}
}
