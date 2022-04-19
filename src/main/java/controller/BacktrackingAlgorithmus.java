package controller;

import model.Result;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Programm zur Ausfuehrung des Algorithmus zur Antennenfindung
 */
public class BacktrackingAlgorithmus implements IAlgorithmus {
    // TODO private Attr
    private Object best;
    private ArrayList<Object> allePunkte = new ArrayList<>();

    public BacktrackingAlgorithmus(Object modelObj) {
        // TODO Attr vorinitialisieren
    }

    /**
     * Methode zum //TODO
     *
     * @return Result inklusive //TODO
     */
    @Override
    public Result findeMinimaleAntennen() {
        for (Object punkt : allePunkte) {
            sucheRekursiv(punkt);
        }

        if (!Objects.equals(best, new Object())) {
            return new Result();// TODO mitgeben, was result ist
        } else {
            throw new RuntimeException("Der Algorithmus findet keine Loesung!");
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