package controller;


import io.*;
import model.Datensatz;
import model.SharedString;

import controller.Runnables.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Programm zur Ausfuehrung der Signalauswertung
 */
public class SignalauswertungsProgramm {
    private final List<String> offeneDateien; // offen heisst, sie wurden noch nicht ausgelesen und dann konvertiert
    private final List<Datensatz> verarbeiteteDatensaetze;
    private final List<String> geschlosseneDateien; // geschlossen heisst, sie wurden eingelesen, konvertiert, verarbeitet und ausgelesen

    private final SharedString aktuellGeleseneDatei = new SharedString();
    private final SharedString aktuellGelesenerInhalt = new SharedString();


    /**
     * @param eingabeordner Name des Ordners inklusive Pfad, in dem sich die Eingabedateien befinden
     */
    public SignalauswertungsProgramm(String eingabeordner) throws IOException {

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
    public void starteProgramm() {
        int anzDateien = offeneDateien.size();

        Thread.UncaughtExceptionHandler handler = (t, e) -> handleException(e);

        Einleser einleser = new Einleser(offeneDateien, aktuellGeleseneDatei, aktuellGelesenerInhalt);
        Thread einleseThread = new Thread(einleser);
        einleseThread.setUncaughtExceptionHandler(handler);


        Verarbeiter verarbeiter = new Verarbeiter(aktuellGeleseneDatei, aktuellGelesenerInhalt, offeneDateien, verarbeiteteDatensaetze);
        Thread verarbeiterThread = new Thread(verarbeiter);
        verarbeiterThread.setUncaughtExceptionHandler(handler);

        Ausgeber ausgeber = new Ausgeber(verarbeiteteDatensaetze, geschlosseneDateien, anzDateien);
        Thread ausgeberThread = new Thread(ausgeber);
        ausgeberThread.setUncaughtExceptionHandler(handler);

        einleseThread.start();
        verarbeiterThread.start();
        ausgeberThread.start();

        try {
            einleseThread.join();
            verarbeiterThread.join();
            ausgeberThread.join();
        } catch (InterruptedException e) {
            handleException(e);
        }
    }

    private void handleException(Throwable e) {
        try {
            IWriter dateiWriter = new DateiWriter(aktuellGeleseneDatei.getS());
            IWriter konsolenWriter = new KonsoleWriter();
            dateiWriter.schreibeAusgabe(e.getMessage()); // falls hier Fehler, wird EingabeAusgabeException geworfen, in Main gefangen
            konsolenWriter.schreibeAusgabe(e.getMessage());
        } catch (IOException ex) {
            System.err.println(e.getMessage());
        }
        System.exit(0);
    }
}
