package io;



import java.io.IOException;

/**
 * Schnittstelle zur Abstrahierung des Einlesens der Eingabedatei
 */
public interface IReader {

    String lies() throws EingabeAusgabeException;
}
