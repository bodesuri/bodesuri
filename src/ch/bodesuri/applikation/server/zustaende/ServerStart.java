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


package ch.bodesuri.applikation.server.zustaende;

import ch.bodesuri.dienste.ServerStatus;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.netzwerk.server.Server;
import ch.bodesuri.dienste.serialisierung.SerialisierungsKontext;

/**
 * {@link Server} wird gestartet. Der TCP-Daemon wird initialisiert und es werden die
 * notwendigen Vorbereitungen getroffen um Spieler zu akzeptieren. Geht direkt in
 * den Zustand {@link EmpfangeSpieler} über.
 */
public class ServerStart extends ServerZustand implements PassiverZustand {
	private final int port;

	/**
	 * Den Server starten.
	 * 
	 * @param port TCP-Port auf welchem gelauscht werden soll
	 */
	public ServerStart(int port) {
		this.port = port;
	}

	public Class<? extends Zustand> handle() {
		SerialisierungsKontext kontext = spiel;

		spiel.server = new Server(spiel.queue, kontext, port);
		spiel.status = new ServerStatus(port);
		spiel.status.schreibeStatus("<p>Kein Spieler verbunden.</p>");

		return EmpfangeSpieler.class;
	}
}
