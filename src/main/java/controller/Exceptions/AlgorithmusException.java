package controller.Exceptions;

/**
 * Exception Klasse fuer unvorhergesehene Fehler waehrend des Algorithmus
 */
public class AlgorithmusException extends RuntimeException{
    public AlgorithmusException(String message) {
        super(message);
    }
}
