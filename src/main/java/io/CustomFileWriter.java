package io;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Schreiben des IResults in Textdatei/Konsole
 */
public class CustomFileWriter implements IWriter {

    public void schreibeAusgabe(String ausgabe, String ausgabedateiname) throws IOException {
        File file = new File(ausgabedateiname + "_Ausgabe.txt");
        if (file.exists() && !file.canWrite()) {
            throw new TechnischeException("Technischer Fehler! Keine Schreibrechte auf Ausgabedatei");
        }
        FileWriter writer = new FileWriter(file);
        try {
            writer.append(ausgabe);
            writer.close();
        } catch (IOException e) {
            throw new TechnischeException("Technischer Fehler! Datei ist nicht beschreibbar");
        }
    }
}