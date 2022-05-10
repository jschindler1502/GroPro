package io;

/**
 * Klasse zum Schreiben einer Zeichenkette in Konsole
 */
public class KonsoleWriter implements IWriter {

    public void schreibeAusgabe(String ausgabe) {
        System.out.println(ausgabe);
    }
}
