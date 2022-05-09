package controller;

import model.Datensatz;
import model.Messwert;

import java.util.ArrayList;

public class OConverter {

    private OConverter() {

    }

    /**
     * Erstellt Ausgabe je nach gewuenschtem Ausgabetyp
     *
     * @param datensatz der um die Auswertung ergaenzte Datensatz
     */
    public static String convertDatensatzToOutput(Datensatz datensatz) {
        StringBuilder sb = new StringBuilder();
        sb.append("# FWHM = ").append(datensatz.getFWHM()).append(", ").append(datensatz.getIndexOf(datensatz.getL())).append(", ").append(datensatz.getIndexOf(datensatz.getR())).append("\n");
        sb.append("# pos\tint\t\tenv\n");
        for (Messwert mw :
                datensatz.getMesswertList()) {
            sb.append(mw.getX()).append("\t").append(mw.getY()).append("\t\t").append(mw.getY_einhuellende()).append("\n");
        }
        return sb.toString();
    }

}
