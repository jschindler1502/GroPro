package controller.Runnables;

import controller.Exceptions.AlgorithmusException;
import controller.IOConverter;
import io.DateiWriter;
import io.IWriter;
import model.Datensatz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse zum nacheinander Auslesen beliebig vieler verarbeiteter Datensaetze in einem Thread
 */
public class Ausgeber implements Runnable {
    private final List<Datensatz> verarbeiteteDatensaetze;
    final private List<String> geschlosseneDateien;
    private final int anzDateien;

    public Ausgeber(List<Datensatz> verarbeiteteDatensaetze, List<String> geschlosseneDateien, int anzDateien) {
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
        this.geschlosseneDateien = geschlosseneDateien;
        this.anzDateien = anzDateien;
    }

    /**
     * Methode, die das Ausgeben vornimmt:<br>
     * Solange noch nicht alle Dateien ausgegeben wurden, hole die verarbeiteten Datensaetze, konvertiere sie und gib sie aus<br>
     * @throws RuntimeException, wenn der Thread unerwarteter Weise unterbrochen wurde
     */
    @Override
    public void run() {
        while (geschlosseneDateien.size() != anzDateien) {
            List<Datensatz> verarbeiteteTemp = new ArrayList<>(verarbeiteteDatensaetze);
            for (Datensatz datensatz :
                    verarbeiteteTemp) {
                verarbeiteteDatensaetze.remove(datensatz);

                String ausgabetext = IOConverter.convertDatensatzToOutput(datensatz);

                IWriter dateiWriter = new DateiWriter(datensatz.getName());
                try {
                    dateiWriter.schreibeAusgabe(ausgabetext);
                } catch (IOException e) {
                    throw new AlgorithmusException("Unerwarteter Fehler im Algorithmus");
                }

                geschlosseneDateien.add(datensatz.getName());
            }
        }
    }


}
