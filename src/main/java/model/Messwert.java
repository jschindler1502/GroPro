package model;

import java.util.Objects;

/**
 * Klasse zum Speichern aller Werte, die zu einem Messwert gehoeren
 */
public class Messwert {
    private final int x_schlange;
    private double x_hut;
    private double x;
    private double y; // zunaechst int, wird normiert und daher dann double
    private double y_einhuellende;

    public Messwert(int x_schlange, int y) {
        this.x_schlange = x_schlange;
        this.y = y;
    }

    public int getX_schlange() {
        return x_schlange;
    }


    public double getX_hut() {
        return x_hut;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX_hut(double x_hut) {
        this.x_hut = x_hut;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY_einhuellende() {
        return y_einhuellende;
    }

    public void setY_einhuellende(double y_einhuellende) {
        this.y_einhuellende = y_einhuellende;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Messwert messwert)) return false;
        return getX_schlange() == messwert.getX_schlange() && getY() == messwert.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX_schlange(),getY());
    }
}
