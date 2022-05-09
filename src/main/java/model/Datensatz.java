package model;

public class Datensatz {
    final private String name;
    private Messwert[] messwertList;
    final private int N;

    private Messwert max;
    private Messwert L;
    private Messwert R;
    private double FWHM;


    public Datensatz(String name, Messwert[] messwerte) {
        this.name = name;
        this.messwertList = messwerte;
        this.N = messwertList.length;
        this.setMax(findMax());
    }

    public Messwert[] getMesswertList() {
        return messwertList;
    }

    public Messwert getL() {
        return L;
    }

    public Messwert getR() {
        return R;
    }

    public double getFWHM() {
        return FWHM;
    }

    public int getIndexOf(Messwert mw) {
        for (int k = 0; k < N; k++) {
            if(mw.equals(messwertList[k])){
                return k;
            }
        }
        return -1;
    }
    private Messwert findMax() {
        Messwert max = messwertList[0];
        for (Messwert mw :
                messwertList) {
            if (mw.getY() > max.getY()) {
                max = mw;
            }
        }
        return max;
    }

    public void setMax(Messwert max) {
        this.max = max;
    }

    public void setL(Messwert l) {
        L = l;
    }

    public void setR(Messwert r) {
        R = r;
    }

    public void setFWHM(double FWHM) {
        this.FWHM = FWHM;
    }

    public String getName() {
        return name;
    }

    public int getN() {
        return N;
    }

    public Messwert getMax() {
        return max;
    }
}
