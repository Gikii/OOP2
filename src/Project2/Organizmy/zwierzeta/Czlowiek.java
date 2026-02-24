package Project2.Organizmy.zwierzeta;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

public class Czlowiek extends Zwierze {
    private int umiejetnosc;
    private Pair<Integer, Integer> kierunek;



    public Czlowiek(Pair<Integer, Integer> polozenie) {
        super(5,4,polozenie.getKey(), polozenie.getValue());
        this.kierunek = new Pair<>(0, 0);
        this.umiejetnosc = -5;
    }

    @Override
    public void akcja() {
        ząbCzasu();
        Pair<Integer, Integer> nowaPozycja;
        Pair<Integer, Integer>[] sasiedzi = swiat.getSasiedzi();

        if (umiejetnosc > 0) {
            if (umiejetnosc == 5) {
                swiat.dodajLog(this, "Aktywowano umiejetnosc");
            }
            for (int i = 0; i < sasiedzi.length; i++) {
                nowaPozycja=addPolozenie(getPolozenie(), sasiedzi[i]);
                Organizm organizm = swiat.getOrganizm(nowaPozycja);
                if (organizm != null) {
                    organizm.zabij();
                }
            }
            swiat.dodajLog(this, "Zostalo " + umiejetnosc + " tur palenia");
        } else if (umiejetnosc < 0 && umiejetnosc != -5) {
            swiat.dodajLog(this, "Musi odczekac " + (umiejetnosc + 5) + " tury przed uzyciem umiejetnosci");
        }

        if (umiejetnosc > -5) {
            umiejetnosc--;
        }
        wykonajRuch(new Pair<>(getX() + kierunek.getKey(), getY() + kierunek.getValue()), false);
        kierunek = new Pair<>(0, 0);
    }

    public boolean ustawKierunek(Pair<Integer, Integer> kierunek) {
        if ((getX() + kierunek.getKey()) < 0 || (getX() + kierunek.getKey()) >= swiat.getSzerokosc() ||
                (getY() + kierunek.getValue()) < 0 || (getY() + kierunek.getValue()) >= swiat.getWysokosc()) {
            return false;
        }
        this.kierunek = kierunek;
        return true;
    }

    public boolean aktywujUmiejetnosc() {
        if (umiejetnosc == -5) {
            umiejetnosc = 5;
            return true;
        }else return  false;
    }

    @Override
    public String getInformacje() {
        String dane = super.getInformacje();
        dane += umiejetnosc + ";";
        return dane;
    }



}
