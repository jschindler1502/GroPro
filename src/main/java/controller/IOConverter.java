package controller;

import io.AusgabeTyp;
import model.Ergebnis;

public class IOConverter {

    private IOConverter() {

    }

    /**
     * statische Methode zur Konvertierung von EIngabeinhalt zu TODO Data Object
     *
     * @param gesamtInhalt die Zeichenkette, die aus dem EIngabemedium eingelesen wurde
     * @return das TODO Data Object, was aus dem Eingabeinhalt erstellt wird
     * @throws ValidierungsException im Falle von Syntax oder Sematikfehlern
     */
    public static Object convertInputToDataObject(String gesamtInhalt) throws ValidierungsException {


        StringBuilder beschreibung = new StringBuilder();
        // TODO Datenstrukturen vorinitalisieren


        if (gesamtInhalt.length() == 0) {
            throw new ValidierungsException("Syntaxfehler: Dokument darf nicht leer sein.");
        }

        String[] aufgeteilterInhalt = gesamtInhalt.split("[\\r\\n]+");

        for (int i = 0; i < aufgeteilterInhalt.length; i++) {
            String zeile = aufgeteilterInhalt[i].trim();

            // Behandlung von Kommentaren
            if (zeile.startsWith("//")) {

                // TODO Behandlung von Beschreibung
                if (zeile.charAt(2) == '+') {
                    String temp = aufgeteilterInhalt[i].substring(3).trim();
                    beschreibung.append(temp).append("\n"); // dadurch mehrere Zeilen Beschreibung moeglich
                } else {
                    // Ueberspringe Kommentarzeilen
                    continue;
                }
            } else {
                // TODO Behandlung von Inhaltszeile
            }

        }
        if (beschreibung.isEmpty()) {
            throw new ValidierungsException("Syntaxfehler: Beschreibung fehlt oder ist nicht korrekt als solche gekennzeichnet. Die Beschreibungszeile beginnt mit //+.");
        }
        // TODO Validieren, dass DS richtig gesetzt und nicht leer oder so


        return new Object();
    }

    /**
     * Erstellt Ausgabe je nach gewuenschtem Ausgabetyp
     *
     * @param ergebnis         Das Ergebnis des Algorithmus
     * @param typ              Ausgabetyp
     * @param ausgabedateiname Name der Ausgabedatei inklusive Pfad
     */
    public static String convertResultToOutput(Ergebnis ergebnis, AusgabeTyp typ, String ausgabedateiname) { // TODO evtl braucht man den ausgabedateinamen nicht
        // bastelt String aus Result
        StringBuilder sb = new StringBuilder();
        sb.append("Erfolg");
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
