package model;


/**
 * Klasse für Resultinformationen im Fehlerfall
 */
public class FehlerResult implements IResult{
    private String message;


    public FehlerResult(String message) {
        this.message = message;
    }

}
