package Project2.Organizmy.rosliny;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

public class BarszczSosnowskiego extends Roslina {

    public BarszczSosnowskiego(Pair<Integer, Integer> polozenie) {
        super(10,polozenie.getKey(), polozenie.getValue());
    }

    @Override
    public void akcja() {
        Pair<Integer, Integer>[] sasiedzi = swiat.getSasiedzi();
        Pair<Integer, Integer> nowaPozycja;

        for (int i = 0; i < sasiedzi.length; i++) {
            nowaPozycja=addPolozenie(getPolozenie(), sasiedzi[i]);
            Organizm organizm = swiat.getOrganizm(nowaPozycja);
            if (organizm != null) {
                String nazwa = organizm.getClass().getSimpleName();
                organizm.zabij();
                swiat.dodajLog(this, "Zatrul " + nazwa);
            }

        }
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        String nazwa = drugiOrganizm.getClass().getSimpleName();
        swiat.dodajLog(this, "Zatrul " + nazwa);
        drugiOrganizm.zabij();
        zabij();
    }
}
