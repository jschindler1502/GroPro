import io.IReader;
import io.IWriter;
import io.SomeFileReader;
import io.SomeFileWriter;
import view.SomeView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        String inPath = null;
        String outPath = null;
        boolean logLvl = false;
        if (args.length != 0) {
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
        if(inPath!= null) {
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
            String[] pathParts = inPath.split("\\\\");
            String filename = pathParts[pathParts.length - 1].substring(0, pathParts[pathParts.length - 1].length() - 4);

            System.out.println(filename);
            // TODO eig IWriter, aber wegen .write
            SomeFileWriter writer = new SomeFileWriter(new File(outPath + filename + ".txt" ));
            try {
                String start = reader.readFileContent();
                writer.write(start);
                System.out.println("Successfully wrote to the file.");
            } catch (IOException ex) {
                writer.schreibeFehler(ex);
            }
        }else {
            System.out.println("schade");
        }
    }
}
