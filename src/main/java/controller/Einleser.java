package controller;

import io.DateiReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Einleser implements Runnable {
    private final List<String> offeneDateien;
    private final SharedString aktuellGeleseneDatei;
    private final SharedString aktuellGelesenerInhalt;

    public Einleser(List<String> offeneDateien, SharedString aktuellGeleseneDatei, SharedString aktuellGelesenerInhalt) {
        this.offeneDateien = offeneDateien;
        this.aktuellGeleseneDatei = aktuellGeleseneDatei;
        this.aktuellGelesenerInhalt = aktuellGelesenerInhalt;
    }

    @Override
    public void run() {
        List<String> offenTemp = new ArrayList<>(offeneDateien);

        while (offenTemp.size() != 0) {
            for (String eingabedateiname : offenTemp) {
                synchronized (aktuellGeleseneDatei) {
                    aktuellGeleseneDatei.setS(eingabedateiname);
                }
                DateiReader reader = new DateiReader(eingabedateiname);
                try {
                    System.out.println("Einleser liest " + eingabedateiname);
                    String inhalt = reader.lies();
                    synchronized (aktuellGelesenerInhalt) {
                        aktuellGelesenerInhalt.setS(inhalt);
                    }
                    synchronized (this) {
                        wait(50);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace(); // TODO exception handl
                }
            }
            offenTemp = new ArrayList<>(offeneDateien);
        }
        synchronized (aktuellGeleseneDatei) {
            aktuellGeleseneDatei.setS(null);
        }
    }
}