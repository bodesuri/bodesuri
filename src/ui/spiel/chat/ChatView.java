package ui.spiel.chat;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import applikation.client.controller.Steuerung;

/**
 * Implementierung des Chat-Client, der zur Text-Kommunikation zwischen den
 * einzelnen Clients dient.
 */
public class ChatView extends JPanel {
	public ChatView(Steuerung controller) {
		// Layout setzen
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.darkGray);

		// Layout zusammenstellen
		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));
	}
}
