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
    public String readFileContent() throws InputException {
        BufferedReader reader;
        // Nummer der akuell gelesenen Zeile ohne Kommentarzeilen
        int lineNumber = 0;
        String line = "";
        String final_line = "";
        try {
            reader = new BufferedReader(new FileReader(input));

            line  = reader.readLine();
            System.out.println(line);
            // Bearbeite alle Zeilen
            while (line != null) {
                final_line = line;
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

        return final_line;// TODO
    }
}