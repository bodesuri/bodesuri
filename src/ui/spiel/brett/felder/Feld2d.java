package ui.spiel.brett.felder;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;

import applikation.client.pd.Feld;

/**
 * JLabel, ist die Oberklase aller Felder. Von dieser Klasse leiten alle
 * weiteren Typen von Feldern ab.
 */
public abstract class Feld2d extends javax.swing.JLabel implements Observer {
	private Point position;
	private final Feld feld;
	protected Icon icon;
	private FigurenManager figurenManager;

	public Feld2d(Point p, Feld feld, FeldMouseAdapter mouseAdapter, Icon icon, FigurenManager figurenManager) {
		super(icon);
		this.icon = icon;
		this.position = p;
		this.feld = feld;
		this.figurenManager = figurenManager;

		feld.addObserver(this);

		addMouseListener(mouseAdapter);
		update(null, null); /* TODO:: evtl. schöner machen */
	}

	private void zeichne(Icon icon) {
		int posx = position.x - (icon.getIconWidth() / 2);
		int posy = position.y - (icon.getIconHeight() / 2);
		setIcon(icon);
		setBounds(posx, posy, icon.getIconWidth(), icon.getIconHeight());
	}

	public int getPointX() {
		return position.x;
	}

	public int getPointY() {
		return position.y;
	}

	public Feld getFeld() {
		return feld;
	}

	public void update(Observable os, Object arg) {
		/* Prüfen ob Feld mit einer Figur bestückt weden muss */
		if (feld.istBesetzt()) {
			/* Figur drauf stellen */
			Figur2d figur = figurenManager.get(feld.getFigur());


			/* wenn nur ein oder zwei Spieler mitspielen können
			 * einige figuren null sein. Dann zeichnen wir einfach ni.
			 */
			if (figur != null) {
				figur.setzeAuf(this);
			}
		}

		Icon icon;
		/* prüfen wir ob selektiert */
		if (feld.getAusgewaehlt()) {
			icon = getAktivesIcon();
		} else if (feld.getHover()) {
			icon = getAktivesIcon();
		} else {
			icon = getPassivesIcon();
		}

		zeichne(icon);
	}

	public abstract Icon getAktivesIcon();

	public Icon getPassivesIcon() {
		return icon;
	}
}
