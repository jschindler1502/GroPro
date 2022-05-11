package controller;

import controller.Exceptions.ValidierungsException;
import model.Datensatz;
import model.Messwert;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * statische Klasse zum Konvertieren von Zeichenkette zu {@link Datensatz} und zurueck
 */
public class IOConverter {

    /**
     * privater Konstruktor, damit es eine statische Klasse wird
     */
    private IOConverter() {

    }

    /**
     * statische Methode zur Konvertierung von Eingabeinhalt zu {@link Datensatz}
     *
     * @param gesamtInhalt die Zeichenkette, die aus dem Eingabemedium eingelesen wurde
     * @return das {@link Datensatz}, der aus dem Eingabeinhalt erstellt wird
     * @throws ValidierungsException im Falle von Syntax- oder Sematikfehlern
     */
    public static Datensatz convertInputToDatensatz(String gesamtInhalt, String eingabedateiname) throws ValidierungsException {
        ArrayList<Messwert> messwerte = new ArrayList<>();


        if (gesamtInhalt.length() == 0) {
            throw new ValidierungsException("Syntaxfehler in der Datei "+eingabedateiname+": Dokument darf nicht leer sein. ");
        }

        String[] aufgeteilterInhalt = gesamtInhalt.split("[\\r\\n]+");

        for (String s : aufgeteilterInhalt) {
            String zeile = s.trim();

            // Ueberspringe Kommentarzeilen
            if (!zeile.startsWith("#")) {
                String[] aufgeteilteZeile = zeile.split("[\\r\\n\\t]+");
                if (aufgeteilteZeile.length != 2) {
                    throw new ValidierungsException("Semantikfehler in der Datei "+eingabedateiname+": in einer Zeile muessen genau 2 natuerliche Zahlen stehen: y und x_schlange.");
                }
                if (!(istNatuerlicheZahl(aufgeteilteZeile[0]) && istNatuerlicheZahl(aufgeteilteZeile[1]))) {
                    throw new ValidierungsException("Semantikfehler in der Datei "+eingabedateiname+": y und x_schlange muessen als natuerliche Zahlen angegeben werden.");
                }
                int y = Integer.parseInt(aufgeteilteZeile[0]);
                int x_schlange = Integer.parseInt(aufgeteilteZeile[1]);
                messwerte.add(new Messwert(x_schlange, y));
            }
        }

        if (messwerte.size() == 0) {
            throw new ValidierungsException("Syntaxfehler in der Datei "+eingabedateiname+": es wurden keine Messwerte eingegeben.");
        }
        if (messwerte.size() < 100) {
            throw new ValidierungsException("Semantikfehler in der Datei "+eingabedateiname+": es wurden zu wenige Messwerte eingegeben. Eine Datei sollte mindestens 100 Messwerte beinhalten");
        }

        Messwert[] mwArr = new Messwert[messwerte.size()];
        mwArr = messwerte.toArray(mwArr);
        return new Datensatz(eingabedateiname, mwArr);
    }

    /**
     * Methode die Ausgabezeichenkette aus {@link Datensatz} erstellt
     *
     * @param datensatz der um die Auswertung ergaenzte {@link Datensatz}
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


    /* Hilfsmethoden zur Validierung */

    /**
     * statische Hilfsmethode, die zurueckgibt, ob die uebergebene Zeichenkette ein Element der natuerlichen Zahlen ist
     *
     * @param wert die zu pruefende Zeichenkette
     * @return true, falls der Inhalt von wert eine natuerliche Zahl ist
     */
    private static boolean istNatuerlicheZahl(String wert) {
        if (wert == null) {
            return false;
        }
        try {
            double doubleWert = Double.parseDouble(wert);
            if (doubleWert % 1 != 0) {
                throw new NumberFormatException();
            }
            int intWert = Integer.parseInt(wert);
            if (intWert <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
