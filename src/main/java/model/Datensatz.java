package model;

import java.util.ArrayList;
import java.util.List;

public class Datensatz {
    final private String name;


    private List<Messwert> messwertList;
    final private int N;
    final private Messwert max;
    private Messwert L;
    private Messwert R;
    private float FWHM;


    public Datensatz(String name, ArrayList<Messwert> messwerte) {
        this.name = name;
        this.messwertList = messwerte;
        this.N = messwertList.size();
        this.max = getMax();
    }

    public void werteDatensatzAus() {
        this.normiereUndRechneUm();
        this.glaette();
        this.berechneObereEinhuellende();
        this.berechneFWHM();
    }

    private void normiereUndRechneUm() {
        for (Messwert mw :
                messwertList) {
            mw.setX_hut((mw.getX_schlange() / (Math.pow(2, 18) - 1)) * 266.3 - 132.3);
            mw.setY(mw.getY() / this.max.getY());
        }
    }

    private void glaette() {
        int n = getKleinN();
        int tau = getTau(n);
        for (int k = 0; k < N; k++) {
            Messwert mw = messwertList.get(k);
            if (k <= tau || k > (N - 1 - tau)) {
                // TODO ist das richtig? k = k oder k = tau nehmen?
                mw.setX(mw.getX_hut());
            } else {
                double sum = 0;
                for (int i = 0; i < n; i++) {
                    sum += messwertList.get(k - tau + i).getX_hut();
                }
                mw.setX(1. / n * sum);
            }
        }
    }

    private void berechneObereEinhuellende() {
        int y_maxInd = messwertList.indexOf(this.max);
        double y_maxTemp = messwertList.get(0).getY();

        // von links bis y_maxInd
        for (int k = 0; k < y_maxInd; k++) {
            Messwert mw = messwertList.get(k);
            if (mw.getY() <= y_maxTemp) {
                mw.setY_einhuellende(y_maxTemp);
            } else {
                y_maxTemp = mw.getY(); // TODO istd as richtig?
                mw.setY_einhuellende(mw.getY());
            }
        }

        // von rechts bis y_maxInd
        for (int k = N - 1; k > y_maxInd; k--) {
            Messwert mw = messwertList.get(k);
            if (mw.getY() <= y_maxTemp) {
                mw.setY_einhuellende(y_maxTemp);
            } else {
                y_maxTemp = mw.getY(); // TODO istd as richtig?
                mw.setY_einhuellende(mw.getY());
            }
        }


    }

    private void berechneFWHM() {
        double y_max = this.max.getY();
        int y_maxInd = messwertList.indexOf(this.max);

        double y_grundlinie = this.getGrundlinie();
        double y_halbeDiff = (y_max - y_grundlinie) / 2.;

        // von y_maxInd bis R
        for (int k = y_maxInd + 1; k < N; k++) {
            Messwert mw = messwertList.get(k);
            if (mw.getY_einhuellende() <= y_halbeDiff) {
                this.R = mw;
            }
        }

        if (this.R == null) {
            // TODO
        }

        // von y_maxInd bis L
        for (int k = y_maxInd - 1; k >= 0; k--) {
            Messwert mw = messwertList.get(k);
            if (mw.getY_einhuellende() <= y_halbeDiff) {
                this.L = mw;
            }
        }
        if (this.L == null) {
            // TODO
        }

        System.out.println(L.getX_schlange());
        this.FWHM = (float) (R.getX() - L.getX());
    }

    private Messwert getMax() { // TODO evtl da mehrmals gebraucht speichern
        Messwert max = messwertList.get(0);
        for (Messwert mw :
                messwertList) {
            if (mw.getY() > max.getY()) {
                max = mw;
            }
        }
        return max;
    }

    private int getKleinN() { // TODO rundet implizit -> Minimalfall ?
        double n = 0.002 * N;
        if (n % 2 == 0) {
            n -= 1;
        }
        return (int) n;
    }

    private int getTau(int n) {
        return (n - 1) / 2;
    }

    private double getGrundlinie() {
        double n_grund = 0.01 * N;
        int n_grundInt = (int) n_grund;
        if ((n_grund % 1) != 0) {
            n_grundInt++;
        }

        double sum = 0;
        for (int k = 0; k < n_grundInt; k++) {
            sum += messwertList.get(k).getY_einhuellende(); // Annahme dass einhuellende y-Werte gemeint
        }
        return 1. / n_grundInt * sum;
    }

    public List<Messwert> getMesswertList() {
        return messwertList;
    }

    public Messwert getL() {
        return L;
    }

    public Messwert getR() {
        return R;
    }

    public float getFWHM() {
        return FWHM;
    }

    public int getIndexOf(Messwert mw) {
        return messwertList.indexOf(mw);
    }
}
