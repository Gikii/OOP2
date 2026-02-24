package Project2.Organizmy.rosliny;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

import java.util.Random;

public class Roslina extends Organizm {
    public Roslina(int sila, int x, int y) {
        super( sila, 0, x, y);
    }

    @Override
    public void akcja() {
        boolean zasianie = new Random().nextInt(150) == 10; // ustawiamy prawdopodobienstwo zasiania
        if (zasianie) {
            Random rand = new Random();
            Pair<Integer, Integer>[] mozliwyRuch = swiat.getAllowedMoves();

            int move = rand.nextInt(mozliwyRuch.length);

            boolean miejsceNarodzin = false;
            Pair<Integer, Integer> newPosition = null; // Declare a new position

            for (int i = 0; i < mozliwyRuch.length; i++) {
                newPosition = addPolozenie(getPolozenie(), mozliwyRuch[move]);
                if (wykonajRuch(newPosition, true)) {
                    miejsceNarodzin = true;
                    swiat.dodajLog(this, "Rozsiewanie");
                    try {

                        swiat.dodajOrganizm(this.getClass().getCanonicalName(), newPosition);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                move = (move + 1) % mozliwyRuch.length;
            }
            if (!miejsceNarodzin) {
                swiat.dodajLog(this, "Nie ma miejsca na rozsianie");
                return;
            }
        }
    }

    @Override
    public void kolizja(Organizm drugiOrganizm) {
        String nazwa = drugiOrganizm.getClass().getSimpleName();

        if (getSila() > drugiOrganizm.getSila()) {
            swiat.dodajLog(this, "Zatrula " + nazwa);
            drugiOrganizm.zabij();
        } else if (getSila() < drugiOrganizm.getSila()) {
            swiat.dodajLog(this, "Zostala zdeptana przez " + nazwa);
            zabij();
        }
    }
}