package Project2.Organizmy;
import Project2.Swiat;
import javafx.util.Pair;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public abstract class Organizm implements Serializable {
    private int sila;
    private int inicjatywa;
    private int wiek;
    protected Pair<Integer, Integer> polozenie;
    private Pair<Integer, Integer> poprzedniePolozenie;
    private boolean doUsuniecia;
    protected Swiat swiat;


    public Organizm(int sila, int inicjatywa, int x, int y) {
        this.swiat = Swiat.getInstance();
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.polozenie = new Pair<>(x, y);
        this.wiek = 0;
        this.doUsuniecia = false;
    }


    public static Organizm spawn(Swiat swiat, String name, Pair<Integer, Integer> polozenie) {
        try{
            if(!name.contains("Project2.Organizmy"))
                name = "Project2.Organizmy." + name;
            return (Organizm) Class.forName(name).getConstructor(Pair.class).newInstance(polozenie);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getX() {
        return polozenie.getKey();
    }

    public int getY() {
        return polozenie.getValue();
    }
public Pair<Integer, Integer> getPolozenie(){
        return polozenie;
}
    public int getInicjatywa() {
        return inicjatywa;
    }
    protected Pair<Integer, Integer> addPolozenie(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        return new Pair<>(pos1.getKey() + pos2.getKey(), pos1.getValue() + pos2.getValue());
    }

    public int getWiek() {
        return wiek;
    }

    public int getSila() {
        return sila;
    }

    public boolean getDoUsuniecia() {
        return doUsuniecia;
    }
    public abstract void akcja();

    public String getInformacje() {
        return getClass().getSimpleName() + ";" + polozenie.getKey() + ";" + polozenie.getValue() + ";" +
                sila + ";" + wiek + ";";
    }


    public void ząbCzasu() {
        wiek++;
    }


    public void zabij() {
        polozenie = new Pair<>(-1, -1);
        sila = -1;
        inicjatywa = -1;
        doUsuniecia = true;
    }

    public void wzmocnij(int wartosc) {
        sila += wartosc;
    }

    public boolean czyUcieczka() {
        return false; // domyślnie organizm nie ucieka, wyjątkiem jest antylopa
    }


    public void cofnijRuch() {
        wykonajRuch(poprzedniePolozenie, false);
    }

    public boolean wykonajRuch(Pair<Integer, Integer> polozenie, boolean rozmnazanie) {
        if (polozenie.getKey() < 0 || polozenie.getKey() >= swiat.getSzerokosc() || polozenie.getValue() < 0 || polozenie.getValue() >= swiat.getWysokosc())
            return false;
        if (rozmnazanie && swiat.getOrganizm(polozenie) != null) {
            return false; // zapobiegniecie natychmiastowego rozmnożeniu się z rodzicem w tej samej turze
        }

        poprzedniePolozenie = this.polozenie;

        Organizm drugiOrganizm = swiat.getOrganizm(polozenie);
        this.polozenie = polozenie;
        if (drugiOrganizm != null && drugiOrganizm.getSila() > -1) {
            drugiOrganizm.kolizja(this);
        }
        return true;
    }

  public abstract void kolizja(Organizm organizm);


}
