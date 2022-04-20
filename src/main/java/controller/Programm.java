package controller;

import io.*;
import model.FehlerResult;
import model.Result;

import java.io.IOException;
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
    public void starteProgramm(String eingabedateiname) {


        String ausgabedateiname = eingabedateiname.replace("input", "output");
        int indexEnde = eingabedateiname.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedateiname.length();
        }
        ausgabedateiname = ausgabedateiname.substring(0, indexEnde + 1);


        boolean erfolgreichDurchgelaufen;

        erfolgreichDurchgelaufen = laufeProgramm(eingabedateiname, ausgabedateiname);


        if (!erfolgreichDurchgelaufen) {
            for (String fehlerMessage :
                    fehlerliste) {
                try {
                    IWriter fileWriter = new CustomFileWriter();
                    IWriter consoleWriter = new ConsoleWriter();
                    fileWriter.schreibeAusgabe(fehlerMessage, ausgabedateiname);
                    consoleWriter.schreibeAusgabe(fehlerMessage, ausgabedateiname);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Methode, die den eigentlichen Programmablauf uebernimmt
     *
     * @param eingabedateiname Name der Eingabedatei inklusive Pfad
     * @param ausgabedateiname Name der Ausgabedatei inklusive Pfad
     * @return true, wenn das Programm ohne Fehler durchgelaufen ist und ein Result generiert hat
     */
    private boolean laufeProgramm(String eingabedateiname, String ausgabedateiname) {
        try {
            FeldReader reader = new FeldReader(eingabedateiname);
            String gesamtInhalt = reader.lies();

            Object modelObj = InputOutputConverter.convertInputToDataObject(gesamtInhalt);// TODO Typ

            Result ergebnis = new BacktrackingStrategie(modelObj).findeMinimaleAntennen();

            String ausgabetext = InputOutputConverter.convertResultToOutput(ergebnis,OutputTyp.MONITOR,ausgabedateiname);// bisher fuer jeden Typ gleiche Konvertierung und filename egal, evtl aus Methode entfernen

            IWriter fileWriter = new CustomFileWriter();
            IWriter consoleWriter = new ConsoleWriter();
            fileWriter.schreibeAusgabe(ausgabetext, ausgabedateiname);
            consoleWriter.schreibeAusgabe(ausgabetext, ausgabedateiname);
            return true;
        } catch (Exception e) {
            fehlerliste.add(e.getMessage());
            return false;
        }
    }
}
