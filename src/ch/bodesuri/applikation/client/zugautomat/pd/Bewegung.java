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

import ch.bodesuri.applikation.client.pd.Feld;

public class Bewegung {
	private Feld start;
	private Feld ziel;
	
	public ch.bodesuri.pd.zugsystem.Bewegung toBewegung() {
		return new ch.bodesuri.pd.zugsystem.Bewegung(start.getFeld(), ziel.getFeld());
	}

	public Feld getStart() {
    	return start;
    }

	public Feld getZiel() {
    	return ziel;
    }

	public void setStart(Feld start) {
    	this.start = start;
    }

	public void setZiel(Feld ziel) {
    	this.ziel = ziel;
    }
}
