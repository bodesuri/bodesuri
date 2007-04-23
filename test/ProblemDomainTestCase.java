import PD.Spielerverwaltung.Spieler;
import PD.Zugsystem.BankFeld;
import PD.Zugsystem.Brett;
import PD.Zugsystem.Feld;
import PD.Zugsystem.Figur;
import PD.Zugsystem.WegFeld;
import junit.framework.TestCase;

public abstract class ProblemDomainTestCase extends TestCase {
	protected Brett brett;
	protected Spieler spieler;
	protected Figur figur;
	protected BankFeld bankFeld;
	protected Feld zielFeld;
	
	protected void setUp() throws Exception {
		brett = new Brett();
		spieler = new Spieler("Frodo");
		
		figur = new Figur(spieler);
		
		bankFeld = new BankFeld();
		bankFeld.setSpieler(spieler);
		bankFeld.setFigur(figur);
		
		Feld feld1 = bankFeld;
		Feld feld2;
		
		for (int i = 0; i < 3; ++i) {
			feld2 = new WegFeld();
			feld2.setVorheriges(feld1);
			feld1.setNaechstes(feld2);
			feld1 = feld2;
		}
		zielFeld = feld1;
	}

}