package controller;

import io.*;
import model.Datensatz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Programm zur Ausfuehrung der Signalauswertung
 */
public class SignalauswertungsProgramm {
    final private String eingabeordner;

    private List<String> offeneDateien ; // offen heisst, sie wurden noch nicht ausgelesen und dann konvertiert
    private List<Datensatz> verarbeiteteDatensaetze;
    private List<String> geschlosseneDateien ; // geschlossen heisst, sie wurden eingelesen, konvertiert, verarbeitet und ausgelesen

    private volatile String aktuellGeleseneDatei;
    private volatile String aktuellGelesenerInhalt;


    /**
     * @param eingabeordner Name des Ordners inklusive Pfad, in dem sich die Eingabedateien befinden
     */
    public SignalauswertungsProgramm(String eingabeordner) throws IOException {
        this.eingabeordner = eingabeordner;

        offeneDateien = Collections.synchronizedList(new ArrayList<>());
        verarbeiteteDatensaetze = Collections.synchronizedList(new ArrayList<>());
        geschlosseneDateien = Collections.synchronizedList(new ArrayList<>());

        File folder = new File(eingabeordner);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            throw new IOException("Technischer Fehler: es wurden keine Eingabedateien im Ordner /testfaelle/input hinterlegt.");
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                offeneDateien.add(file.getPath());
            }
        }

    }

    /**
     * Methode zum Starten des Programms mit allen Nebenlaeufigkeiten
     */
    public void starteProgramm() throws IOException {
        int anzDateien = offeneDateien.size();

        Einleser einleser = new Einleser(offeneDateien,aktuellGeleseneDatei,aktuellGelesenerInhalt);
        Thread einleseThread = new Thread(einleser);

        Verarbeiter verarbeiter = new Verarbeiter(aktuellGeleseneDatei,aktuellGelesenerInhalt,offeneDateien,verarbeiteteDatensaetze);
        Thread verarbeiterThread = new Thread(verarbeiter);

        Ausgeber ausgeber = new Ausgeber(verarbeiteteDatensaetze,geschlosseneDateien,anzDateien);
        Thread ausgeberThread = new Thread(ausgeber);
        try {
            // List<String> offeneDateien (noch nicht konverti)

            // t1
            // schreibt String geradeGelesenDateiname, String geradeGelesenInhalt
            einleseThread.start();

            //t2 nimmt das was geradeGelesen, entfernt aus offeneDatein
            verarbeiterThread.start();
            // schreibt in List<Datensatz> schonVerarbeitet

            //t3 nimmt aus schonVerarbeite, löscht da
            ausgeberThread.start();
            // fügt List<String> geschlosseneDateienNamen zu

            einleseThread.join();
            verarbeiterThread.join();
            ausgeberThread.join();
            // if(geschlosseneDateien.length == M) threads.join()
        } catch (ValidierungsException | AlgorithmusException e) { // in diesem Fall in Konsole und Datei schreiben
            IWriter dateiWriter = new DateiWriter(aktuellGeleseneDatei);
            IWriter konsolenWriter = new KonsoleWriter();
            dateiWriter.schreibeAusgabe(e.getMessage()); // falls hier Fehler, wird EingabeAusgabeException geworfen, in Main gefangen
            konsolenWriter.schreibeAusgabe(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
