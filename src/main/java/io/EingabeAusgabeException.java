package io;

import java.io.IOException;

/**
 * Exception Klasse fuer technische Fehler beim Lesen der Eingabedatei oder Schreiben der Ausgabedatei
 */
public class EingabeAusgabeException extends IOException {

    public EingabeAusgabeException(String message) {
        super(message);
    }

}
