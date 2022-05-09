/*
package controller;

import model.Messung;
import model.Result;

import java.util.HashMap;

public class Algorithm   {
    // TODO: Variablen
    private Messung messung;

    public Algorithm(Messung messung) {
        this.messung = messung;
    }

    @Override
    public Result solve() {
        algo1(messung.getxWerte(), messung.getyWerte());
        messung.setxGlatt(algo2(messung.getxWerte()));
        messung.setObereEinhuellende(algo3(messung.getxWerte(), messung.getyWerte()));
//        return new Result();
        return null;
    }

    private void algo1(double[] xWerte, double[] yWerte) {
        double yMax = Double.NEGATIVE_INFINITY;

        for (double value : yWerte) {
            yMax = Math.max(yMax, value);
        }

        for (int i = 0; i < xWerte.length; i++) {
            xWerte[i] = (xWerte[i] / (Math.pow(2, 18) - 1) * 266.3) - 132.3;
            yWerte[i] = yWerte[i] / yMax;
        }
    }

    private double[] algo2(double[] xWerte) {
        int anzahlMessungen = xWerte.length;

        int temp = (int) (0.002 * anzahlMessungen);
        int n = temp % 2 == 0 ? temp - 1 : temp;
        int tau = (n - 1) / 2;

        double[] xGlatt = new double[anzahlMessungen];
        for (int k = 0; k < anzahlMessungen; k++) {
            double sum = 0;
            for (int i = 0; i < anzahlMessungen; i++) {
                if (k < tau) {
                    sum += xWerte[tau];
                } else if (k > anzahlMessungen - 1 - tau) {
                    sum += xWerte[anzahlMessungen - 1 - tau];
                } else {
                    sum += xWerte[k - tau + i];
                }
            }
            xGlatt[k] = (1. / n) * sum;
        }
        return xGlatt;
    }

    private HashMap<Double, Double> algo3(double[] xWerte, double[] yWerte) {
        HashMap<Double, Double> obereEinhuellende = new HashMap<>();

        for (int i = 0; i < xWerte.length; i++) {
            if (!obereEinhuellende.containsKey(xWerte[i])) {
                obereEinhuellende.put(xWerte[i], yWerte[i]);
            } else {
                if (yWerte[i] > obereEinhuellende.get(xWerte[i])) {
                    obereEinhuellende.replace(xWerte[i], yWerte[i]);
                }
            }
        }
        return obereEinhuellende;
    }
}*/
