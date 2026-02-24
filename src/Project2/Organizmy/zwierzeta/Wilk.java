package Project2.Organizmy.zwierzeta;

import javafx.util.Pair;


public class Wilk extends Zwierze {
    public Wilk(Pair<Integer, Integer> polozenie) {
        super(9,5,polozenie.getKey(), polozenie.getValue());
    }
}
