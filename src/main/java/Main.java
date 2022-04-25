import controller.Programm;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        String eingabedateiname = null;

        if (args.length == 1) {
            eingabedateiname = args[0];
        } else if (args.length == 0) {
            System.err.println("Eingabe/Ausgabe Fehler: keine Eingabedatei angegeben.\n Aufruf: java -jar Programm.java [eingabedatei]");
            System.exit(1);
        } else {
            System.err.println("Eingabe/Ausgabe Fehler: zu viele Parameter angegeben.\n Aufruf: java -jar Programm.java [eingabedatei]");
            System.exit(1);
        }

        if (eingabedateiname != null) {
            try {
                new Programm().starteProgramm(eingabedateiname);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            } catch (Exception e) {
                System.err.println("Technischer Fehler: " + e.getMessage());
                System.exit(1);
            }

        } else {
            System.err.println("Eingabe/Ausgabe Fehler: ungueltige Eingabedatei");
        }

    }
}
