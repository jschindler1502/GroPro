package controller;

import io.*;
import model.AlgorithmusException;
import model.Result;
import model.ValidierungsException;

import java.util.ArrayList;

/**
 * Programm zur Ausfuehrung der //TODO
 */
public class Programm {
    private ArrayList<String> fehlerliste = new ArrayList<>();

    /**
     * Methode zum Starten des Programms
     *
     * @param eingabedateiname Name der Eingabedatei inklusive Pfad
     */
    public void starteProgramm(String eingabedateiname) throws EingabeAusgabeException {

        String ausgabedateiname = eingabedateiname.replace("input", "output");
        int indexEnde = eingabedateiname.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedateiname.length();
        }
        ausgabedateiname = ausgabedateiname.substring(0, indexEnde + 1);

        laufeProgramm(eingabedateiname, ausgabedateiname);



//        for (String fehlerMessage :
//                fehlerliste) {
//            try {
//                IWriter fileWriter = new CustomFileWriter();
//                IWriter consoleWriter = new ConsoleWriter();
//                fileWriter.schreibeAusgabe(fehlerMessage, ausgabedateiname);
//                consoleWriter.schreibeAusgabe(fehlerMessage, ausgabedateiname);
//            } catch (IOException e) {
//                System.err.println(e.getMessage());
//            }
//        }
    }


    /**
     * Methode, die den eigentlichen Programmablauf uebernimmt
     *
     * @param eingabedateiname Name der Eingabedatei inklusive Pfad
     * @param ausgabedateiname Name der Ausgabedatei inklusive Pfad
     * @return true, wenn das Programm ohne Fehler durchgelaufen ist und ein Result generiert hat
     */
    private void laufeProgramm(String eingabedateiname, String ausgabedateiname) throws EingabeAusgabeException {
        // TODO in fehlerliste adden (damit mehrere Fehler auftreten dürfen?) oder Abbruch -> äußeres try catch
        try {
            CustomReader reader = new CustomReader(eingabedateiname);
            String gesamtInhalt = reader.lies();

            Object modelObj = InputOutputConverter.convertInputToDataObject(gesamtInhalt);// TODO Typ

            // long msstart = System.currentTimeMillis()
            Result ergebnis = new BacktrackingStrategie(modelObj).findeResult();
            //long msEnd = System.currentTimeMillis();
            //System.out.println("Die Kalkulationszeit beträgt " + (msEnd - msstart) + "ms");

            String ausgabetext = InputOutputConverter.convertResultToOutput(ergebnis, OutputTyp.MONITOR, ausgabedateiname);// bisher fuer jeden Typ gleiche Konvertierung und filename egal, evtl aus Methode entfernen

            IWriter fileWriter = new CustomFileWriter(ausgabedateiname);
            IWriter consoleWriter = new ConsoleWriter();
            fileWriter.schreibeAusgabe(ausgabetext);
            consoleWriter.schreibeAusgabe(ausgabetext);
        } catch (ValidierungsException | AlgorithmusException e) { // in diesem Fall in Konsole und Datei schreiben
            IWriter fileWriter = new CustomFileWriter(ausgabedateiname);
            IWriter consoleWriter = new ConsoleWriter();
            fileWriter.schreibeAusgabe(e.getMessage()); // falls hier Fehler, wird EingabeAusgabeException geworfen, in Main gefangen
            consoleWriter.schreibeAusgabe(e.getMessage());
        }

    }
}
