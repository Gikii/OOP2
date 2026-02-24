package Project2.Organizmy.rosliny;

import javafx.util.Pair;

public class Mlecz extends Roslina{
    public Mlecz(Pair<Integer, Integer> polozenie) {
        super(0,polozenie.getKey(), polozenie.getValue());
    }

    @Override
    public void akcja(){
        super.akcja();
        super.akcja();
        super.akcja();
    }
}
