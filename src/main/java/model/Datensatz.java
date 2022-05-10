package model;

public class Datensatz {
    final private String name;
    private Messwert[] messwertList;
    final private int N;

    private Messwert max;
    private int indL;
    private int indR;
    private double FWHM;


    public Datensatz(String name, Messwert[] messwerte) {
        this.name = name;
        this.messwertList = messwerte;
        this.N = messwertList.length;
        this.max = findMax();
    }

    public Messwert[] getMesswertList() {
        return messwertList;
    }

    public double getFWHM() {
        return FWHM;
    }

    public int getIndexOf(Messwert mw) { // TODO performanz
        for (int k = 0; k < N; k++) {
            if (mw.equals(messwertList[k])) {
                return k;
            }
        }
        System.out.println("nicht gefunden");
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

    public Messwert getMax() {
        return max;
    }

    public int getIndL() {
        return indL;
    }

    public int getIndR() {
        return indR;
    }
}
