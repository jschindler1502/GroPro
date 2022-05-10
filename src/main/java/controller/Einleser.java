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
    public void run() throws RuntimeException {
        List<String> offenTemp = new ArrayList<>(offeneDateien);

        while (offenTemp.size() != 0) {
            for (String eingabedateiname : offenTemp) {
                synchronized (aktuellGeleseneDatei) {
                    aktuellGeleseneDatei.setS(eingabedateiname);
                }
                DateiReader reader = new DateiReader(eingabedateiname);

                System.out.println("Einleser liest " + eingabedateiname);
                String inhalt = null;
                try {
                    inhalt = reader.lies();
                } catch (IOException e) { // wandle IOException in AlgorithmusException um
                    throw new AlgorithmusException(e.getMessage());
                }
                synchronized (aktuellGelesenerInhalt) {
                    aktuellGelesenerInhalt.setS(inhalt);
                }
                synchronized (this) {
                    try {
                        wait(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e.getMessage()); // unvorhergesehener Fehler TODO Fehlermeldung
                    }
                }

            }
            offenTemp = new ArrayList<>(offeneDateien);
        }
        synchronized (aktuellGeleseneDatei) {
            aktuellGeleseneDatei.setS(null);
        }
    }
}