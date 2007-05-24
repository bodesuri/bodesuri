/**
 * @(#) ZugEntgegennahme.java
 */

package applikation.zugentgegennahme;

import pd.brett.Feld;
import pd.zugsystem.Bewegung;
import applikation.client.events.ZugEingegebenEvent;
import dienste.automat.EventQueue;

public class ZugEntgegennahme {
	private ZugStatus zugStatus;
	private Feld start;
	private Feld ziel;
	
	private EventQueue queue;

	public ZugEntgegennahme(EventQueue queue) {
		this.queue = queue;
		zugStatus = new WartenAufStarteingabe();
	}

	/**
	 * Status-Handhabung für die Zugentgegennahme.
	 */
	private abstract class ZugStatus {
		abstract public void handle(Feld feld);
	}

	private class WartenAufStarteingabe extends ZugStatus {
		public void handle(Feld feld) {
			start = feld;
			zugStatus = new WartenAufZieleingabe();
		}
	}

	private class WartenAufZieleingabe extends ZugStatus {
		public void handle(Feld feld) {
			ziel = feld;
			zugStatus = new ZugErfasst();
			zugBestaetigen(start, ziel);
		}
	}
	
	private class ZugErfasst extends ZugStatus {
		public void handle(Feld feld) {}
	}

	/**
	 * Setzt den neuen Status der ZugEntgegennahme und entscheidet, ob das Feld
	 * das Start- oder das Zielfeld ist.
	 * 
	 * @param feld
	 */
	public void ziehen(Feld feld) {
		zugStatus.handle(feld);
		System.out.println("Auf " + feld + "geklickt.");
	}

	/**
	 * Nachdem ein Zug erfolgreich erfasst wurde wird hier die resultierende
	 * Bewegung erstellt.
	 * 
	 * @param start
	 * @param ziel
	 * @return
	 */
	private void zugBestaetigen(Feld start, Feld ziel) {
		Bewegung bewegung = new Bewegung(start, ziel);
		queue.enqueue(new ZugEingegebenEvent(bewegung));
	}
}
