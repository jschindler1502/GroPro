import controller.SignalauswertungsProgramm;

import java.io.IOException;

/**
 * Klasse nimmt als 1. Parameter den Ordner der Eingabedateien entgegen und startet das Signalauswertungsprogramm
 */
public class Main {

    public static void main(String[] args) {

        String eingabeordner = null;

        if (args.length == 1) {
            eingabeordner = args[0];
        } else if (args.length == 0) {
            System.err.println("Eingabe/Ausgabe Fehler: kein Eingabeordner angegeben.\n Aufruf: java -jar SignalauswertungsProgramm.jar [eingabeordner]");
            System.exit(1);
        } else {
            System.err.println("Eingabe/Ausgabe Fehler: zu viele Parameter angegeben.\n Aufruf: java -jar SignalauswertungsProgramm.jar [eingabeordner]");
            System.exit(1);
        }

        if (eingabeordner != null) {
            try {
                new SignalauswertungsProgramm(eingabeordner).starteProgramm();
            } catch (IOException e){
                System.err.println(e.getMessage());
            }

        } else {
            System.err.println("Eingabe/Ausgabe Fehler: ungueltiger Eingabeordner angegeben.\n Aufruf: java -jar SignalauswertungsProgramm.jar [eingabeordner]");
        }

    }
}
