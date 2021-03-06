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


package ch.bodesuri.pd.regelsystem;

import ch.bodesuri.pd.regelsystem.verstoesse.RegelVerstoss;
import ch.bodesuri.pd.regelsystem.verstoesse.Verstoesse;
import ch.bodesuri.pd.spiel.brett.BankFeld;
import ch.bodesuri.pd.spiel.brett.Brett;
import ch.bodesuri.pd.spiel.brett.Feld;
import ch.bodesuri.pd.spiel.brett.LagerFeld;
import ch.bodesuri.pd.spiel.spieler.Figur;
import ch.bodesuri.pd.spiel.spieler.Spieler;
import ch.bodesuri.pd.zugsystem.Aktion;
import ch.bodesuri.pd.zugsystem.Bewegung;
import ch.bodesuri.pd.zugsystem.HeimschickAktion;
import ch.bodesuri.pd.zugsystem.Zug;

/**
 * Regel für das Starten mit einer Figur vom {@link LagerFeld} auf das
 * {@link BankFeld}.
 */
public class StartRegel extends Regel {
	public StartRegel() {
		super();
		setBeschreibung("Starten");
	}

	/**
	 * Validiere ZugEingabe. Der Zug muss von einem Lagerfeld des Spielers auf
	 * sein Bankfeld gehen. Geht nicht, wenn das Bankfeld geschützt ist.
	 */
	public Zug validiere(ZugEingabe zugEingabe) throws RegelVerstoss {
		if (zugEingabe.getAnzahlBewegungen() != 1) {
			throw new Verstoesse.AnzahlBewegungen();
		}

		Spieler spieler = zugEingabe.getBetroffenerSpieler();
		Feld start = zugEingabe.getBewegung().start;
		Feld ziel  = zugEingabe.getBewegung().ziel;

		if (!start.istLager()) {
			throw new Verstoesse.NurMitFigurVomLagerStarten();
		}
		LagerFeld lager = (LagerFeld)start;

		if (!lager.istBesetztVon(spieler)) {
			throw new Verstoesse.NurMitFigurVomLagerStarten();
		}

		if (!ziel.istBank()) {
			throw new Verstoesse.NurAufEigeneBankStarten();
		}
		BankFeld bank = (BankFeld)ziel;

		if (!bank.istVon(spieler)) {
			throw new Verstoesse.NurAufEigeneBankStarten();
		}

		Zug zug = new Zug();

		if (ziel.istGeschuetzt()) {
			throw new Verstoesse.AufGeschuetzteFigurStarten();
		} else if (ziel.istBesetzt()) {
			zug.fuegeHinzu(new HeimschickAktion(ziel));
		}

		zug.fuegeHinzu(new Aktion(start, ziel));

		return zug;
	}

	protected void liefereZugEingaben(Spieler spieler, Karte karte,
	                               ZugEingabeAbnehmer abnehmer) {
		Brett brett = spieler.getSpiel().getBrett();
		for (Figur figur : spieler.getZiehbareFiguren()) {
			if (figur.getFeld().istLager()) {
				Feld ziel = brett.getBankFeldVon(figur.getSpieler());
				if (!ziel.istGeschuetzt()) {
					Feld start = figur.getFeld();
					Bewegung bewegung = new Bewegung(start, ziel);
					ZugEingabe ze = new ZugEingabe(spieler, karte, bewegung);
					boolean abbrechen = abnehmer.nehmeEntgegen(ze);
					if (abbrechen) {
						return;
					}
				}
			}
		}
	}
}
