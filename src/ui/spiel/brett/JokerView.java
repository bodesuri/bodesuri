package ui.spiel.brett;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pd.karten.Acht;
import pd.karten.Ass;
import pd.karten.Bube;
import pd.karten.Dame;
import pd.karten.Drei;
import pd.karten.Fuenf;
import pd.karten.KartenFarbe;
import pd.karten.Koenig;
import pd.karten.Neun;
import pd.karten.Sechs;
import pd.karten.Sieben;
import pd.karten.Vier;
import pd.karten.Zehn;
import pd.karten.Zwei;
import ui.erweiterungen.ClickMouseAdapter;
import ui.ressourcen.BrettXML;
import ui.ressourcen.Icons;
import ui.spiel.karten.KarteMouseAdapter;
import ui.spiel.karten.KarteView;
import ui.spiel.karten.KartenAuswahl;
import applikation.client.controller.Steuerung;
import applikation.client.pd.Joker;
import applikation.client.pd.Karte;

public class JokerView extends JPanel {
	private Vector<Karte> kartenDeck;
	private BrettXML brettXML;

	public JokerView(Dimension groesse, Steuerung steuerung) {
		setLayout(null);
		setBackground(new Color(0, 0, 0, 100));
		setOpaque(false);

		erstelleDeck();

		try {
			brettXML = new BrettXML("/ui/ressourcen/brett.xml");
		} catch (Exception e) {
			// Checked Exception in unchecked umwandeln
			throw new RuntimeException(e);
		}

		KarteMouseAdapter kma = new KarteMouseAdapter(steuerung);
		KartenAuswahl ka = new KartenAuswahl();
		
		Vector<KarteView> karteViews = new Vector<KarteView>();
		for (int i = 0; i < kartenDeck.size(); i++) {
			KarteView kv = new KarteView(brettXML.getJokerKarten().get(i),
					kma, ka);
			karteViews.add(kv);
			kv.setKarte(kartenDeck.get(i));
			add(kv);
		}

		JLabel jokerSchliessen = new JLabel();
		jokerSchliessen.setIcon(Icons.JOKERSCHLIESSEN);
		Point pos = brettXML.getJokerKarten().get(13);
		jokerSchliessen.setBounds(pos.x, pos.y, Icons.JOKERSCHLIESSEN
				.getIconWidth(), Icons.JOKERSCHLIESSEN.getIconHeight());
		jokerSchliessen.addMouseListener(new ClickMouseAdapter() {
			public void clicked(MouseEvent e) {
				setVisible(false);
				
			}
		});
		add(jokerSchliessen);

		JLabel hintergrund = new JLabel();
		hintergrund.setBackground(new Color(0, 0, 0, 178));
		hintergrund.setOpaque(true);
		hintergrund.setBounds(0, 0, (int)groesse.getWidth(), (int)groesse.getHeight());

		add(hintergrund);
	}

	public void erstelleDeck() {
		kartenDeck = new Vector<applikation.client.pd.Karte>();
		kartenDeck.add(new Joker(new Karte(new Ass(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Koenig(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Bube(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Dame(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Zehn(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Neun(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Acht(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Sieben(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Sechs(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Fuenf(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Vier(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Drei(KartenFarbe.Herz))));
		kartenDeck.add(new Joker(new Karte(new Zwei(KartenFarbe.Herz))));
	}
}
