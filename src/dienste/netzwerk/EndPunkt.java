package dienste.netzwerk;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import dienste.serialisierung.SerialisierungsKontext;

/**
 * Dient dem Server und dem Client zur Kommunikation mit dem jewiligen
 * Kommunikationspartner.
 *
 */
public class EndPunkt {
	private Socket socket;
	private Thread empfaengerThread;
	private ObjectOutputStream outputStream;
	private Empfaenger empfaenger;
	private SerialisierungsKontext serialisierungsKontext;

	/**
	 * Startet die Kommunkation mit dem übergebenen Socket. Dieser Konstrutkur
	 * wird vom Server verwendet.
	 *
	 * TODO: prüfen ob man evtl. zwei Subklassen anstatt zwei Konstruktoren
	 * verwenden sollte
	 *
	 * @param socket
	 *            zu verwendendender Socket
	 * @param briefkasten
	 *            Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskonext
	 * @throws IOException
	 */
	public EndPunkt(Socket socket, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws IOException {
		this.socket = socket;
		this.serialisierungsKontext = sk;

		startVerhandlung(briefkasten);
	}

	/**
	 * Startet die Kommunikation mit dem übergebenen System (Hostname & Port).
	 * Dieser Konstrutkur wird vom Client verwendet.
	 *
	 * TODO: prüfen ob man evtl. zwei Subklassen anstatt zwei Konstruktoren
	 * verwenden sollte
	 *
	 * @param hostname
	 *            Hostname des zu verbindenen Systems
	 * @param port
	 *            Port des zu verbinden Systems
	 * @param briefkasten Briefkasten in welchem die Nachrichten abgelegt werden können
	 * @param sk Serialisierungskonext
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public EndPunkt(String hostname, int port, BriefKastenInterface briefkasten,
	                SerialisierungsKontext sk)
	        throws UnknownHostException, IOException {
		System.out.println("Verbinde zu " + hostname + ":" + port);

		serialisierungsKontext = sk;
		socket = new Socket(hostname, port);

		startVerhandlung(briefkasten);

		System.out.println("Verbindungsaufbau erfolgreich");
	}

	private void startVerhandlung(BriefKastenInterface briefkasten) throws IOException {
		outputStream = new ObjectOutputStream(socket.getOutputStream());

		/*
		 * Bis auf weiteres machen wir das Empfanger der Nachrichten mal in
		 * einem seperaetn Thread. Später kann man das evtl. mit Select etc.
		 * besser lösen
		 */
		empfaenger = new Empfaenger(this, socket, briefkasten);
		empfaengerThread = new Thread(empfaenger);
		serialisierungsKontext.registriere(empfaengerThread);
		empfaengerThread.start();
	}

	/**
	 * Sende die angegebene Nachricht an den Host auf der anderen Seite des
	 * Kommunikationkanals.
	 *
	 * @param nachricht
	 *            zu übertragende Nachricht
	 */
	public void sende(Nachricht nachricht) {
		try {
			outputStream.writeObject(nachricht);
		} catch (IOException e) {
			throw new VerbindungWegException();
		}
	}

	/**
	 * Beendet die Verbindung
	 *
	 * @throws IOException
	 */
	public void ausschalten() throws IOException {
		socket.shutdownInput();
		socket.shutdownOutput();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		InetAddress addr = socket.getInetAddress();
		return addr.getHostAddress() + ":" + socket.getPort();
	}
}
