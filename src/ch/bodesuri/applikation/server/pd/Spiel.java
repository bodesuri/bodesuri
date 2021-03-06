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


package ch.bodesuri.applikation.server.pd;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Vector;

import ch.bodesuri.applikation.nachrichten.SpielInfo;
import ch.bodesuri.applikation.nachrichten.SpielerInfo;
import ch.bodesuri.dienste.ServerStatus;
import ch.bodesuri.dienste.eventqueue.EventQueue;
import ch.bodesuri.dienste.netzwerk.EndPunktInterface;
import ch.bodesuri.dienste.netzwerk.Nachricht;
import ch.bodesuri.dienste.netzwerk.server.Server;
import ch.bodesuri.dienste.serialisierung.SerialisierungsKontext;
import ch.bodesuri.pd.ProblemDomain;
import ch.bodesuri.pd.serialisierung.CodiererThreads;
import ch.bodesuri.pd.spiel.spieler.Partnerschaft;


/**
 * Repräsentiert eine Gruppe von Spielern, die in einem Spiel auf dem Server
 * mitspielen.
 */
public class Spiel implements SerialisierungsKontext {
	private IdentityHashMap<EndPunktInterface, Spieler> endpunktZuSpieler;
	private Vector<Spieler> spielers = new Vector<Spieler>();
	private Vector<Partnerschaft> partnerschaften;
	private int anzahlSpieler;
	private ProblemDomain problemDomain;
	private ch.bodesuri.pd.spiel.Spiel spiel;

	public Server server;
	public EventQueue queue;
	public Runde runde;
	public ServerStatus status;

	/* (non-Javadoc)
	 * @see dienste.serialisierung.SerialisierungsKontext#registriere(java.lang.Thread)
	 */
	public void registriere(Thread thread) {
		CodiererThreads.registriere(thread, problemDomain.getCodierer());
	}

	/**
	 * Erstellt eine neue Spielerschaft.
	 *
	 * @param anzSpieler
	 *            Anzahl der Spieler
	 */
	public Spiel(int anzSpieler) {
		this.problemDomain = new ProblemDomain();
		this.spiel = problemDomain.getSpiel();
		this.anzahlSpieler = anzSpieler;
		this.spielers = new Vector<Spieler>();
		this.partnerschaften = new Vector<Partnerschaft>();
		this.endpunktZuSpieler = new IdentityHashMap<EndPunktInterface, Spieler>();
	}

	/**
	 * Bildet die Partnerschaften, die im Spiel benötigt werden. Die Zuordnung
	 * der Spieler zu den Partnerschaften erfolgt nach einem vorgegebenen
	 * Schema. Das Bilden der Partnerschaften funktioniert für 1, 2 oder 4
	 * Spieler.
	 */
	public void partnerschaftenBilden() {
		if (anzahlSpieler != spielers.size())
			throw new RuntimeException("partnerschaftBilden zu früh aufgerufen");

		if (anzahlSpieler == 1) {
			/* Entwickler Betrieb */
			Spieler einzelgaenger = spielers.get(0);

			einzelgaenger.setPartner(einzelgaenger);
			partnerschaften.add( new Partnerschaft(einzelgaenger.spieler, einzelgaenger.spieler) );
		} else if (anzahlSpieler == 2) {
			spielers.get(0).setPartner(spielers.get(1));
			spielers.get(1).setPartner(spielers.get(0));

			partnerschaften.add( new Partnerschaft(spielers.get(0).spieler, spielers.get(1).spieler) );
		} else if (anzahlSpieler == 4) {
			/* Normaler Betrieb */
			spielers.get(0).setPartner(spielers.get(2));
			spielers.get(2).setPartner(spielers.get(0));
			partnerschaften.add( new Partnerschaft(spielers.get(0).spieler, spielers.get(2).spieler) );

			spielers.get(1).setPartner(spielers.get(3));
			spielers.get(3).setPartner(spielers.get(1));
			partnerschaften.add( new Partnerschaft(spielers.get(1).spieler, spielers.get(3).spieler) );
		} else {
			throw new RuntimeException("Hups, so viele sieler sind nicht unterstüzt");
		}
	}

	/**
	 * Sendet die übergebene Nachricht an alle Spieler
	 *
	 * @param nachricht
	 *            zu versendende Nachricht
	 */
	public void broadcast(Nachricht nachricht) {
		for (Spieler spieler : spielers) {
			spieler.sende(nachricht);
		}
	}

	/**
	 * Anzahl der Spieler
	 *
	 * @return Anzahl der Spieler
	 */
	public int getAnzahlSpieler() {
		return spielers.size();
	}

	/**
	 * @return true falls Spiel komplett
	 */
	public boolean isKomplett() {
		return getAnzahlSpieler() == anzahlSpieler;
	}

	/**
	 * @return Neu zu spielende Runde
	 */
	public Runde starteRunde() {
		if (runde == null) {
			runde = new Runde(0, spielers, problemDomain.getKartenGeber());
		} else {
			runde = new Runde(runde.nummer + 1, spielers, problemDomain.getKartenGeber());
		}

		return runde;
	}

	/**
	 * Anti-Cheat-Funktion die sicherstellt, dass der Endpunkt auch immer der
	 * aktuelle Spieler der Runde ist.
	 *
	 * @param endpunkt
	 */
	public void sicherStellenIstAktuellerSpieler(EndPunktInterface endpunkt) {
		Spieler spieler = getSpieler(endpunkt);

		if (spieler == null) {
			throw new RuntimeException("Unbekannter Spieler, kann Spieler nicht anhand des Endpunktes "
			                                   + endpunkt + " auflösen");
		}

		if (runde.getAktuellerSpieler() != spieler) {
			throw new RuntimeException("Beschiss von " + endpunkt + " an "
			                     + runde.getAktuellerSpieler());
		}
	}

	/**
	 * Liefert den Spieler zu einem gegebenen Endpunkt.
	 *
	 * @param endpunkt Der Endpunkt des gesuchten Spielers
	 * @return Der Spieler oder null falls dieser nicht gefunden wurde
	 */
	public Spieler getSpieler(EndPunktInterface endpunkt) {
		Spieler spieler = endpunktZuSpieler.get(endpunkt);

		return spieler;
	}

	/**
	 * @return Liste aller Partnerschaften
	 */
	public List<Partnerschaft> getPartnerschaften() {
		return partnerschaften;
	}

	public SpielInfo getSpielInfo() {
		Vector<SpielerInfo> spielers = new Vector<SpielerInfo>();

		for (ch.bodesuri.pd.spiel.spieler.Spieler spieler: this.spiel.getSpieler()) {
			SpielerInfo si = new SpielerInfo(spieler.getName());
			spielers.add(si);
		}

		return new SpielInfo(spielers);
	}


	/*
	 * Decorator für pd.Spiel
	 */


	/**
	 * Erstellt einen neuen Spieler und registriert diesen beim Spiel
	 *
	 * @param spielerName Name des Spielers
	 * @param endpunkt Endpunkt des Spielers
	 * @return Spieler Objekt
	 */
	public Spieler fuegeHinzu(String spielerName, EndPunktInterface endpunkt) {
		/* PD Spieler erstellen */
		ch.bodesuri.pd.spiel.spieler.Spieler pdSpieler = spiel.fuegeHinzu(spielerName);

		/* Mini PD Spieler erstellen */
		Spieler spieler = new Spieler(endpunkt, pdSpieler);
		endpunktZuSpieler.put(spieler.getEndPunkt(), spieler);
		spielers.add(spieler);

		return spieler;
	}

	/**
	 * @param absender Zu entfernender Spieler
	 * @return true falls es einen solchen Spieler hatte
	 */
	public boolean entferne(EndPunktInterface absender) {
		Spieler spieler = endpunktZuSpieler.remove(absender);
		spiel.entferne(spieler.spieler);
		return spielers.remove(spieler);
    }

	/**
	 * Zeigt an, ob das Spiel fertig ist oder nicht. Dies wird anhand der
	 * Partnerschaften ermittelt (sobald eine Partnerschaft fertig ist, ist auch
	 * das Spiel fertig).
	 *
	 * @return Ob Spiel fertig ist oder nicht
	 */
	public boolean istFertig() {
		return getGewinner() != null;
    }

	/**
	 * Gibt die siegreiche Partnerschaft zurück.
	 *
	 * @return Siegreiche Partnerschaft. Falls noch nicht bekannt wird null
	 *         zurückgegeben.
	 */
	public Partnerschaft getGewinner() {
		for (Partnerschaft p : partnerschaften) {
			if (p.istFertig()) {
				return p;
			}
		}

	    return null;
    }

	/**
	 * Prüft ob es bereits einen Spieler mit dem übergebenen Namen im Spiel hat.
	 *
	 * @param spielerName
	 * @return true solle es bereis einen solchen Spieler haben
	 */
	public boolean hatBereitsSpieler(String spielerName) {
		for (Spieler s : spielers) {
			if (s.getName().equals(spielerName)) {
				return true;
			}
		}
		return false;
	}
}