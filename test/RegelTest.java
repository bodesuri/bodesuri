import pd.brett.BankFeld;
import pd.brett.Feld;
import pd.karten.Karte;
import pd.karten.KartenFarbe;
import pd.karten.Vier;
import pd.regelsystem.Regel;
import pd.regelsystem.RegelVerstoss;
import pd.regelsystem.StartRegel;
import pd.zugsystem.Bewegung;
import pd.zugsystem.Zug;
import pd.zugsystem.ZugEingabe;

public class RegelTest extends ProblemDomainTestCase {
	private Karte vier;
	protected BankFeld bankFeld;
	protected Feld zielFeld;
	
	public void setUp() throws Exception {
		super.setUp();
		vier = new Vier(KartenFarbe.Herz, 0);
		bankFeld = brett.getBankFeldVon(spieler1);
		zielFeld = bankFeld.getNtesFeld(4);
	}
	
	public void testVier() throws RegelVerstoss {
		bankFeld.setFigur(figur1);
		Bewegung bewegung4 = new Bewegung(bankFeld, zielFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertFalse(bewegung4.getStart().istBesetzt());
		assertTrue(bewegung4.getZiel().istBesetztVon(spieler1));
	}
	
	public void testVierFalsch() {
		bankFeld.setFigur(figur1);
		Bewegung bewegung3 = new Bewegung(bankFeld, zielFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}

	public void testVierRueckwaerts() throws RegelVerstoss {
		zielFeld.setFigur(figur1);
		Bewegung bewegung4 = new Bewegung(zielFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung4);
		
		Zug zug = ze.validiere();
		zug.ausfuehren();
		assertFalse(bewegung4.getStart().istBesetzt());
		assertTrue(bewegung4.getZiel().istBesetztVon(spieler1));
	}
	
	public void testVierRueckwaertsFalsch() {
		zielFeld.setFigur(figur1);
		Bewegung bewegung3 = new Bewegung(zielFeld, bankFeld.getVorheriges());
		ZugEingabe ze = new ZugEingabe(spieler1, vier, bewegung3);
		try {
			ze.validiere();
			fail("Sollte RegelVerstoss geben.");
		} catch (RegelVerstoss rv) {
			assertNotNull(rv);
		}
	}
	
	public void testStartRegel() throws RegelVerstoss {
		Feld lagerFeld = brett.getLagerFelderVon(spieler1).get(0);
		
		Bewegung bewegung = new Bewegung(lagerFeld, bankFeld);
		ZugEingabe ze = new ZugEingabe(spieler1, null, bewegung);
		
		Regel regel = new StartRegel();
		Zug zug = regel.validiere(ze);
		zug.ausfuehren();
		assertFalse(lagerFeld.istBesetzt());
		assertTrue(bankFeld.istBesetztVon(spieler1));
	}
}
