package controller;


import controller.Exceptions.AlgorithmusException;
import controller.Exceptions.ValidierungsException;
import io.*;
import model.Datensatz;
import model.SharedString;

import controller.Runnables.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// unvorhergesehener Fehler TODO Fehlermeldung


/**
 * Programm zur Ausfuehrung der Signalauswertung, dass drei Nebenlaeufigkeiten ({@link controller.Runnables}) erstellt<br>
 * 1. Thread fuer das Einlesen der Eingabedateien, bis alle verarbeitet wurden<br>
 * 2. Thread fuer das Verarbetiten, also Konvertieren und Auswerten der Eingabedateien, bis alle verarbeitet wurden<br>
 * 3. Thread fuer das Ausgeben, also Konvertieren und Ausgeben der Datensaetze, bis alle verarbeitet wurden<br>
 */
public class SignalauswertungsProgramm {

    private final List<String> offeneDateien = Collections.synchronizedList(new ArrayList<>()); // offen heisst, sie wurden noch nicht ausgelesen und dann verarbeitet
    private final List<Datensatz> verarbeiteteDatensaetze = Collections.synchronizedList(new ArrayList<>()); // verarbeitet heisst, sie wurden konvertiert und ausgewertet
    private final List<String> geschlosseneDateien = Collections.synchronizedList(new ArrayList<>());// geschlossen heisst, sie wurden eingelesen, konvertiert, verarbeitet und ausgelesen

    private final SharedString aktuellGeleseneDatei = new SharedString();
    private final SharedString aktuellGelesenerInhalt = new SharedString();


    /**
     * Konstruktor der Klasse, der die zu lesenden Dateien in offeneDateien speichert
     *
     * @param eingabeordner Name des Ordners inklusive Pfad, in dem sich die Eingabedateien befinden
     */
    public SignalauswertungsProgramm(String eingabeordner) throws IOException {
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
     * Methode zum Starten des Programms mit allen 3 Nebenlaeufigkeiten
     * Diese fuehren die Bearbeitungsschritte parallel durch, bis alle Datensaetze verarbeitet wurden
     */
    public void starteProgramm() {
        long startTime = System.nanoTime();

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


        long endTime = System.nanoTime();

        System.out.println("Das Programm hat " + (endTime - startTime) / 1000000000. + " Sekunden zum Verarbeiten der " + anzDateien + " Dateien gebraucht.");
    }

    /**
     * Methode zum Exceptionhandling:<br>
     * Schreibt Fehlermeldung in Ausgabedatei und Konsole oder nur die Konsole
     *
     * @param e die gefangene Exception: {@link IOException}, {@link controller.Exceptions.AlgorithmusException} oder {@link controller.Exceptions.ValidierungsException}
     */
    private void handleException(Throwable e) {
        String message = "Unerwarteter Fehler im Algorithmus";
        if (e instanceof ValidierungsException || e instanceof AlgorithmusException) {
            message = e.getMessage();
        }
        IWriter dateiWriter = new DateiWriter(aktuellGeleseneDatei.getS());
        IWriter konsolenWriter = new KonsoleWriter();
        try {
            dateiWriter.schreibeAusgabe(message);
            konsolenWriter.schreibeAusgabe(message);
        } catch (IOException ex) {
            System.err.println(message);
        }
        System.exit(0);
    }
}
