package io;

import java.io.IOException;

/**
 * Exception Klasse fuer syntaktische Fehler in der Eingabedatei
 */
public class InvalidSyntaxException extends IOException {

	public InvalidSyntaxException(String message) {
		super(message);
	}

}
