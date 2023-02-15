package exceptions;

public class kansException extends IllegalArgumentException {

	/**kansException is een fout die gegooit wordt indien er onvoldoende speelkansen zijn
	 * 
	 */
	private static final long serialVersionUID = -4101346908859214331L;
	public kansException() {
		super("Er zijn te weinig speelkansen");
	}
	public kansException(String message) {
		super(message);
	}
	public kansException(String message, Throwable cause) {
		super(message,cause);
	}
	public kansException(Throwable cause) {
		super(cause);
	}

}
