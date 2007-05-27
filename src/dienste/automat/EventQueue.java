package dienste.automat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Die EventQueue ermöglicht die Zwischenspeicherung von zu berabeitenden
 * Events. Die Queue ist Thread-Safe und arbeitet nach dem FIFO prinzip.
 * 
 */
public class EventQueue implements EventQuelle {
	private BlockingQueue<Event> queue;

	/**
	 * Erstellt eine neue EventQueue
	 */
	public EventQueue() {
		queue = new LinkedBlockingQueue<Event>();
	}

	/**
	 * Enqueued einen neuen Event. Falls die Queue voll ist blockiert der Aufruf
	 * bis der Event hinzugefügt werden kann
	 * 
	 * @param event
	 *            Event der hinzugefügt werden soll
	 */
	public void enqueue(Event event) {
		try {
			queue.put(event);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Entfernt den ältesten Event aus der EventQueue. Falls die Queue leer ist
	 * blockiert der Aufruf bis ein Event eintrifft.
	 * 
	 * @return älteste anstehende Event
	 */
	public Event dequeue() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dienste.automat.EventQuelle#getEvent()
	 */
	public Event getEvent() {
		return dequeue();
	}
}
