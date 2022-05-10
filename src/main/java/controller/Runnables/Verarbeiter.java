package controller.Runnables;

import controller.Auswertung;
import controller.IOConverter;
import model.SharedString;
import model.Datensatz;

import java.util.List;

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


    public void run() {

        try {
            synchronized (this) {
                wait(50); // damit Einleser wenigstens die erste Datei gelesen hat
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage()); // unvorhergesehener Fehler TODO Fehlermeldung
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
                        throw new RuntimeException(e.getMessage()); // unvorhergesehener Fehler TODO Fehlermeldung
                    }
                }
                continue;
            }

            if (offeneDateien.contains(tempDateiname)) { // pruefe, dass ich die aktuellGeleseneDatei noch nicht verarbeitet
                System.out.println("Verarbeiter startet Verarbeitung von " + tempDateiname);
                offeneDateien.remove(tempDateiname);

                Datensatz datensatz = IOConverter.convertInputToDatensatz(tempDateiInhalt, tempDateiname);

                Auswertung alg = new Auswertung(datensatz);
                datensatz = alg.werteAus(); // TODO nicht return

                verarbeiteteDatensaetze.add(datensatz);
                System.out.println("Verarbeiter beendet Verarbeitung von " + tempDateiname);
            }
        }

    }


}
