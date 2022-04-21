package io;

import java.io.IOException;

/**
 * Interface zur Abstrahierung des Auslesens der Ausgabedatei
 */
public interface IWriter {
    void schreibeAusgabe(String ausgabe) throws EingabeAusgabeException;
}
