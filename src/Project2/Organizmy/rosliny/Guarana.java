package Project2.Organizmy.rosliny;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

public class Guarana extends Roslina {

    public Guarana(Pair<Integer, Integer> polozenie) {
        super(0,polozenie.getKey(), polozenie.getValue());
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        String nazwa = drugiOrganizm.getClass().getSimpleName();
        drugiOrganizm.wzmocnij(3);
        swiat.dodajLog(this, "Wzmocnila " + nazwa + " i jego sila wynosi " + drugiOrganizm.getSila());
        zabij();
    }
}