package Project2.Organizmy.zwierzeta;

import javafx.util.Pair;

import java.util.Random;

public class Antylopa extends Zwierze {


    public Antylopa(Pair<Integer, Integer> polozenie) {
        super(4,4,polozenie.getKey(), polozenie.getValue());
    }
    @Override
    public void akcja() {
        super.akcja();
        super.akcja();
    }

    @Override
    public boolean czyUcieczka() {
        Random rand = new Random();
        boolean ucieczka = rand.nextBoolean();
        if (ucieczka) {
            Pair<Integer, Integer>[] mozliwyRuch = swiat.getAllowedMoves();
            int move = 0;
            while (!wykonajRuch(addPolozenie(getPolozenie(), mozliwyRuch[move]), true)) {
                move++;
                if (move == mozliwyRuch.length) {
                    swiat.dodajLog(this, "Nie udalo się uciec");
                    return false;
                }
            }
            swiat.dodajLog(this, "Udalo sie uciec");
            return true;
        }
        return false;
    }
}