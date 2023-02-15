package exceptions;

/**
 * beurtException vertegenwoordigt voor exceptions van een speelbeurt.
 */
public class beurtException extends IllegalArgumentException {

	private static final long serialVersionUID = -42454924128676986L;
	public beurtException() {
		super("De beurt is fout verlopen.");
	}
	public beurtException(String message) {
		super(message);
	}
	public beurtException(String message, Throwable cause) {
		super(message,cause);
	}
	public beurtException(Throwable cause) {
		super(cause);
	}

}
