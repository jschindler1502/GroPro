package controller;

import model.Ergebnis;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Programm zur Ausfuehrung des Algorithmus zur Antennenfindung
 */
public class BacktrackingStrategie implements IStrategie {
    // TODO private Attr
    private Object best;
    private ArrayList<Object> allePunkte = new ArrayList<>();

    public BacktrackingStrategie(Object modelObj) {
        // TODO Attr vorinitialisieren
    }

    /**
     * Methode zum //TODO
     *
     * @return Result inklusive //TODO
     */
    @Override
    public Ergebnis findeResult() throws AlgorithmusException{
        for (Object punkt : allePunkte) {
            sucheRekursiv(punkt);
        }

        if (!Objects.equals(best, new Object())) {
            return new Ergebnis();// TODO mitgeben, was result ist
        } else {
            throw new AlgorithmusException("Fehler im Algorithmus: der Algorithmus findet keine Loesung!");
        }
    }

    /**
     * Rekursive Methode als Kernstueck des Backtracking-Algorithmus
     *
     */
    private void sucheRekursiv(Object punkt) {
        // Abbruchbedingungen -> return

        // falls Erfolg, setze best
        for (Object obj : allePunkte) {
            sucheRekursiv(obj);
        }
    }
}