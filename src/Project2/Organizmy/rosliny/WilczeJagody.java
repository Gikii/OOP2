package Project2.Organizmy.rosliny;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

public class WilczeJagody extends Roslina {
    public WilczeJagody(Pair<Integer, Integer> polozenie) {
        super(99,polozenie.getKey(), polozenie.getValue());
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        String nazwa = drugiOrganizm.getClass().getSimpleName();
        swiat.dodajLog(this, "Zatruly " + nazwa);
        drugiOrganizm.zabij();
        zabij();
    }
}