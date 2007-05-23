package applikation.client.zustaende;

import applikation.client.BodesuriClient;
import applikation.client.events.NetzwerkEvent;
import applikation.client.events.VerbindenEvent;
import applikation.server.nachrichten.BeitrittsBestaetigung;
import applikation.server.nachrichten.ChatNachricht;
import applikation.server.nachrichten.SpielBeitreten;
import applikation.server.nachrichten.SpielStartNachricht;
import applikation.server.nachrichten.SpielVollNachricht;
import dienste.automat.AktiverZustand;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.Zustand;
import dienste.netzwerk.Brief;
import dienste.netzwerk.EndPunkt;
import dienste.netzwerk.Nachricht;

public class AktiverClientZustand extends AktiverZustand {
	protected BodesuriClient automat;

	public Zustand handle(Event event) {

		if (event instanceof NetzwerkEvent) {
			NetzwerkEvent be = (NetzwerkEvent) event;

			Brief brief = be.brief;
			Nachricht nachricht = brief.nachricht;

			if (nachricht instanceof SpielBeitreten)
				return spielBeitreten(brief.absender,
				                      (SpielBeitreten) nachricht);
			else if (nachricht instanceof ChatNachricht)
				return chatNachricht(brief.absender, (ChatNachricht) nachricht);
			else if (nachricht instanceof SpielVollNachricht)
				return spielVoll(brief.absender, (SpielVollNachricht) nachricht);
			else if (nachricht instanceof BeitrittsBestaetigung) 
				return beitrittsBestaetitigung((BeitrittsBestaetigung) nachricht);
			else if (nachricht instanceof SpielStartNachricht)
				//TODO SpielStarten wäre unten eigentlich besser. Aber was hier?
				return spielStarten((SpielStartNachricht) nachricht);

		} else {
			if (event instanceof VerbindenEvent)
				return verbinden((VerbindenEvent) event);
		}

		return super.handle(event);
	}

	Zustand verbinden(VerbindenEvent event) {
		return keinUebergang();
	}

	Zustand spielVoll(EndPunkt absender, SpielVollNachricht nachricht) {
		return keinUebergang();
	}

	Zustand chatNachricht(EndPunkt absender, ChatNachricht nachricht) {
		return keinUebergang();
	}

	Zustand neueVerbindeung(EndPunkt absender) {
		return keinUebergang();
	}

	Zustand spielBeitreten(EndPunkt absender, SpielBeitreten beitreten) {
		return keinUebergang();
	}
	
	Zustand beitrittsBestaetitigung(BeitrittsBestaetigung bestaetitigung) {
		return keinUebergang();
	}
	
	Zustand spielStarten(SpielStartNachricht nachricht) {
		return keinUebergang();
	}

	Zustand keinUebergang() {
		throw new KeinUebergangException("Kein Übergang definiert in Zustand "
		                                 + this);
	}

	@Override
    public void setAutomat(Automat automat) {
		this.automat = (BodesuriClient) automat;
    }
}