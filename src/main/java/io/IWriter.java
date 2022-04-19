package io;

import model.IResult;

/**
 * Interface zur Abstrahierung des Auslesens des Results
 */
public interface IWriter {

    void schreibeAusgabe(IResult result, OutputTyp typ, String ausgabedateiname) throws TechnischeException;

}
