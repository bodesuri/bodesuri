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

import ch.bodesuri.applikation.client.controller.Controller;
import ch.bodesuri.applikation.client.events.FeldAbgewaehltEvent;
import ch.bodesuri.applikation.client.events.FeldGewaehltEvent;
import ch.bodesuri.applikation.client.events.HoverEndeEvent;
import ch.bodesuri.applikation.client.events.HoverStartEvent;
import ch.bodesuri.applikation.client.events.KarteGewaehltEvent;
import ch.bodesuri.applikation.client.pd.Feld;
import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.pd.regelsystem.Regel;
import ch.bodesuri.pd.zugsystem.Bewegung;
import ch.bodesuri.pd.zugsystem.Weg;

/**
 * Zustand wenn der Spieler das Zielfeld wählen muss.
 * <ul>
 * <li>Trifft ein {@link FeldGewaehltEvent} ein, wird das Feld überprüft.
 * <ul>
 * <li>Macht die Wahl Sinn, wird nach {@link ZugValidieren} gewechselt.</li>
 * <li>Wurde das Startfeld ausgewählt, wird das Startfeld wieder deaktiviert und
 * nach {@link StartWaehlen} gewechselt.</li>
 * </ul>
 * </li>
 * <li>Trifft ein {@link KarteGewaehltEvent} ein (der Spieler hat eine andere
 * Karte ausgewählt) wird nach {@link KarteWaehlen} gewechselt.</li>
 * <li>Trifft ein {@link FeldAbgewaehltEvent} ein, wird das Brett
 * zurückgesetzt. Wir bleiben aber in diesem Zustand.</li>
 * <li>{@link HoverStartEvent} und {@link HoverEndeEvent} aktiveren bzw.
 * deaktivieren das Hervorheben des Weges</li>
 * </ul>
 */
public class ZielWaehlen extends ClientZugZustand {
	public ZielWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void onEntry() {
		spielDaten.spiel.hinweis.neuerHinweis("Wähle das Zielfeld.", true, spielDaten.spiel.spielerIch.getFarbe());
	}

	public void onExit() {
		spielDaten.weg.setAktuellerWeg(null);
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		Feld feld = event.feld;

		if (spielDaten.getStart() == feld) {
			/* Dasselbe Feld nochmals angeklickt */
			spielDaten.setStart(null);

			return StartWaehlen.class;
		} else if /* Prüfen ob der Spieler eine andere Figur aus seinem eigenen Lager angelickt hat */
				(feld.istLager() && /* ist ein lager */
				 feld.getFigur() != null  && /* hat figur drauf */
				 feld.getFigur().istVon(spielDaten.spiel.spielerIch.getSpieler())) { /* gehört mir */

			spielDaten.setStart(feld);

			return this.getClass();
		} else {
			spielDaten.setZiel(feld);
			return ZugValidieren.class;
		}
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		bewegungenZuruecksetzen();
		return StartWaehlen.class;
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.spiel.queue.enqueue(event);
		return KarteWaehlen.class;
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		/* Hover anschalten */
		event.feld.setHover(false);

		Regel regel = spielDaten.konkreteKarte.getRegel();
		if (regel != null && regel.arbeitetMitWeg()) {
			/* Weg markieren */
			Bewegung bewegung = new Bewegung(spielDaten.getStart().getFeld(),
			                                 event.feld.getFeld());

			Weg weg = bewegung.getWeg();

			if (weg != null) {
				spielDaten.weg.setAktuellerWeg(weg);
			}
		}

		return this.getClass();
	}

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		/* Hover abschalten*/
		event.feld.setHover(false);

		/* Weg nicht mehr markieren */
		return this.getClass();
	}
}
