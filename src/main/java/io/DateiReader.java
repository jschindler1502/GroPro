package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Klasse zum Lesen des kompletten Eingabeinhalts aus einer Textdatei
 */
public class DateiReader implements IReader {

    private final String eingabedateiname;

    public DateiReader(String eingabedateiname) {
        this.eingabedateiname = eingabedateiname;
    }

    @Override
    public String lies() throws IOException {
        File eingabedatei = new File(this.eingabedateiname);
        // Datei muss existieren
        if (!eingabedatei.exists()) {
            throw new IOException("Eingabe/Ausgabe Fehler: Eingabedatei existiert nicht");
        }
        // Datei muss lesbar sein
        if (!eingabedatei.canRead()) {
            throw new IOException("Eingabe/Ausgabe Fehler: Eingabedatei ist nicht lesbar");
        }
        BufferedReader reader;
        String zeile = "";
        StringBuilder gesamtInhalt = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(eingabedateiname));
            zeile = reader.readLine();

            // Bearbeite alle Zeilen
            while (zeile != null) {
                // führende und abschließende Whitespaces ignorieren
                zeile = zeile.trim();
                gesamtInhalt.append(zeile).append("\n");
                zeile = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            throw new IOException("Eingabe/Ausgabe Fehler: beim Lesen der Eingabedatei");
        }
        return gesamtInhalt.toString();
    }
}