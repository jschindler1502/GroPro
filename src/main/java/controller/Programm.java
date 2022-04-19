package controller;

import io.FeldReader;
import io.FeldWriter;
import io.InputConverter;
import io.OutputTyp;
import model.FehlerResult;
import model.Result;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Programm zur Ausfuehrung der Antennenfindung
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
            FeldWriter writer = new FeldWriter();
            for (String fehlerMessage :
                    fehlerliste) {
                try {
                    writer.schreibeAusgabe(new FehlerResult(fehlerMessage), OutputTyp.MONITOR, ausgabedateiname);
                    writer.schreibeAusgabe(new FehlerResult(fehlerMessage), OutputTyp.DATEI, ausgabedateiname);
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
            // TODO Konvertiere zu model Object
            Object modelObj = InputConverter.convertInputToFeld(gesamtInhalt);
            Result result = new BacktrackingAlgorithmus(modelObj).findeMinimaleAntennen();
            FeldWriter writer = new FeldWriter();
            writer.schreibeAusgabe(result, OutputTyp.MONITOR, ausgabedateiname);
            writer.schreibeAusgabe(result, OutputTyp.DATEI, ausgabedateiname);
        } catch (Exception e) {
            fehlerliste.add(e.getMessage());
            return false;
        }
        return true;
    }
}
