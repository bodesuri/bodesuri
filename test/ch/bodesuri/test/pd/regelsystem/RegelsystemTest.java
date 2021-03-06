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


package ch.bodesuri.test.pd.regelsystem;

import java.util.List;

import ch.bodesuri.pd.karten.Ass;
import ch.bodesuri.pd.karten.KartenFarbe;
import ch.bodesuri.pd.regelsystem.Karte;
import ch.bodesuri.pd.regelsystem.Regelsystem;
import ch.bodesuri.pd.regelsystem.ZugEingabe;
import ch.bodesuri.test.pd.ProblemDomainTestCase;


public class RegelsystemTest extends ProblemDomainTestCase {
	public void testKannZiehen() {
		Karte karte = new Ass(KartenFarbe.Herz);
		spieler(0).getKarten().add(karte);

		assertTrue(Regelsystem.kannZiehen(spieler(0)));
	}

	public void testGetMoeglicheZuege() {
		Karte karte = new Ass(KartenFarbe.Herz);
		spieler(0).getKarten().add(karte);

		List<ZugEingabe> zuege1 = Regelsystem.getMoeglicheZuege(spieler(0));
		List<ZugEingabe> zuege2 = karte.getRegel().getMoeglicheZuege(spieler(0), karte);
		assertEquals(zuege1, zuege2);
	}
}
