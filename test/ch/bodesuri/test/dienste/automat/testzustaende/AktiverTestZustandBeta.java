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


package ch.bodesuri.test.dienste.automat.testzustaende;

import ch.bodesuri.dienste.automat.zustaende.Zustand;
import ch.bodesuri.dienste.eventqueue.Event;
import ch.bodesuri.test.dienste.automat.events.TestEventC;

public class AktiverTestZustandBeta extends AktiverTestZustand {
	public Class<? extends Zustand> handle(Event e) {
		if (e instanceof TestEventC)
			c();

		return super.handle(e);
	}

	Class<? extends Zustand> a() {
		return AktiverTestZustandBeta.class;
    }

	Class<? extends Zustand> b() {
		return AktiverTestZustandBeta.class;
    }

	Class<? extends Zustand> c() {
		return keinUebergang();
	}
}
