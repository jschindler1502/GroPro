import io.IReader;
import io.IWriter;
import io.SomeFileReader;
import io.SomeFileWriter;
import view.SomeView;

import java.io.File;
import java.io.IOException;


// TODO https://www.ibm.com/docs/en/integration-bus/10.0?topic=java-adding-code-dependencies
public class Main {

    public void test(String a){
        System.out.println(a);
    }

    public static void main(String[] args) {
        String inPath = null;
        String outPath = null;
        boolean logLvl = false;
        if (args.length == 0) {
            for (String str: args) {
                if (str.startsWith("--input=")) {
                    inPath = str.substring(8);
                } else if (str.startsWith("--output=")) {
                    outPath = str.substring(9);
                } else if (str.startsWith("--logLvl")) {
                    logLvl = true;
                }
            }
        } else {
            System.err.println("Keine Eingabedatei angegeben");
            System.err.println("Aufruf: java -jar Project.java --input=[eingabedatei] --output=[ausgabeedatei]");
            System.exit(1);
        }

        File inputFile = new File(inPath);
        // Datei muss existieren
        if (!inputFile.exists()) {
            System.err.println("Eingabedatei existiert nicht");
            System.exit(1);
        }
        // Datei muss lesbar sein
        if (!inputFile.canRead()) {
            System.err.println("Eingabedatei ist nicht lesbar");
            System.exit(1);
        }

        IReader reader = new SomeFileReader(inputFile);
        IWriter writer = new SomeFileWriter(new File(outPath+"/output.txt"));
        try {
            Object start = reader.readFileContent();
            writer.schreibeAusgabe(new SomeView());
            System.out.println("Successfully wrote to the file.");
        } catch (IOException ex) {
            writer.schreibeFehler(ex);
        }
    }
}
