package applikation.client.zugAutomat.zustaende;

import applikation.client.zugAutomat.ZugAutomat;
import applikation.events.BewegungEingegebenEvent;
import applikation.events.KarteGewaehltEvent;
import dienste.automat.Automat;
import dienste.automat.Event;
import dienste.automat.KeinUebergangException;
import dienste.automat.zustaende.AktiverZustand;
import dienste.automat.zustaende.Zustand;

public class AktiverZugZustand extends AktiverZustand {
	protected ZugAutomat automat;

	public Zustand handle(Event event) {
		if (event instanceof KarteGewaehltEvent)
			return karteGewaehlt((KarteGewaehltEvent) event);
		else if (event instanceof BewegungEingegebenEvent)
			return bewegungEingegeben((BewegungEingegebenEvent) event);
		
		return super.handle(event);
	}

	Zustand karteGewaehlt(KarteGewaehltEvent event) {
		return keinUebergang();
	}

	Zustand bewegungEingegeben(BewegungEingegebenEvent event) {
		return keinUebergang();
	}

	Zustand keinUebergang() {
		throw new KeinUebergangException("Kein Übergang definiert in Zustand "
		                                 + this);
	}

	public void setAutomat(Automat automat) {
		this.automat = (ZugAutomat) automat;
	}
}
