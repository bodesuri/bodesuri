package spielplatz.hilfsklassen;

public class Registrierung implements Nachricht {
	private static final long serialVersionUID = -495776719176123894L;

	public String name;

	public Registrierung(String name) {
		this.name = name;
	}
}