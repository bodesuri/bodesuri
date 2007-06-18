package applikation.client.zustaende;

import pd.karten.Karte;
import applikation.client.controller.Controller;
import applikation.client.events.AufgegebenEvent;
import applikation.client.events.ChatEingabeEvent;
import applikation.client.events.FeldAbgewaehltEvent;
import applikation.client.events.FeldGewaehltEvent;
import applikation.client.events.HoverEndeEvent;
import applikation.client.events.HoverStartEvent;
import applikation.client.events.KarteGewaehltEvent;
import applikation.client.events.VerbindeEvent;
import applikation.client.events.ZugErfasstEvent;
import applikation.client.pd.Spiel;
import applikation.nachrichten.AktuellerSpielerInformation;
import applikation.nachrichten.BeitrittsInformation;
import applikation.nachrichten.ChatNachricht;
import applikation.nachrichten.KartenTausch;
import applikation.nachrichten.RundenStart;
import applikation.nachrichten.SpielStartNachricht;
import applikation.nachrichten.SpielVollNachricht;
import applikation.nachrichten.ZugAufforderung;
import applikation.nachrichten.ZugInformation;
import dienste.automat.zustaende.EndZustand;
import dienste.automat.zustaende.Zustand;
import dienste.eventqueue.Event;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunktInterface;
import dienste.netzwerk.Nachricht;
import dienste.netzwerk.VerbindungGeschlossen;
import dienste.netzwerk.server.NetzwerkEvent;

/**
 * Spezifischer aktiver Client-Zustand
 */
public class ClientZustand extends Zustand {
	protected Controller controller;
	protected Spiel spiel;

	public Class<? extends Zustand> handle(Event event) {
		if (event instanceof NetzwerkEvent) {
			return handleNetzwerk(event);
		} else {
			return handleGUI(event);
		}
	}

	private Class<? extends Zustand> handleGUI(Event event) {
	    if (event instanceof VerbindeEvent)
	    	return verbinden((VerbindeEvent) event);
	    else if (event instanceof AufgegebenEvent)
	    	return aufgegeben();
	    else if (event instanceof ZugErfasstEvent)
	    	return gezogen((ZugErfasstEvent) event);
	    else if (event instanceof KarteGewaehltEvent)
	    	return karteGewaehlt((KarteGewaehltEvent) event);
	    else if (event instanceof FeldGewaehltEvent)
	    	return feldGewaehlt((FeldGewaehltEvent) event);
	    else if (event instanceof FeldAbgewaehltEvent)
	    	return feldAbgewaehlt((FeldAbgewaehltEvent) event);
	    else if (event instanceof HoverStartEvent)
	    	return hoverStart((HoverStartEvent) event);
	    else if (event instanceof HoverEndeEvent)
	    	return hoverEnde((HoverEndeEvent) event);
	    else if (event instanceof ChatEingabeEvent)
	    	return chatEingabe((ChatEingabeEvent) event);

	    return super.handle(event);
    }


	private Class<? extends Zustand> handleNetzwerk(Event event) {
	    NetzwerkEvent be = (NetzwerkEvent) event;

	    Brief brief = be.brief;
	    Nachricht nachricht = brief.nachricht;

	    if (nachricht instanceof SpielVollNachricht)
	    	return spielVoll(brief.absender, (SpielVollNachricht) nachricht);
	    else if (nachricht instanceof BeitrittsInformation)
	    	return beitrittsBestaetitigung((BeitrittsInformation) nachricht);
	    else if (nachricht instanceof SpielStartNachricht)
	    	return spielStarten((SpielStartNachricht) nachricht);
	    else if (nachricht instanceof ZugInformation)
	    	return zugWurdeGemacht(((ZugInformation) nachricht).zug);
	    else if (nachricht instanceof ZugAufforderung)
	    	return zugAufforderung((ZugAufforderung) nachricht);
	    else if (nachricht instanceof AktuellerSpielerInformation)
	    	return aktuellerSpielerInformation((AktuellerSpielerInformation) nachricht);
	    else if (nachricht instanceof KartenTausch)
	    	return kartenTausch(((KartenTausch) nachricht).karte);
	    else if (nachricht instanceof RundenStart)
	    	return rundenStart((RundenStart) nachricht);
	    else if (nachricht instanceof VerbindungGeschlossen)
	    	return verbindungGeschlossen(brief.absender);
	    else if (nachricht instanceof ChatNachricht)
	    	return chatNachricht(brief.absender, (ChatNachricht) nachricht);
	    else
	    	System.out.println("Nachricht " + nachricht.getClass()
	    	                   + " ist (noch) nicht implementiert!");

	    return super.handle(event);
    }

	/* GUI Handler - Global */

	Class<? extends Zustand> chatEingabe(ChatEingabeEvent eingabe) {
		ChatNachricht cn = new ChatNachricht(spiel.spielerIch.getName(), eingabe.text);
		spiel.endpunkt.sende(cn);

		return this.getClass();
    }

	/* GUI Handler - Verbinden */

	Class<? extends Zustand> verbinden(VerbindeEvent event) {
		return keinUebergang();
	}

	/* GUI Handler - Lobby */

	/* GUI Handler - Spiel */

	Class<? extends Zustand> gezogen(ZugErfasstEvent erfassterZug) {
		return ignoriereEvent("gezogen");
	}

	Class<? extends Zustand> aufgegeben() {
		return ignoriereEvent("aufgegeben");
	}

	Class<? extends Zustand> feldGewaehlt(FeldGewaehltEvent event) {
		return ignoriereEvent("feldG");
	}

	Class<? extends Zustand> feldAbgewaehlt(FeldAbgewaehltEvent event) {
		return ignoriereEvent("feldAbgewaehlt");
	}

	Class<? extends Zustand> karteGewaehlt(KarteGewaehltEvent event) {
		return ignoriereEvent("karteGewaehlt");
	}

	Class<? extends Zustand> hoverStart(HoverStartEvent event) {
		return ignoriereEvent();
    }

	Class<? extends Zustand> hoverEnde(HoverEndeEvent event) {
		return ignoriereEvent();
    }

	Class<? extends Zustand> verbindungGeschlossen(EndPunktInterface endpunkt) {
		controller.zeigeFehlermeldung("Verbindung zu Server " + endpunkt +
		                  " wurde unerwartet beendet. Client wird beendet.");
    	return EndZustand.class;
    }


	/* Netzwerk Handler */

	Class<? extends Zustand> spielVoll(EndPunktInterface absender, SpielVollNachricht nachricht) {
		return keinUebergang();
	}

	Class<? extends Zustand> chatNachricht(EndPunktInterface absender, ChatNachricht nachricht) {
		spiel.getChat().neueNachricht(nachricht.sender, nachricht.text);

		return this.getClass();
	}

	Class<? extends Zustand> beitrittsBestaetitigung(BeitrittsInformation bestaetitigung) {
		return keinUebergang();
	}

	Class<? extends Zustand> spielStarten(SpielStartNachricht nachricht) {
		return keinUebergang();
	}

	Class<? extends Zustand> zugWurdeGemacht(pd.zugsystem.ZugEingabe zug) {
		return keinUebergang();
	}

	Class<? extends Zustand> zugAufforderung(ZugAufforderung aufforderung) {
		return keinUebergang();
	}
	Class<? extends Zustand> kartenTausch(Karte karte) {
		return keinUebergang();
	}

	Class<? extends Zustand> rundenStart(RundenStart rundenstart) {
		return keinUebergang();
	}

	Class<? extends Zustand> aktuellerSpielerInformation(AktuellerSpielerInformation information) {
		return keinUebergang();
    }


	/* Sonstiges */
	public void setController(Controller controller) {
	    this.controller = controller;
    }

	public void setSpiel(Spiel spiel) {
		this.spiel = spiel;
    }
}
