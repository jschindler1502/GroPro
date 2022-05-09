package io;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Schreiben des IResults in Textdatei/Konsole
 */
public class DateiWriter implements IWriter {
    private final String ausgabedateiname;

    public DateiWriter(String ausgabedateiname) {
        this.ausgabedateiname = ausgabedateiname;
    }

    public void schreibeAusgabe(String ausgabe) throws IOException {
        File file = new File(ausgabedateiname );
        if (file.exists() && !file.canWrite()) {
            throw new IOException("Eingabe/Ausgabe Fehler: keine Schreibrechte auf Ausgabedatei");
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.append(ausgabe);
            writer.close();
        } catch (IOException e) {
            throw new IOException("Eingabe/Ausgabe Fehler: beim Schreiben der Ausgabedatei");
        }
    }
}