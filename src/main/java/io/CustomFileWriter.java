package io;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Schreiben des IResults in Textdatei/Konsole
 */
public class CustomFileWriter implements IWriter {
    private final String ausgabedateiname;

    public CustomFileWriter(String ausgabedateiname) {
        this.ausgabedateiname = ausgabedateiname;
    }

    public void schreibeAusgabe(String ausgabe) throws EingabeAusgabeException {
        File file = new File(ausgabedateiname + "_Ausgabe.txt");
        if (file.exists() && !file.canWrite()) {
            throw new EingabeAusgabeException("Technischer Fehler: keine Schreibrechte auf Ausgabedatei");
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(ausgabe);
            writer.close();
        } catch (IOException e) {
            throw new EingabeAusgabeException("Technischer Fehler: Datei ist nicht beschreibbar");
        }
    }
}