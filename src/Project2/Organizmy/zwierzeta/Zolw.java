package Project2.Organizmy.zwierzeta;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

import java.util.Random;

public class Zolw extends Zwierze {
    public Zolw(Pair<Integer, Integer> polozenie) {
        super(2,1,polozenie.getKey(), polozenie.getValue());
    }

    @Override
    public void akcja() {
        boolean robiRuch = new Random().nextInt(100) >= 75;
        if (robiRuch) {
            super.akcja();
        } else {
            ząbCzasu();
        }
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        if (drugiOrganizm.getSila() < 5 && !(this.getClass().equals(drugiOrganizm.getClass()))) {
            String nazwa = drugiOrganizm.getClass().getSimpleName();
            swiat.dodajLog(this, "Odparl atak " + nazwa);
            drugiOrganizm.cofnijRuch();
        } else {
            super.kolizja(drugiOrganizm);
        }
    }
}