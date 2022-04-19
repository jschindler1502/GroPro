package io;

import model.IResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Klasse zum Konvertieren des IResults in Textdatei/Gnuplot/Konsole
 * statisch, da kein Objekt der Klasse zum Konvertieren benoetigt wird
 */
public class OutputConverter {
    /**
     * Erstellt Ausgabe je nach gewuenschtem Ausgabetyp
     *
     * @param result Das Ergebnis des Algorithmus
     * @param typ Ausgabetyp
     * @param ausgabedateiname Name der Ausgabedatei inklusive Pfad
     * @throws IOException wenn das Schreiben zu einem Fehler fuehrt
     */
    public static void convertResultToOutput(IResult result, OutputTyp typ, String ausgabedateiname) throws IOException {
        try {
            if (typ == OutputTyp.DATEI) {
                convertToFileOutput(result, ausgabedateiname);
            }
        } catch (IOException e) {
            throw new TechnischeException(e.getMessage());
        }
    }

    public static void convertResultToOutput(IResult result) throws IOException { // always case Monitor
        System.out.println(result.getGesamtAusgabe());
    }

    private static void convertToFileOutput(IResult result, String ausgabedateiname) throws IOException {
        File file = new File(ausgabedateiname + "_Ausgabe.txt");
        if (file.exists() && !file.canWrite()) {
            System.err
                    .println("Technischer Fehler! Keine Schreibrechte auf Ausgabedatei");
            System.exit(1);
        }
        FileWriter writer = new FileWriter(file);
        writer.append(result.getGesamtAusgabe());
        writer.close();

    }
}
