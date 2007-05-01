/**
 * @(#) Koenig.java
 */

package pd.deck;

import pd.regelsystem.VorwaertsRegel;

public class Koenig extends Karte{
	Koenig(){
		/* TODO: ein Stein vom Startraum auf die Startposition */
		setRegel(new VorwaertsRegel(13));
	}
}
