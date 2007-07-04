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


package dienste.netzwerk.server;

import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;

/**
 * Event der ausgelöst wird wenn eine Netzwerknachricht eintrifft.
 */
public class NetzwerkEvent extends Event {
	/**
	 * Im Event enthaltene Brief
	 */
	public final Brief brief;

	/**
	 * Ein Netzwerkevent ist eingetroffen
	 * @param brief Enthält die eingetroffene Nachricht.
	 */
	public NetzwerkEvent(Brief brief) {
		this.brief = brief;
	}

	/* (non-Javadoc)
	 * @see dienste.eventqueue.Event#toString()
	 */
	public String toString() {
		return super.toString() + " " + brief.nachricht;
	}
}
