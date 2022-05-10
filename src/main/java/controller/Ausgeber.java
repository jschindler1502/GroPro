package controller;

import io.DateiWriter;
import io.IWriter;
import model.Datensatz;

import java.io.IOException;
import java.util.ArrayList;


public class Ausgeber implements Runnable {
    private ArrayList<Datensatz> verarbeiteteDatensaetze;
    final private ArrayList<String> geschlosseneDateien;
    private int anzDateien;
    private Datensatz datensatz; // TODO dummy

    public Ausgeber(ArrayList<Datensatz> verarbeiteteDatensaetze, ArrayList<String> geschlosseneDateien, int anzDateien) {
        this.verarbeiteteDatensaetze = verarbeiteteDatensaetze;
        this.geschlosseneDateien = geschlosseneDateien;
        this.anzDateien = anzDateien;

    }


    @Override
    public void run() {
        while (geschlosseneDateien.size() != anzDateien) {

            this.datensatz = verarbeiteteDatensaetze.get(0); // TODO dummy
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
