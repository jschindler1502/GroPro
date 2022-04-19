package io;

import java.io.IOException;

/**
 * Exception Klasse fuer technische Fehler beim Lesen der Eingabedatei oder Schreiben der Ausgabedatei
 */
public class TechnischeException extends IOException {

    public TechnischeException(String message) {
        super(message);
    }

}
