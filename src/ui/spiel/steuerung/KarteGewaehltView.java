package ui.spiel.steuerung;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import applikation.client.controller.Steuerung;
import applikation.client.pd.Karte;
import applikation.client.pd.Karten;
import dienste.observer.ListChangeEvent;
import dienste.observer.ListChangeEvent.ListChangeType;

public class KarteGewaehltView extends JPanel implements Observer {
	private JLabel name;
	private JTextArea beschreibung;

	public KarteGewaehltView(Steuerung steuerung, Karten karten) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);

		name = new JLabel();
		Font nameFont = name.getFont().deriveFont(Font.BOLD, 16);
		name.setFont(nameFont);
		name.setForeground(Color.white);
		name.setOpaque(false);

		beschreibung = new JTextArea();
		// Dialog-Schrift setzen. Unter Windows haben wir sonst Courier als
		// Schriftart.
		Font beschreibungFont = new Font("Dialog",
        		                         beschreibung.getFont().getStyle(),
        		                         beschreibung.getFont().getSize());
		beschreibung.setFont(beschreibungFont);
		beschreibung.setLineWrap(true);
		beschreibung.setOpaque(false);
		beschreibung.setEnabled(false);
		beschreibung.setWrapStyleWord(true);
		beschreibung.setDisabledTextColor(Color.white);
		beschreibung.setRows(3);

		zeigeKeineKarte();

		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		beschreibung.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

		add(name);
		add(beschreibung);

		karten.addObserver(this);
	}

	public void update(Observable observable, Object arg) {
		if (arg instanceof ListChangeEvent) {
			ListChangeEvent change = (ListChangeEvent) arg;
			if (change.changeType == ListChangeType.CHANGED) {
				Karte karte = (Karte) change.changedObject;
				if (karte.istAusgewaehlt()) {
					zeigeKarte(karte);
				} else {
					zeigeKeineKarte();
				}
			}
		}
	}

	private void zeigeKarte(Karte karte) {
		name.setText(karte.toString());
		beschreibung.setText(karte.getRegelBeschreibung());
	}

	private void zeigeKeineKarte() {
		// Zeilenumbrüche sind ein Hack, damit die Grösse richtig berechnet wird.
		name.setText("\n");
		beschreibung.setText("\n\n");
	}
}
