package controller;

import model.Datensatz;
import java.util.List;

public class Verarbeiter implements Runnable {
    private SharedString aktuellGeleseneDatei;
    private SharedString aktuellGelesenerInhalt;
    private List<String> offeneDateien;
    private List<Datensatz> verarbeiteteDatensaetze;
    private Datensatz datensatz; // TODO dummy

    public Verarbeiter(SharedString aktuellGeleseneDatei, SharedString aktuellGelesenerInhalt, List<String> offeneDateien, List<Datensatz> verarbeiteteDatensaetze) {
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

        String datei,inhalt;

        while (offeneDateien.size() != 0){ // wenn 0, ist Einleser fertig

            synchronized (aktuellGeleseneDatei){
                datei = aktuellGeleseneDatei.getS();
            }
            synchronized (aktuellGelesenerInhalt){
                inhalt = aktuellGelesenerInhalt.getS();
            }

            if(datei == null || inhalt == null){
                synchronized (this){
                    try {
                        wait(10); // damit Einleser wenigstens die erste Datei gelesen hat

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                continue;
            }

            if (offeneDateien.contains(datei)) { // pruefe, dass ich die aktuellGeleseneDatei noch nicht verarbeitet
                System.out.println("Verarbeiter startet Verarbeitung von "+datei);
                offeneDateien.remove(datei);

                datensatz= IConverter.convertInputToDatensatz(inhalt,datei); // dummy

                AuswertungAlgorithmus alg = new AuswertungAlgorithmus(datensatz);
                datensatz=alg.werteAus(); // TODO nicht return

                verarbeiteteDatensaetze.add(datensatz);
                System.out.println("Verarbeiter beendet Verarbeitung von "+datei);
            }
        }

    }


}
