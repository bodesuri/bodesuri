package applikation.client.zugautomat.zustaende;

import pd.regelsystem.RegelVerstoss;
import pd.zugsystem.Bewegung;
import applikation.client.controller.Controller;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.GezogenEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.pd.ZugEingabe;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand wenn der Spieler eine Bewegung machen muss. Wenn ein
 * {@link FeldGewaehltEvent} eintrifft wird der gesamte Zug validiert und
 * versendet. Der Automat wird dann beendet.
 */
public class ZielWaehlen extends ClientZugZustand {
	public ZielWaehlen(Controller controller) {
		this.controller = controller;
	}

	public void onEntry() {
		spielDaten.spiel.setHinweis("Wähle das Zielfeld.");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		if (spielDaten.start == event.feld) {
			event.feld.setAusgewaehlt(false);
			return StartWaehlen.class;
		} else {
			spielDaten.ziel = event.feld;

			/* Kann in eine seperate Methode oder in die Klasse SpielDaten verschoben werden */
			Bewegung bewegung = new Bewegung(spielDaten.start.getFeld(), spielDaten.ziel.getFeld());
			ZugEingabe zugEingabe = new ZugEingabe(spielDaten.spielerIch,
			                                       spielDaten.karte, bewegung);
			try {
				zugEingabe.validiere();
			} catch (RegelVerstoss e) {
				controller.zeigeFehlermeldung("Ungültiger Zug: " + e.getMessage());
				return this.getClass();
			}

			spielDaten.eventQueueBodesuriClient.enqueue(new GezogenEvent(zugEingabe));

			spielDaten.start.setAusgewaehlt(false);
			spielDaten.spielerIch.getKarten().setAktiv(false);

			return EndZustand.class;
		}
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		spielDaten.start.setAusgewaehlt(false);
		return StartWaehlen.class;
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.karte = event.karte;
		spielDaten.start.setAusgewaehlt(false);
		return StartWaehlen.class;
	}
}
