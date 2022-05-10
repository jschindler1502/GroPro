package controller;

import model.Datensatz;
import model.Messwert;

import java.util.ArrayList;

public class Verarbeiter implements Runnable {
    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;
    private ArrayList<String> offeneDateien;
    private ArrayList<Datensatz> verarbeiteteDatensaetze;
    private Datensatz datensatz; // TODO dummy

    public Verarbeiter(String aktuellGeleseneDatei, String aktuellGelesenerInhalt, ArrayList<String> offeneDateien, ArrayList<Datensatz> verarbeiteteDatensaetze) {
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
        this.offeneDateien = offeneDateien;
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
    }


    public void run() {
        // TODO iterien?
        try {
            this.wait(50); // damit Einleser wenigstens die erste Datei gelesen hat
        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO
        }
        while (aktuellGeleseneDatei!= null){ // wenn null, ist Einleser fertig
            if (offeneDateien.contains(aktuellGeleseneDatei)) { // pruefe, dass ich die aktuellGeleseneDatei noch nicht verarbeitet

                offeneDateien.remove(aktuellGeleseneDatei);
                datensatz= IConverter.convertInputToDatensatz(aktuellGelesenerInhalt,aktuellGeleseneDatei); // dummy

                AuswertungAlgorithmus alg = new AuswertungAlgorithmus(datensatz);
                datensatz=alg.werteAus(); // TODO nicht return

                verarbeiteteDatensaetze.add(datensatz);

            }
        }

    }


}
