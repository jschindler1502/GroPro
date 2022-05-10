package io;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Klasse zum Schreiben des IResults in Textdatei/Konsole
 */
public class DateiWriter implements IWriter {
    private final String eingabedateiName;

    public DateiWriter(String eingabedateiName) {
        this.eingabedateiName = eingabedateiName;
    }

    public void schreibeAusgabe(String ausgabe) throws IOException {
        File file = new File(getAusgabedateiname(eingabedateiName) );
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
    private String getAusgabedateiname(String eingabedateiname) {
        String ausgabedateiname = eingabedateiname.replace("input", "output");

        int indexEnde = eingabedateiname.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedateiname.length();
        }

        return ausgabedateiname.substring(0, indexEnde) + "out" + ausgabedateiname.substring(indexEnde);
    }
}