package pd.spielerverwaltung;

import java.util.Vector;

import pd.zugsystem.Figur;
import spielplatz.EndPunkt;

public class Spieler {
	private String name;
	public EndPunkt endpunkt;
	
	private Vector<Figur> figuren = new Vector<Figur>();
	
	public Spieler() {
		for (int i = 0; i < 4; ++i) {
			figuren.add(new Figur(this));
		}
	}
	
	public Spieler(String name) {
		this();
		this.name = name;
	}

	public Spieler(EndPunkt client, String name) {
		this(name);
		this.endpunkt = client;
	}

	public String toString() {
		return "Spieler " + getName();
	}

	public String getName() {
    	return name;
    }
	
	public void setName(String name) {
		this.name = name;
	}

	// Wird für das CLI verwendet, um die Spieler anzeigen zu können
	public Vector<Figur> getFiguren() {
    	return figuren;
    }
}
