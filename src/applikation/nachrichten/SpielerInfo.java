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


package applikation.nachrichten;

import java.io.Serializable;

/**
 * Ein Spieler wie er über die Leitung geht. Aus diesem SpielerInfo werden auf
 * den Clients anhand des Spielernamens die Spieler-Objekte erstellt.
 */
public class SpielerInfo implements Serializable {
	public final String spielername;

	public SpielerInfo(String spielername) {
		this.spielername = spielername;
	}
}
