package spielplatz;

import applikation.client.konfiguration.Konfiguration;
import initialisierung.Bodesuri;
import initialisierung.BodesuriServer;

public class BodesuriSingular {

	public static void main(String[] args) throws InterruptedException {
		BodesuriServer server = new BodesuriServer(1);
		server.start();
		server.warteAufBereitschaft();

		Konfiguration konfig = new Konfiguration();
		konfig.defaultName = "Huere Michi";
		konfig.debugAutoLogin = true;

		Bodesuri a = new Bodesuri(konfig);
		a.start();

		a.join();
	}
}
