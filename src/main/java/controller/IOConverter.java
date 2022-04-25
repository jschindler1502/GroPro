package controller;

import io.AusgabeTyp;
import model.Ergebnis;

public class IOConverter {

    private IOConverter() {

    }

    /**
     * @param gesamtInhalt
     * @return das Data Object, was aus dem Eingabeinhalt erstellt wird
     * @throws ValidierungsException
     */
    public static Object convertInputToDataObject(String gesamtInhalt) throws ValidierungsException {


        StringBuilder beschreibung = new StringBuilder();
        // TODO Datenstrukturen vorinitalisieren


        if (gesamtInhalt.length() == 0) {
            throw new ValidierungsException("Syntaxfehler: Dokument darf nicht leer sein.");
        }

        String[] splittedContent = gesamtInhalt.split("[\\r\\n]+");

        // Beschreibung setzen
        for (int i = 0; i < 3; i++) {
            if (splittedContent[i].charAt(0) == ';') {
                String temp = splittedContent[i].substring(1).trim();
                beschreibung.append(temp);
                if (i != 2) {
                    beschreibung.append("\n");
                }
            } else {
                throw new ValidierungsException("Syntaxfehler: die ersten 3 Zeilen des Dokuments müssen Kommentare sein.");
            }

        }

        for (int i = 3; i < splittedContent.length; i++) {
            String line = splittedContent[i];
            // Ueberspringe Kommentarzeilen
            if (line.startsWith(";")) {
                continue;
            }
            // An Leerstellen trennen
            String[] values = line.split("[ ]+");
            // TODO Syntax und Semantikueberpruefung, sonst DS setzen
        }

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
}
