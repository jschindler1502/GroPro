package model;

/**
 * Klasse zum Speichern aller Messwerte zu einem Datensatz und den berechneten Werten der Auswertung
 */
public class Datensatz {
    final private String name;
    private final Messwert[] messwertList;
    final private int N;

    private final int maxIndex;
    private int indL;
    private int indR;
    private double FWHM;


    public Datensatz(String name, Messwert[] messwerte) {
        this.name = name;
        this.messwertList = messwerte;
        this.N = messwertList.length;
        this.maxIndex = findMaxInd();
    }

    public Messwert[] getMesswertList() {
        return messwertList;
    }

    public double getFWHM() {
        return FWHM;
    }

    private int findMaxInd() {
        Messwert max = messwertList[0];
        int maxInd = 0;
        for (int k = 0; k < N; k++) {
            Messwert mw = messwertList[k];
            if (mw.getY() > max.getY()) {
                max = mw;
                maxInd = k;
            }
        }
        return maxInd;
    }


    public void setIndL(int l) {
        indL = l;
    }

    public void setIndR(int r) {
        indR = r;
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

    public int getMaxIndex() {
        return maxIndex;
    }

    public int getIndL() {
        return indL;
    }

    public int getIndR() {
        return indR;
    }
}
