package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// TODO import model

/**
 * Liest die Eingabe zeilenweise aus einer Datei und initialisiert so das
 * Programm
 */

public class SomeFileReader implements IReader {

    private File input;

    public SomeFileReader(File input) {
        this.input = input;
    }

    @Override
    public Object readFileContent() throws InputException {
        BufferedReader reader;
        // Nummer der akuell gelesenen Zeile ohne Kommentarzeilen
        int lineNumber = 0;

        try {
            reader = new BufferedReader(new FileReader(input));

            String line = reader.readLine();
            // Bearbeite alle Zeilen
            while (line != null) {

                // Ueberspringe Kommentarzeilen
                if (line.startsWith(";")) {
                    line = reader.readLine();
                    continue;
                }
                // Erhöhe aktuelle Zeilennummer
                lineNumber++;

                // Führende und Abschließende Whitespaces ignorieren
                line = line.trim();
                // An Leerstellen trennen
                String[] values = line.split("[ ]+");

                // TODO

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            throw new InputException(
                    "Fehler beim Lesen der Eingabedatei");
        }
//        try {
//            // set up readers
//            StringBuilder sB = new StringBuilder();
//            FileReader fR = new FileReader(input);
//            BufferedReader bR = new BufferedReader(fR);
//
//            // fill lines into StringBuilder
//            String line;
//            boolean isFirstLine = true;
//            while (((line = bR.readLine()) != null)) {
//                if (!isFirstLine) {
//                    // append linebreaks for new lines
//                    sB.append("\n");
//                } else {
//                    isFirstLine = false;
//                }
//                sB.append(line);
//            }
//            return sB.toString();
//        } catch (IOException e) {
//            throw new InputException(
//                    "Fehler beim Lesen der Eingabedatei");
//        }

        /*
         * Es müssen mindestens Dimension der Fläche und Startparzelle gelesen
         * werden
         */
        if (lineNumber < 2) {
            throw new InputException(
                    "Es müssen mindestens 2 Zeilen angegeben werden");
        }

        return new Object();// TODO
    }
}