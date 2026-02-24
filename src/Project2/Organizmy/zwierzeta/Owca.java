package Project2.Organizmy.zwierzeta;

import javafx.util.Pair;

public class Owca extends Zwierze{

public Owca(Pair<Integer, Integer> polozenie) {
    super(4,4,polozenie.getKey(), polozenie.getValue());
}
}
