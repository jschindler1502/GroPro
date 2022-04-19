package io;

import model.IResult;

import java.io.IOException;

/**
 * Klasse zum Schreiben des IResults in Textdatei/Konsole
 */
public class FeldWriter implements IWriter {

    @Override
    public void schreibeAusgabe(IResult result, OutputTyp typ, String ausgabedateiname) throws TechnischeException {

        try {
            switch (typ) {
                case DATEI -> OutputConverter.convertResultToOutput(result, OutputTyp.DATEI, ausgabedateiname);
                case MONITOR -> OutputConverter.convertResultToOutput(result); // OutputTyp.Monitor
            }
        } catch (IOException e) {
            throw new TechnischeException("Technischer Fehler! Datei ist nicht beschreibbar");
        }
    }
}