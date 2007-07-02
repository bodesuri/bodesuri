package ui.spiel.brett;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ressourcen.Icons;
import applikation.client.pd.Spieler;

/**
 * Dient zur Darstellung des Zustandes eines Spielers, ob er am Zug ist oder
 * aufgeben musste.
 */
public class SpielerZustandView extends JPanel implements Observer {
	private Spieler spieler;
	private JLabel icon;

	public SpielerZustandView(Spieler spieler, Point position) {
		this.spieler = spieler;

		setLayout(new GridBagLayout());
		setOpaque(false);
		setSize(70, 70);
		setLocation(position.x - 35, position.y - 35);

		icon = new JLabel();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		add(icon, c);

		spieler.addObserver(this);
	}

	/**
	 * OBSERVER-PATTERN: OBSERVER 
	 * Überschreibt <code>update()</code> Methode des Observer.
	 * 
	 * @param o zu observierendes Objekt
	 * @param arg Objekt
	 */
	public void update(Observable o, Object arg) {
		if (spieler.amZug()) {
			icon.setIcon(Icons.AM_ZUG);
		} else if (spieler.hatAufgegeben()) {
			icon.setIcon(Icons.FAHNE);
		} else {
			icon.setIcon(null);
		}
	}
}
