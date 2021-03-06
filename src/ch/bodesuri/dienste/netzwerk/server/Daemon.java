/*
 * Copyright (C) 2007  Danilo Couto, Philippe Eberli,
 *                     Pascal Hobus, Reto Schüttel, Robin Stocker
 *
 * This file is part of Bodesuri.
 *
 * Bodesuri is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * Bodesuri is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Bodesuri; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


package ch.bodesuri.dienste.netzwerk.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import ch.bodesuri.dienste.eventqueue.EventQueue;
import ch.bodesuri.dienste.netzwerk.BriefKastenInterface;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.dienste.serialisierung.SerialisierungsKontext;
import ch.bodesuri.dienste.threads.BodesuriThread;


/**
 * Netzwerkdämon der neue Netzwerk-Verbindungen akzeptiert und diese den
 * dazugehörigen Server meldet.
 *
 */
public class Daemon extends BodesuriThread {
	private BriefKastenInterface briefkasten;
	private ServerSocket serverSock;
	private EventQueue queue;
	private SerialisierungsKontext serialisierungsKontext;
	private boolean istBeendet;

	/**
	 * Erstellt einen neuen Dämon.
	 *
	 * @param port
	 *            TCP-Port auf welchem gelauscht werden soll
	 * @param briefkasten
	 *            Briefkasten in welchen Nachrichten der Verbindungen abgelegt
	 *            werden soll
	 * @param queue
	 *            Queue über welche die neue Verbindung angekündet wird
	 * @param sk
	 *            Serialisierungskontext
	 */
	public Daemon(int port, BriefKastenInterface briefkasten, EventQueue queue,
	        SerialisierungsKontext sk) {
		super("Server-Daemon");

		try {
	        this.serverSock = new ServerSocket(port);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
		this.briefkasten = briefkasten;
		this.queue = queue;
		this.serialisierungsKontext = sk;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			while (true) {
				Socket clientSocket = serverSock.accept();
				EndPunktInterface client = new ServerEndPunkt(clientSocket, briefkasten,
				                               serialisierungsKontext);

				queue.enqueue(new NeueVerbindung(client));
			}
		} catch (Exception e) {
			/* Prüfen ob der Socket ggf. absichtlcih beendet wurde*/
			if (e instanceof SocketException && istBeendet == true) {
				return;
			}

			try {
				/* Fehler dem HauptThread melden und abbrechen */
				queue.enqueue(new SchwererDaemonFehler(e));
			} catch (Exception e_nested) {
				/* sogar das Fehler-Melden geht nicht mehr */
				e_nested.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Deaktivert den Sever.
	 */
	public void auschalten() {
		try {
			istBeendet = true;
	        serverSock.close();
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
	}
}
