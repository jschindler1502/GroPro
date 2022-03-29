package io;

// TODO import model, das zurueckgegeben wird

import java.io.IOException;

/**
 * Interface zur Abstrahierung der Initialiserung des Programms
 */
public interface IReader {

    /**
     * Gibt TODO zurück
     * @return TODO
     * @throws InputException
     * TODO wenn die Eingabeparameter ungültig sind
     */
    String readFileContent() throws IOException;

}
