package io;

import java.io.IOException;

/**
 * Exception Klasse fuer semantische Fehler in der Eingabedatei
 */
public class InvalidSemantikException extends IOException {

	public InvalidSemantikException(String message) {
		super(message);
	}

}
