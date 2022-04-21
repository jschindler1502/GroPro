package io;


public class ConsoleWriter implements IWriter {

    public void schreibeAusgabe(String ausgabe) {
        System.out.println(ausgabe);
    }
}
