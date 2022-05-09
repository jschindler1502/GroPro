package controller;

import model.Datensatz;
import model.Messwert;

import java.util.ArrayList;
import java.util.Arrays;

public class IConverter {

    private IConverter() {

    }

    /**
     * statische Methode zur Konvertierung von EIngabeinhalt zu TODO Data Object
     *
     * @param gesamtInhalt die Zeichenkette, die aus dem EIngabemedium eingelesen wurde
     * @return das TODO Data Object, was aus dem Eingabeinhalt erstellt wird
     * @throws ValidierungsException im Falle von Syntax oder Sematikfehlern
     */
    public static Datensatz convertInputToDatensatz(String gesamtInhalt, String eingabedateiname) throws ValidierungsException {
        ArrayList<Messwert> messwerte = new ArrayList<>();


        if (gesamtInhalt.length() == 0) {
            throw new ValidierungsException("Syntaxfehler: Dokument darf nicht leer sein. ");
        }

        String[] aufgeteilterInhalt = gesamtInhalt.split("[\\r\\n]+");

        for (String s : aufgeteilterInhalt) {
            String zeile = s.trim();

            // Ueberspringe Kommentarzeilen
            if (zeile.startsWith("#")) {
                continue;
            } else {
                String[] aufgeteilteZeile = zeile.split("[\\r\\n\\t]+"); // TODO ist das richtig
                if(aufgeteilteZeile.length != 2){
                    System.out.println(Arrays.toString(aufgeteilteZeile));
                    throw new ValidierungsException("Semantikfehler: in einer Zeile muessen genau 2 natuerliche Zahlen stehen: y und x_schlange.");
                }
                if(!(istNatuerlicheZahl(aufgeteilteZeile[0]) || istNatuerlicheZahl(aufgeteilteZeile[1]))){
                    throw new ValidierungsException("Semantikfehler: y und x_schlange muessen als natuerliche Zahlen angegeben werden.");
                }
                int y = Integer.parseInt(aufgeteilteZeile[0]);
                int x_schlange = Integer.parseInt(aufgeteilteZeile[1]);
                messwerte.add(new Messwert(x_schlange,y));
            }
        }

        if(messwerte.size() == 0){
            throw new ValidierungsException("Syntaxfehler: es wurden keine Messwerte eingegeben.");
        }
        return new Datensatz(eingabedateiname, messwerte);
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
            // TODO ueberpruefen dass nicht gerundet wurde
            if (intWert <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
