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

import ch.bodesuri.pd.regelsystem.StartRegel;
import ch.bodesuri.pd.regelsystem.VorwaertsRegel;
import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;

/**
 * Testet die Funktionalität der Startregel.
 */
public class StartRegelTest extends RegelTestCase {
	protected void setUp() {
		super.setUp();
		regel = new StartRegel();
	}

	public void testArbeitetMitWeg() {
		assertFalse(regel.arbeitetMitWeg());
	}

	/**
	 * Prüft, ob die Startregel korrekt angewendet wird, und ob
	 * danach normal mit der Figur gefahren werden kann.
	 *
	 * @throws RegelVerstoss
	 */
	public void testStart() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren();

		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());

		start = bank(0);
		ziel  = start.getNtesFeld(5);
		sollteValidieren(new VorwaertsRegel(5));

		assertFalse(start.istGeschuetzt());
		assertFalse(ziel.istGeschuetzt());
	}

	/**
	 * Prüft, ob ein Start von einem Nicht-Lagerfeld unmöglich ist.
	 */
	public void testStartNichtVonLagerFeldAus() {
		start = bank(0);
		ziel  = bank(0).getNaechstes();
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob die StartRegel einen Verstoss gibt, wenn keine Figur
	 * auf dem Lagerfeld ist.
	 */
	public void testStartKeineFigurAufLagerFeld() {
		start = lager(0);
		ziel  = bank(0);
		start.versetzeFigurAuf(ziel);
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob die StartRegel einen Verstoss gibt, wenn keine Figur
	 * auf dem Bankfeld ist.
	 */
	public void testStartNichtAufBankFeld() {
		start = lager(0);
		ziel  = bank(0).getNaechstes();
		sollteVerstossGeben();

		ziel  = bank(1);
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob andere Spieler auch starten können.
	 */
	public void testStartAndererSpieler() {
		start = lager(1);
		ziel  = bank(1);
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob nach erfolgtem Start die Figur auch wirklich verschoben
	 * wurde und ob das Feld geschützt ist. Ein erneuter Start ist dann
	 * auf besetztes Feld nicht möglich.
	 *
	 * @throws RegelVerstoss
	 */
	public void testStartGeschuetzt() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		sollteValidieren();

		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());

		start = lager(0, 1);
		ziel  = bank(0);
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob nicht auf fremde Bankfelder gestartet werden kann.
	 *
	 */
	public void testStartAufFremdeBank() {
		start = lager(0);
		ziel  = bank(1);
		sollteVerstossGeben();
	}

	/**
	 * Prüft, ob Figuren heimgeschickt werden, die auf der Bank stehen.
	 *
	 * @throws RegelVerstoss
	 */
	public void testStartHeimSchicken() throws RegelVerstoss {
		start = lager(0);
		ziel  = bank(0);
		lager(1).versetzeFigurAuf(ziel);
		sollteValidieren();

		assertTrue(start.istFrei());
		assertTrue(ziel.istBesetztVon(spieler(0)));
		assertTrue(ziel.istGeschuetzt());
		assertTrue(lager(1).istBesetztVon(spieler(1)));
	}
}
