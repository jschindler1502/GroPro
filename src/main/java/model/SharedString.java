package model;

/**
 * String-Wrapper, damit mehrere Threads zum Lesen oder Schreiben synchronisiert auf einen String zugreifen koennen
 */
public class SharedString {
    private String s;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
