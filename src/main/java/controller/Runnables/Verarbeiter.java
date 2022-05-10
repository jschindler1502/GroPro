package controller.Runnables;

import controller.Auswertung;
import controller.Exceptions.AlgorithmusException;
import controller.IOConverter;
import model.SharedString;
import model.Datensatz;

import java.util.List;

/**
 * Klasse zum Verarbeiten des Inhalts einer Eingabedatei in einem Thread<br>
 * konvertiert den Inhalt in ein Objekt der Klasse {@link Datensatz} und wertet es aus
 */
public class Verarbeiter implements Runnable {
    private final SharedString aktuellGeleseneDatei;
    private final SharedString aktuellGelesenerInhalt;
    private final List<String> offeneDateien;
    private final List<Datensatz> verarbeiteteDatensaetze;

    public Verarbeiter(SharedString aktuellGeleseneDatei, SharedString aktuellGelesenerInhalt, List<String> offeneDateien, List<Datensatz> verarbeiteteDatensaetze) {
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
        this.offeneDateien = offeneDateien;
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
    }

    /**
     * Methode, die das Verarbeiten vornimmt:<br>
     * Solange noch zu lesende Dateien im Ordner existieren oder der Einleser einen Inhalt zur Verfuegung stellt,<br>
     * konvertiere diesen und werte ihn aus
     * @throws RuntimeException, wenn der Thread unerwarteter Weise unterbrochen wurde
     */
    @Override
    public void run() throws RuntimeException {

        try {
            synchronized (this) {
                wait(50); // damit Einleser wenigstens die erste Datei gelesen hat
            }
        } catch (InterruptedException e) {
            throw new AlgorithmusException("Unerwarteter Fehler im Algorithmus");
        }

        String tempDateiname, tempDateiInhalt;

        while (offeneDateien.size() != 0) { // wenn 0, ist Einleser fertig

            synchronized (aktuellGeleseneDatei) {
                tempDateiname = aktuellGeleseneDatei.getS();
            }
            synchronized (aktuellGelesenerInhalt) {
                tempDateiInhalt = aktuellGelesenerInhalt.getS();
            }

            if (tempDateiname == null || tempDateiInhalt == null) {
                synchronized (this) {
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                        throw new AlgorithmusException("Unerwarteter Fehler im Algorithmus");
                    }
                }
                continue;
            }

            if (offeneDateien.contains(tempDateiname)) { // pruefe, dass aktuellGeleseneDatei noch nicht verarbeitet
                offeneDateien.remove(tempDateiname);

                Datensatz datensatz = IOConverter.convertInputToDatensatz(tempDateiInhalt, tempDateiname);

                Auswertung auswertung = new Auswertung(datensatz);
                auswertung.werteAus();

                verarbeiteteDatensaetze.add(auswertung.getDatensatz());
            }
        }

    }


}
