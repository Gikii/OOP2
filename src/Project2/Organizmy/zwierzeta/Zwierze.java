package Project2.Organizmy.zwierzeta;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

import java.util.Random;

public class Zwierze extends Organizm {
    public Zwierze(int sila, int inicjatywa, int x, int y) {
        super(sila, inicjatywa, x, y);
    }

    public void akcja() {
        ząbCzasu();
        Random rand = new Random();
        Pair<Integer, Integer>[] mozliwyRuch = swiat.getAllowedMoves();

        int move = rand.nextInt(mozliwyRuch.length);
        while (!wykonajRuch(addPolozenie(getPolozenie(), mozliwyRuch[move]), false)) {
            move++;
            move %= mozliwyRuch.length;
        }
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        if(this==drugiOrganizm){
            return;
        }
        if (this.getClass() == drugiOrganizm.getClass()) {
            if (getWiek() < 2 || drugiOrganizm.getWiek() < 2) { // cooldown na rozmnazanie
                return;
            }
            Random rand = new Random();
            Pair<Integer, Integer>[] mozliwyRuch = swiat.getAllowedMoves();
            int move = rand.nextInt(mozliwyRuch.length);
            boolean miejsceNarodzin = false;
            Pair<Integer, Integer> newPosition = null; // Declare a new position

            for (int i = 0; i < mozliwyRuch.length; i++) {
                newPosition = addPolozenie(getPolozenie(), mozliwyRuch[move]);

                if (wykonajRuch(newPosition, true)) {

                    miejsceNarodzin = true;
                    swiat.dodajLog(this, "Rozmnazanie");
                    break;
                }
                move++;
                move %= mozliwyRuch.length;
            }
            if (miejsceNarodzin) {
                swiat.dodajOrganizm(this.getClass().getCanonicalName(), newPosition);

            }else {
                swiat.dodajLog(this, "Nie ma miejsca na narodziny");
                return;
            }
        } else if (czyUcieczka() || drugiOrganizm.czyUcieczka()) {
            return;
        } else if (getSila() > drugiOrganizm.getSila()) {
            String nazwa = drugiOrganizm.getClass().getSimpleName();
            swiat.dodajLog(this, "Pokonal w walce " + nazwa);
            drugiOrganizm.zabij();
        } else if (getSila() < drugiOrganizm.getSila()) {
            String nazwa = drugiOrganizm.getClass().getSimpleName();
            swiat.dodajLog(this, "Przegral walke z " + nazwa);
            this.zabij();
        } else {
            String nazwa = drugiOrganizm.getClass().getSimpleName();
            swiat.dodajLog(this, "oraz " + nazwa + " umieraja w stoczonej bitwie");
            zabij();
            drugiOrganizm.zabij();
        }
    }
}

