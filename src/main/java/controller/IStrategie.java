package controller;

import model.Ergebnis;

/**
 * Schnittstelle zur Abstrahierung des Algorithmus zur Antennenfindung
 */
public interface IStrategie {

    Ergebnis findeResult() throws AlgorithmusException;
}
