package controller;


/**
 * Exception Klasse fuer syntaktische Fehler in der Eingabedatei
 */
public class ValidierungsException extends RuntimeException {

	public ValidierungsException(String message) {
		super(message);
	}

}
