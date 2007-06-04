package ui.spiel.chat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import applikation.client.Controller;



/**
 * Implementierung des Chat-Client, der zur Text-Kommunikation zwischen den
 * einzelnen Clients dient.
 */
public class ChatView extends JPanel {
	@SuppressWarnings("unused")
	private Controller controller;

	public ChatView(Controller controller) {
		this.controller = controller;

		TitledBorder titel = new TitledBorder("Chat");
		setBorder(titel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(new JLabel("Hier käme der Chat hin... \n(Prio 3)"));
	}
}
