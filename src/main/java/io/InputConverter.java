package io;

import java.io.IOException;


/**
 * Klasse zum Konvertieren des Inhalts der Eingabedatei in ein Feld
 * statisch, da kein Objekt der Klasse zum Konvertieren benoetigt wird
 */
public class InputConverter implements IInputConverter{
    /**
     *
     * @param gesamtInhalt
     * @return das Feld, was aus dem Eingabeinhalt erstellt wird
     * @throws IOException
     */
    public static  Object convertInputToFeld(String gesamtInhalt) throws IOException {


        StringBuilder beschreibung = new StringBuilder();
        // TODO Datenstrukturen vorinitalisieren


        if (gesamtInhalt.length() == 0) {
            throw new InvalidSyntaxException("Syntaxfehler! Dokument darf nicht leer sein.");
        }

        String[] splittedContent = gesamtInhalt.split("[\\r\\n]+");

        // Beschreibung setzen
        for (int i = 0; i < 3; i++) {
            if(splittedContent[i].charAt(0) == ';'){
                String temp = splittedContent[i].substring(1).trim();
                beschreibung.append(temp);
                if (i != 2) {
                    beschreibung.append("\n");
                }
            } else {
                throw new InvalidSyntaxException("Syntaxfehler! Die ersten 3 Zeilen des Dokuments müssen Kommentare sein.");
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
}
