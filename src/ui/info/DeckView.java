/**
 * @(#) DeckView.java
 */

package ui.info;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class DeckView extends JPanel {

    private static final long serialVersionUID = 1L;

    public DeckView() {
		TitledBorder titel = new TitledBorder("Karten");
		// titel.setTitleFont(titel.getTitleFont().deriveFont(Font.BOLD));
		setBorder(titel);
		setLayout(new FlowLayout());
		setAlignmentX(Component.LEFT_ALIGNMENT);
		Dimension groesse = new Dimension(100, 100);
		setPreferredSize(groesse);
		setMaximumSize(groesse);
		setMinimumSize(groesse);
		
		this.add(new JLabel("Hier kommen die Karten hin..."));
    }
}
