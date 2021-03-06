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


package ch.bodesuri.applikation.client.zugautomat.pd;

import ch.bodesuri.applikation.client.pd.Brett;

/**
 * Decorator für die Klasse pd.zugsystem.Weg. Kümmert sicht um das Handling
 * der Weg-Markierung.
 */
public class Weg {
	private ch.bodesuri.pd.zugsystem.Weg aktuellerWeg;
	private Brett brett;

    public Weg(Brett brett) {
    	this.brett = brett;
    }

    public void setAktuellerWeg(ch.bodesuri.pd.zugsystem.Weg neuerWeg) {
    	if (aktuellerWeg != null)
    		unmarkiere(aktuellerWeg);

    	this.aktuellerWeg = neuerWeg;

    	if (aktuellerWeg != null)
    		markiere(aktuellerWeg);
    }

	private void markiere(ch.bodesuri.pd.zugsystem.Weg aktuellerWeg) {
		for (ch.bodesuri.pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWegLange(aktuellerWeg.getWegLaenge());
		}
    }

	private void unmarkiere(ch.bodesuri.pd.zugsystem.Weg aktuellerWeg) {
		for (ch.bodesuri.pd.spiel.brett.Feld f : aktuellerWeg) {
			brett.getFeld(f).setWegLange(0);
		}
    }
}
