package controller;

import model.Datensatz;
import model.Messwert;

import java.util.ArrayList;

public class Auswerter implements Runnable {
    private String aktuellGeleseneDatei;
    private String aktuellGelesenerInhalt;
    private ArrayList<String> offeneDateien;
    private ArrayList<Datensatz> verarbeiteteDatensaetze;
    private Datensatz datensatz; // TODO dummy

    public Auswerter(String aktuellGeleseneDatei, String aktuellGelesenerInhalt, ArrayList<String> offeneDateien, ArrayList<Datensatz> verarbeiteteDatensaetze) {
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

                normiereUndRechneUm();

                glaette();

                berechneObereEinhuellende();

                berechneFWHM();
                verarbeiteteDatensaetze.add(datensatz);

            }
        }

    }

    private void normiereUndRechneUm() {
        double yMax = datensatz.getMax().getY();
        for (Messwert mw :
                datensatz.getMesswertList()) {
            mw.setX_hut((((double) mw.getX_schlange()) / (Math.pow(2., 18) - 1.)) * 266.3 - 132.3);
            mw.setY(mw.getY() / yMax);
        }
    }

    private void glaette() {
        int n = getKleinN();
        int tau = getTau(n);
        for (int k = 0; k < datensatz.getN(); k++) {
            Messwert mw = datensatz.getMesswertList()[k];
            double sum = 0.;
            if (k < tau) {
                int obergrenze = (n - Math.abs(k - tau));
                for (int i = 0; i < obergrenze; i++) {
                    sum += datensatz.getMesswertList()[i].getX_hut();
                }
                mw.setX(1. / (obergrenze) * sum);
            } else if (k > (datensatz.getN() - 1 - tau)) {
                int untergrenze = Math.abs(k - tau);
                for (int i = untergrenze; i < datensatz.getN(); i++) {
                    sum += datensatz.getMesswertList()[i].getX_hut();
                }
                mw.setX(1. / (datensatz.getN() - untergrenze) * sum);
            } else {
                for (int i = 0; i < n; i++) {
                    sum += datensatz.getMesswertList()[k - tau + i].getX_hut();
                }
                mw.setX((1. / n) * sum);
            }
        }
    }

    private void berechneObereEinhuellende() {
        int y_maxInd = datensatz.getIndexOf(datensatz.getMax());
        double y_maxTemp = datensatz.getMesswertList()[0].getY();

        // von links bis y_maxInd
        for (int k = 0; k < y_maxInd; k++) {
            Messwert mw = datensatz.getMesswertList()[k];
            if (mw.getY() > y_maxTemp) {
                y_maxTemp = mw.getY();
            }
            mw.setY_einhuellende(y_maxTemp);
        }

        // von rechts bis y_maxInd
        y_maxTemp = datensatz.getMesswertList()[datensatz.getN() - 1].getY();
        for (int k = datensatz.getN() - 1; k >= y_maxInd; k--) {
            Messwert mw = datensatz.getMesswertList()[k];
            if (mw.getY() > y_maxTemp) {
                y_maxTemp = mw.getY();
            }
            mw.setY_einhuellende(y_maxTemp);
        }
    }

    private void berechneFWHM() {
        double y_max = datensatz.getMax().getY(); // ist immer 1
        int y_maxInd = datensatz.getIndexOf(datensatz.getMax());
        double y_grundlinie = getGrundlinie();
        double y_halbeHoehe = (y_max + y_grundlinie) / 2.;

        // von y_maxInd bis R
        int k;
        for (k = y_maxInd + 1; datensatz.getMesswertList()[k].getY_einhuellende() >= y_halbeHoehe; k++) {
        }
        int indR = k;

        // von y_maxInd bis L
        for (k = y_maxInd - 1; datensatz.getMesswertList()[k].getY_einhuellende() >= y_halbeHoehe; k--) {
        }
        int indL = k;


        datensatz.setIndR(indR);
        datensatz.setIndL(indL);
        datensatz.setFWHM(datensatz.getMesswertList()[indR].getX() - datensatz.getMesswertList()[indL].getX());
    }


    private int getKleinN() {
        int temp = (int) (0.002 * datensatz.getN());
        return temp % 2 == 0 ? temp - 1 : temp;
    }

    private int getTau(int n) {
        return (n - 1) / 2;
    }

    private double getGrundlinie() {
        int n_grundInt = (int) (0.01 * datensatz.getN());
        if (n_grundInt == 0) {
            n_grundInt = 1;
        }
        double sum = 0;
        for (int k = 0; k < n_grundInt; k++) {
            sum += datensatz.getMesswertList()[k].getY_einhuellende(); // Annahme dass einhuellende y-Werte gemeint
        }
        return (1. / n_grundInt) * sum;
    }

}
