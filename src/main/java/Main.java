import controller.Programm;
import io.TechnischeException;


public class Main {

    public static void main(String[] args) {

        String eingabedateiname = null;

        if (args.length == 1) {
            eingabedateiname = args[0];
        } else if (args.length == 0) {
            System.err.println(new TechnischeException("Technischer Fehler! Keine Eingabedatei angegeben.\n Aufruf: java -jar Programm.java [eingabedatei]").getMessage());
            System.exit(1);
        } else {
            System.err.println(new TechnischeException("Technischer Fehler! Zu viele Parameter angegeben.\n Aufruf: java -jar Programm.java [eingabedatei]").getMessage());
            System.exit(1);
        }

        if (eingabedateiname != null) {
            new Programm().starteProgramm(eingabedateiname);
        } else {
            System.err.println(new TechnischeException("Technischer Fehler! Eingabedatei ist nicht lesbar").getMessage());
        }

    }
}
