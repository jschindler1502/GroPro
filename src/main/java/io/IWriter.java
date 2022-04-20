package io;

import model.IResult;

import java.io.IOException;

/**
 * Interface zur Abstrahierung des Auslesens der Ausgabedatei
 */
public interface IWriter {
    public void schreibeAusgabe(String ausgabe, String ausgabedateiname) throws IOException;
}
