package io;


public class KonsoleWriter implements IWriter {

    public void schreibeAusgabe(String ausgabe) {
        System.out.println(ausgabe);
    }
}
