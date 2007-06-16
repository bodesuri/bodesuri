package applikation.client.zustaende;

import applikation.client.pd.ZugEingabe;
import applikation.client.zugautomat.ZugAutomat;
import applikation.events.FeldAbgewaehltEvent;
import applikation.events.FeldGewaehltEvent;
import applikation.events.HoverEndeEvent;
import applikation.events.HoverStartEvent;
import applikation.events.KarteGewaehltEvent;
import applikation.nachrichten.Aufgabe;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.Zustand;

/**
 * Zustand in welchem der Spieler dran kommt. Erstellt einen {@link ZugAutomat}
 * der sich um das Erfassen und Validieren eines Zuges kümmert. Der Automat
 * sendet eine {@link ZugEingabe} wenn er fertig ist. Diese wird versandt und
 * der Automat geht nach {@link NichtAmZug} über.
 */
public class AmZug extends ClientZustand {
	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		spielDaten.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		spielDaten.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		event.feld.setHover(true);
		return this.getClass();
    }

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		event.feld.setHover(false);
		return this.getClass();
    }


	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		spielDaten.zugAutomat.step(event);
		return this.getClass();
	}

	Class<? extends Zustand> gezogen(ZugEingabe zugEingabe) {
		spielDaten.spielerIch.getKarten().remove(zugEingabe.getKarte());
		spielDaten.endpunkt.sende(new ZugInformation(zugEingabe.getZugEingabe()));
		return NichtAmZug.class;
	}

	Class<? extends Zustand> aufgegeben() {
		if (spielDaten.spielerIch.kannZiehen()) {
			controller.zeigeFehlermeldung("Es kann noch nicht aufgegeben werden, " +
                               "da es noch möglich ist zu ziehen.");
			return this.getClass();
		} else {
			spielDaten.endpunkt.sende(new Aufgabe());
			spielDaten.spielerIch.getKarten().setAktiv(false); /* TODO: evtl. ins onexit? (-reto) */
			spielDaten.spielerIch.getKarten().clear();
			return NichtAmZug.class;
		}
	}
}
