package io;

import java.io.IOException;

public class ConsoleWriter implements IWriter {

    public void schreibeAusgabe(String ausgabe, String ausgabedateiname) throws IOException {
        System.out.println(ausgabe);
    }
}
