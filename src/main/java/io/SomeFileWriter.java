package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import view.IView;

/**
 * Schreibt die Ausgabe (View oder Fehler) in eine Datei
 */
public class SomeFileWriter implements IWriter {

    private File file;

    public SomeFileWriter(File f) {
        if (f.exists() && f.canWrite() == false) {
            System.err
                    .println("Keine Schreibrechte auf Ausgabedatei");
            System.exit(1);
        }
        this.file = f;
    }

    @Override
    public void schreibeAusgabe(IView view) {
        write(view.erzeugeAusgabe());
    }

    @Override
    public void schreibeFehler(Exception ex) {
        write(ex.getMessage());
    }

    /**
     * Führt den eigentlichen Schreibvorgang aus
     *
     * @param s
     *            - String, der in die Datei geschrieben werden soll
     */
    private void write(String s) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(s).append('\n');
            writer.close();
        } catch (IOException ex) {
            System.err.println("Fehler beim Schreiben der Ausgabedatei");
            System.exit(1);
        }
    }
}
