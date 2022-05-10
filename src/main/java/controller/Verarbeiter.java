package controller;

import model.Datensatz;
import java.util.List;

public class Verarbeiter implements Runnable {
    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;
    private List<String> offeneDateien;
    private List<Datensatz> verarbeiteteDatensaetze;
    private Datensatz datensatz; // TODO dummy

    public Verarbeiter(String aktuellGeleseneDatei, String aktuellGelesenerInhalt, List<String> offeneDateien, List<Datensatz> verarbeiteteDatensaetze) {
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
        this.offeneDateien = offeneDateien;
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
    }


    public void run() {
        try {
            synchronized (this){

                wait(50); // damit Einleser wenigstens die erste Datei gelesen hat
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO handling
        }

        String datei;
        synchronized (this){
            datei = aktuellGeleseneDatei;
        }

        while (datei!= null){ // wenn null, ist Einleser fertig
            if (offeneDateien.contains(datei)) { // pruefe, dass ich die aktuellGeleseneDatei noch nicht verarbeitet

                offeneDateien.remove(datei);
                datensatz= IConverter.convertInputToDatensatz(aktuellGelesenerInhalt,datei); // dummy

                AuswertungAlgorithmus alg = new AuswertungAlgorithmus(datensatz);
                datensatz=alg.werteAus(); // TODO nicht return

                verarbeiteteDatensaetze.add(datensatz);

            }
            synchronized (this){
                datei = aktuellGeleseneDatei;
            }
        }

    }


}
