package controller;

import io.DateiReader;

import java.io.IOException;
import java.util.ArrayList;

public class Einleser implements Runnable {
    private ArrayList<String> offeneDateien;
    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;

    public Einleser(ArrayList<String> offeneDateien, String aktuellGeleseneDatei, String aktuellGelesenerInhalt) {
        this.offeneDateien = offeneDateien;
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;

    }

    @Override
    public void run() {
        while (offeneDateien.size() != 0) {
            for (String eingabedateiname : offeneDateien) {
                this.aktuellGeleseneDatei = eingabedateiname;
                DateiReader reader = new DateiReader(eingabedateiname);
                try {
                    this.aktuellGelesenerInhalt = reader.lies();
                    this.wait(50);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace(); // TODO exception handl
                }
            }
        }
        aktuellGeleseneDatei = null;
    }
}