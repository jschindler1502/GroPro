package controller;

import io.*;
import model.Datensatz;

import java.io.IOException;

/**
 * Programm zur Ausfuehrung der Signalauswertung
 */
public class SignalauswertungsProgramm {

    /**
     * Methode zum Starten des Programms mit allen Nebenlaeufigkeiten
     *
     * @param eingabeordner Name des Ordners inklusive Pfad, in dem sich die Eingabedateien befinden
     */
    public void starteProgramm(String eingabeordner) throws IOException {
        String eingabedatei_TEST = eingabeordner;
        String ausgabedateiname = eingabedatei_TEST.replace("input", "output");

        int indexEnde = eingabedatei_TEST.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedatei_TEST.length();
        }

        ausgabedateiname= ausgabedateiname.substring(0, indexEnde )+ "out"+ ausgabedateiname.substring(indexEnde);
        System.out.println("in programm" + ausgabedateiname);
        laufeProgramm(eingabedatei_TEST, ausgabedateiname);
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

            Datensatz d = IConverter.convertInputToDatensatz(gesamtInhalt, eingabedateiname);// TODO Typ

            // long msstart = System.currentTimeMillis()
            // TODO Threads
            //long msEnd = System.currentTimeMillis();
            //System.out.println("Die Kalkulationszeit beträgt " + (msEnd - msstart) + "ms");
            String ausgabetext = OConverter.convertDatensatzToOutput(d);
            d.werteDatensatzAus();
            IWriter dateiWriter = new DateiWriter(ausgabedateiname);
            dateiWriter.schreibeAusgabe(ausgabetext);
        } catch (ValidierungsException | AlgorithmusException e) { // in diesem Fall in Konsole und Datei schreiben
            IWriter dateiWriter = new DateiWriter(ausgabedateiname);
            IWriter konsolenWriter = new KonsoleWriter();
            dateiWriter.schreibeAusgabe(e.getMessage()); // falls hier Fehler, wird EingabeAusgabeException geworfen, in Main gefangen
            konsolenWriter.schreibeAusgabe(e.getMessage());
        }
    }
}
