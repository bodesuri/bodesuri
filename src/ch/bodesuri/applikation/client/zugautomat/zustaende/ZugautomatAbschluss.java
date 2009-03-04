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


package ch.bodesuri.applikation.client.zugautomat.zustaende;

import ch.bodesuri.applikation.client.pd.SteuerungsZustand;
import ch.bodesuri.dienste.automat.zustaende.PassiverZustand;
import ch.bodesuri.dienste.automat.zustaende.Zustand;

/**
 * Räumt den Zugautomaten auf. Alle Bewegungen werden zurückgesetzt, die
 * Kartenauswahl deaktiviert, die SteuerungsButtons ausgeblendet und in
 * {@link ZugautomatEndZustand} übergegangen.
 */
public class ZugautomatAbschluss extends ClientZugZustand implements
        PassiverZustand {

	public Class<? extends Zustand> handle() {
		bewegungenZuruecksetzen();
		spielDaten.spiel.spielerIch.getKarten().setAktiv(false);
		spielDaten.spiel.setSteuerungsZustand(SteuerungsZustand.NICHTS);

		return ZugautomatEndZustand.class;
	}
}
