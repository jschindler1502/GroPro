package controller.Runnables;

import controller.Exceptions.AlgorithmusException;
import io.IReader;
import model.SharedString;
import io.DateiReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse zum nacheinander Einlesen beliebig vieler Eingabedateien im angegebenen Ordner in einem Thread
 */
public class Einleser implements Runnable {
    private final List<String> unverarbeiteteDateien;
    private final SharedString aktuellGeleseneDatei;
    private final SharedString aktuellGelesenerInhalt;

    public Einleser(List<String> unverarbeiteteDateien, SharedString aktuellGeleseneDatei, SharedString aktuellGelesenerInhalt) {
        this.unverarbeiteteDateien = unverarbeiteteDateien;
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
    }

    /**
     * Methode, die das Einlesen vornimmt:<br>
     * Solange noch zu lesende Dateien im Ordner existieren, lies diese ein, speichere sie für 0.05 Sekunden und fahre fort
     *
     * @throws RuntimeException, wenn der Thread unerwarteter Weise unterbrochen wurde
     */
    @Override
    public void run() throws RuntimeException {
        List<String> offenTemp = new ArrayList<>(unverarbeiteteDateien);

        while (offenTemp.size() != 0) {
            for (String eingabedateiname : offenTemp) {
                synchronized (aktuellGeleseneDatei) {
                    aktuellGeleseneDatei.setS(eingabedateiname);
                }
                IReader reader = new DateiReader(eingabedateiname);

                String inhalt;
                try {
                    inhalt = reader.lies();
                } catch (IOException e) {
                    throw new AlgorithmusException(e.getMessage());
                }
                synchronized (aktuellGelesenerInhalt) {
                    aktuellGelesenerInhalt.setS(inhalt);
                }
                synchronized (this) {
                    try {
                        wait(50);
                    } catch (InterruptedException e) {
                        throw new AlgorithmusException("Unerwarteter Fehler im Algorithmus beim Verarbeiten der Datei " + aktuellGeleseneDatei);
                    }
                }

            }
            offenTemp = new ArrayList<>(unverarbeiteteDateien);
        }
        synchronized (aktuellGeleseneDatei) {
            aktuellGeleseneDatei.setS(null);
        }
        synchronized (aktuellGelesenerInhalt) {
            aktuellGelesenerInhalt.setS(null);
        }
    }
}