package controller.Exceptions;


/**
 * Exception Klasse fuer semantische oder syntaktische Fehler in einer Eingabedatei
 */
public class ValidierungsException extends RuntimeException {

	public ValidierungsException(String message) {
		super(message);
	}

}
