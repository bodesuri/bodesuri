package dienste.netzwerk;

import dienste.eventqueue.Event;

public class SchwererDaemonFehler extends Event {
	public final Exception exception;

	public SchwererDaemonFehler(Exception e) {
		this.exception = e;
	}
}
