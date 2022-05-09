package controller;

import io.*;
import model.Datensatz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Programm zur Ausfuehrung der Signalauswertung
 */
public class SignalauswertungsProgramm {
    final private String eingabeordner;
    private ArrayList<String> offeneDateien = new ArrayList<>(); // offen heisst, sie wurden noch nicht ausgelesen und dann konvertiert
    private ArrayList<Datensatz> konvertierteDatensaetze = new ArrayList<>();
    private ArrayList<Datensatz> verarbeiteteDatensaetze = new ArrayList<>();
    private ArrayList<Datensatz> geschlosseneDateien = new ArrayList<>(); // geschlossen heisst, sie wurden eingelesen, konvertiert, verarbeitet und ausgelesen


    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;


    /**
     * @param eingabeordner Name des Ordners inklusive Pfad, in dem sich die Eingabedateien befinden
     */
    public SignalauswertungsProgramm(String eingabeordner) throws IOException {
        this.eingabeordner = eingabeordner;
        /*File folder = new File(eingabeordner);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null || listOfFiles.length == 0){
            throw new IOException("Technischer Fehler: es wurden keine Eingabedateien im Ordner /testfaelle/input hinterlegt.");
        }
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                offeneDateien.add(listOfFile.getName());
            }
        }*/

//        Thread einleseThread = new Thread();
//        Thread konvertiereThread = new Thread();
//        Thread verarbeiteThread = new Thread();
//        Thread ausleseThread = new Thread();
    }

    /**
     * Methode zum Starten des Programms mit allen Nebenlaeufigkeiten
     */
    public void starteProgramm() throws IOException {


        String eingabedatei_TEST = eingabeordner;
        String ausgabedateiname = eingabedatei_TEST.replace("input", "output");

        int indexEnde = eingabedatei_TEST.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedatei_TEST.length();
        }

        ausgabedateiname = ausgabedateiname.substring(0, indexEnde) + "out" + ausgabedateiname.substring(indexEnde);


        // zu Testzwecken:
        laufeProgrammMitEinemDatensatz(eingabeordner);
    }

    private void leseEin(){
        for (String eingabedateiname :
                offeneDateien) {

        }
    }

    /**
     * nur zu Testzwecken
     */
    private void laufeProgrammMitEinemDatensatz(String eingabedatei_TEST) throws IOException {
        String ausgabedateiname = eingabedatei_TEST.replace("input", "output");

        int indexEnde = eingabedatei_TEST.lastIndexOf('.');
        if (indexEnde == -1) {
            indexEnde = eingabedatei_TEST.length();
        }

        ausgabedateiname = ausgabedateiname.substring(0, indexEnde) + "out" + ausgabedateiname.substring(indexEnde);
        try {
            DateiReader reader = new DateiReader(eingabedatei_TEST);
            String gesamtInhalt = reader.lies();
            Datensatz d = IConverter.convertInputToDatensatz(gesamtInhalt, eingabedatei_TEST);

            Auswerter auswerter = new Auswerter(d);
            auswerter.werteDatensatzAus();

            String ausgabetext = OConverter.convertDatensatzToOutput(d);
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
