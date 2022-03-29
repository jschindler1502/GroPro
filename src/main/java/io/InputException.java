package io;

import java.io.IOException;

/**
 * Exception Klasse für Fehler in der Eingabedatei 
 */
public class InputException extends IOException {

	public InputException(String message) {
		super(message);
	}
	
	public InputException(String message, Throwable cause) {
		super(message, cause);
	}
}
