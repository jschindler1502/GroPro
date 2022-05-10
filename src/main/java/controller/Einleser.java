package controller;

import io.DateiReader;

import java.io.IOException;
import java.util.List;

public class Einleser implements Runnable {
    private List<String> offeneDateien;
    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;

    public Einleser(List<String> offeneDateien, String aktuellGeleseneDatei, String aktuellGelesenerInhalt) {
        this.offeneDateien = offeneDateien;
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
    }

    @Override
    public void run() {
        while (offeneDateien.size() != 0) {
            for (String eingabedateiname : offeneDateien) {
                synchronized (this){
                    this.aktuellGeleseneDatei = eingabedateiname;
                }
                DateiReader reader = new DateiReader(eingabedateiname);
                try {
                    String inhalt = reader.lies();
                    synchronized (this){
                        this.aktuellGelesenerInhalt = inhalt;
                    }
                    synchronized (this){
                        wait(50);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace(); // TODO exception handl
                }
            }
        }
        aktuellGeleseneDatei = null;
    }
}