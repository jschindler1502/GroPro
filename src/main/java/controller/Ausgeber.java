package controller;

import io.DateiWriter;
import io.IWriter;
import model.Datensatz;

import java.io.IOException;
import java.util.List;


public class Ausgeber implements Runnable {
    private List<Datensatz> verarbeiteteDatensaetze;
    final private List<String> geschlosseneDateien;
    private int anzDateien;
    private Datensatz datensatz; // TODO dummy

    public Ausgeber(List<Datensatz> verarbeiteteDatensaetze, List<String> geschlosseneDateien, int anzDateien) {
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
        this.geschlosseneDateien = geschlosseneDateien;
        this.anzDateien = anzDateien;

    }


    @Override
    public void run() {
        while (geschlosseneDateien.size() != anzDateien) {
            for (Datensatz datensatz :
                    verarbeiteteDatensaetze) {
                verarbeiteteDatensaetze.remove(datensatz);

                String ausgabetext = OConverter.convertDatensatzToOutput(datensatz);

                IWriter dateiWriter = new DateiWriter(datensatz.getName());
                try {
                    dateiWriter.schreibeAusgabe(ausgabetext);
                } catch (IOException e) {
                    e.printStackTrace();// TODO handling
                }

                geschlosseneDateien.add(datensatz.getName());
            }
        }
    }


}