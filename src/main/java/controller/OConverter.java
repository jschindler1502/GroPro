package controller;

import model.Datensatz;
import model.Messwert;


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
        sb.append("# FWHM = ").append((float) datensatz.getFWHM()).append(", ").append(datensatz.getIndL()).append(", ").append(datensatz.getIndR()).append("\n");
        sb.append("# pos\tint\t\tenv\n");
        for (Messwert mw :
                datensatz.getMesswertList()) {
            sb.append((float) mw.getX()).append("\t").append((float) mw.getY()).append("\t\t").append((float) mw.getY_einhuellende()).append("\n");
        }
        return sb.toString();
    }

}
