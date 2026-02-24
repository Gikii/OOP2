package Project2.Organizmy.zwierzeta;

import java.util.Random;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

public class Lis extends Zwierze {


    public Lis(Pair<Integer, Integer> polozenie) {
        super(3,7,polozenie.getKey(), polozenie.getValue());
    }
    @Override
    public void akcja() {
        Pair<Integer, Integer> nowaPozycja;
        Pair<Integer, Integer>[] mozliwyRuch = swiat.getAllowedMoves();

        Random rand = new Random();
        int move = rand.nextInt(mozliwyRuch.length);
        boolean wykonanoRuch = false;
        for (int i = 0; i < mozliwyRuch.length; i++) {
            nowaPozycja = addPolozenie(getPolozenie(), mozliwyRuch[move]);

            Organizm organizmNaPozycji = swiat.getOrganizm(nowaPozycja);
            if (organizmNaPozycji == null) {
                if (wykonajRuch(nowaPozycja, false)) {
                    wykonanoRuch = true;
                    break;
                }
            } else if (organizmNaPozycji.getSila() <= getSila()) {
                if (wykonajRuch(nowaPozycja, false)) {
                    wykonanoRuch = true;
                    break;
                }
            }

            move++;
            move %= mozliwyRuch.length;
        }
        if (!wykonanoRuch) {
            swiat.dodajLog(this, "Mowi: Jestem przebiegly i nie dam sie zjesc x))");
        }
    }
}
