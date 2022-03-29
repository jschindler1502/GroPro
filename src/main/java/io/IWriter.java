package io;

import view.IView;

/**
 * Interface zur Abstrahierung der Ausgabe
 */
public interface IWriter {

    /**
     * Schreibt die Ausgabe, welcher der View erzeugt
     * @param view , welcher die Ausgabe erzeugt
     */
    void schreibeAusgabe(IView view);

    /**
     * Schreibt eine aufgetretene Fehlermeldung
     * @param ex - die Fehlermeldung
     */
    void schreibeFehler(Exception ex);

}
