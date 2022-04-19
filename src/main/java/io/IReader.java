package io;



import java.io.IOException;

/**
 * Schnittstelle zur Abstrahierung des Einlesens der Datei
 */
public interface IReader {

    String lies() throws IOException;
}
