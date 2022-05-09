package controller;

import io.*;
import model.Ergebnis;

import java.io.IOException;

/**
 * Programm zur Ausfuehrung der //TODO
 */
public class Programm {

    /**
     * Methode zum Starten des Programms
     *
     * @param eingabedateiname Name der Eingabedatei inklusive Pfad
     */
    public void starteProgramm(String eingabedateiname) throws IOException {

        String ausgabedateiname = eingabedateiname.replace("input", "output");
        int indexEnde = eingabedateiname.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedateiname.length();
        }
        ausgabedateiname = ausgabedateiname.substring(0, indexEnde + 1);

        laufeProgramm(eingabedateiname, ausgabedateiname);

    }


    /**
     * Methode, die den eigentlichen Programmablauf uebernimmt
     *
     * @param eingabedateiname Name der Eingabedatei inklusive Pfad
     * @param ausgabedateiname Name der Ausgabedatei inklusive Pfad
     */
    private void laufeProgramm(String eingabedateiname, String ausgabedateiname) throws IOException {
        try {
            DateiReader reader = new DateiReader(eingabedateiname);
            String gesamtInhalt = reader.lies();

            Object modelObj = IOConverter.convertInputToDataObject(gesamtInhalt);// TODO Typ

            // long msstart = System.currentTimeMillis()
            Ergebnis ergebnis = new BacktrackingStrategie(modelObj).findeResult();
            //long msEnd = System.currentTimeMillis();
            //System.out.println("Die Kalkulationszeit beträgt " + (msEnd - msstart) + "ms");

            String ausgabetext = IOConverter.convertResultToOutput(ergebnis, AusgabeTyp.MONITOR, ausgabedateiname);// bisher fuer jeden Typ gleiche Konvertierung und filename egal, evtl aus Methode entfernen

            IWriter dateiWriter = new DateiWriter(ausgabedateiname);
            IWriter konsolenWriter = new KonsoleWriter();
            dateiWriter.schreibeAusgabe(ausgabetext);
            konsolenWriter.schreibeAusgabe(ausgabetext);
        } catch (ValidierungsException | AlgorithmusException e) { // in diesem Fall in Konsole und Datei schreiben
            IWriter dateiWriter = new DateiWriter(ausgabedateiname);
            IWriter konsolenWriter = new KonsoleWriter();
            dateiWriter.schreibeAusgabe(e.getMessage()); // falls hier Fehler, wird EingabeAusgabeException geworfen, in Main gefangen
            konsolenWriter.schreibeAusgabe(e.getMessage());
        }
    }
}
