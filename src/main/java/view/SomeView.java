package view;

// TODO import models/controllers whatever

public class SomeView implements IView {

    @Override
    public String erzeugeAusgabe() {
        StringBuilder sb = new StringBuilder();

        sb.append("geschrieben");

        return sb.toString();
    }
}
