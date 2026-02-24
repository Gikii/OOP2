package Project2;

import Project2.Organizmy.Organizm;
import Project2.Organizmy.zwierzeta.*;
import javafx.util.Pair;


import javax.swing.*;
import java.util.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.random;



public abstract class Swiat implements Serializable {
    int szerokosc;
    int wysokosc;
    int tura;

    static Swiat instance = null;
    ArrayList<Organizm> organizmy;
    Czlowiek czlowiek;

    protected static GamePanel gamePanel;

    static public Swiat getInstance(String name, int szerokosc, int wysokosc, GamePanel gamePanel) {
        switch (name) {
            case "Rectangle":
                instance = new RectangleWorld(szerokosc, wysokosc, gamePanel);
                break;
            case "Hexagon":
                instance = new HexagonWorld(szerokosc, wysokosc, gamePanel);
                break;
            default:
                instance = null;
                break;
        }
        return instance;
    }

    static public Swiat getInstance() {
        return instance;
    }

    public ArrayList<Organizm> getOrganizmy() {
        return organizmy;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public int getTura() {
        return tura;
    }


    public void zaludnijSwiat() {
        dodajOrganizm("zwierzeta.Czlowiek", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("zwierzeta.Owca", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("zwierzeta.Owca", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("zwierzeta.Wilk", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("zwierzeta.Wilk", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("zwierzeta.Lis", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("zwierzeta.Lis", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("zwierzeta.Zolw", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("zwierzeta.Zolw", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("rosliny.BarszczSosnowskiego", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("rosliny.Guarana", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("rosliny.Guarana", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("rosliny.Mlecz", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("rosliny.Mlecz", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("rosliny.Trawa", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("rosliny.Trawa", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));
        dodajOrganizm("rosliny.Trawa", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

        dodajOrganizm("rosliny.WilczeJagody", new Pair<>((int) (random() * szerokosc), (int) (random() * wysokosc)));

    }

    public void dodajLog(Organizm zrodlo, String log) {
        String nazwa = zrodlo.getClass().getSimpleName();
        nazwa = "[TURA " + instance.getTura() + "] " + nazwa + "(" + zrodlo.getX() + ", " + zrodlo.getY() + ") ";

        gamePanel.log(nazwa + log);


    }


    public abstract void rysuj(Pair<Integer, Integer> polozenie);

    public Organizm getOrganizm(Pair<Integer, Integer> polozenie) {
        return organizmy.stream().filter(o -> o.getPolozenie().equals(polozenie)).findFirst().orElse(null);
    }

    public void wykonajTure() {
        usunOrganizmy();
        tura++;

        organizmy = organizmy.stream().filter(o -> o.getSila() >= 0).collect(Collectors.toCollection(ArrayList::new));
        organizmy.sort(Comparator.comparingInt(Organizm::getSila).thenComparingInt(Organizm::getWiek));

        int size = organizmy.size();
        for (int i = 0; i < size; i++) {
            if (organizmy.get(i).getSila() >= 0)
                organizmy.get(i).akcja();
        }
        for (int i = 0; i < szerokosc; i++)
            for (int j = 0; j < wysokosc; j++)
                rysuj(new Pair<>(i, j));
    }


    public abstract Pair<Integer, Integer>[]  getAllowedMoves();

    public abstract Pair<Integer, Integer>[]  getSasiedzi();



    public void usunOrganizmy() {
        List<Organizm> organizmyDoUsuniecia = new ArrayList<>();

        Iterator<Organizm> it = organizmy.iterator();
        while (it.hasNext()) {
            Organizm o = it.next();
            if (o != null && o.getDoUsuniecia()) {
                organizmyDoUsuniecia.add(o);
                it.remove();
            }
        }
        for (Organizm o : organizmyDoUsuniecia) {
            if (o instanceof Czlowiek) {
                czlowiek = null;
            }
            o = null;
        }
    }

public void dodajOrganizm(String name, Pair<Integer, Integer> polozenie) {
    Organizm o = Organizm.spawn(this, name, polozenie);
    organizmy.add(o);
}
    public void dodajOrganizm(Organizm organizm) {
        organizmy.add(organizm);
    }


    public Organizm getCzlowiek() {
        return (Czlowiek) organizmy.stream()
                .filter(organizm -> organizm instanceof Czlowiek)
                .findFirst()
                .orElse(null);
   }

    public void setCzlowiek(Czlowiek czlowiek) {
        organizmy = (ArrayList<Organizm>) organizmy.stream()
                .filter(organizm -> !(organizm instanceof Czlowiek))
                .collect(Collectors.toList());
        organizmy.add(czlowiek);
    }

    public void zapisz() {

        JFileChooser fileChooser = new JFileChooser();

        int response = fileChooser.showSaveDialog(null);
        if (response != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Nie wybrano pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
            oos.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się zapisać gry: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }
        JOptionPane.showMessageDialog(null, "Pomyślnie zapisano grę", "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    public void wczytaj() {


        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        if (response != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null, "Nie wybrano pliku", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = fileChooser.getSelectedFile();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (!(obj instanceof Swiat)) {
                throw new ClassNotFoundException("Invalid object type in file.");
            }


            Swiat loadedSwiat = (Swiat) obj;

            if (obj instanceof RectangleWorld) {
                instance= new RectangleWorld(((Swiat) obj).getSzerokosc(), ((Swiat) obj).getWysokosc(), gamePanel);
            } else if (obj instanceof HexagonWorld) {
                instance= new HexagonWorld(((Swiat) obj).getSzerokosc(), ((Swiat) obj).getWysokosc(), gamePanel);
            }

            instance.organizmy = loadedSwiat.getOrganizmy();
            instance.tura = loadedSwiat.getTura();


            szerokosc = instance.getSzerokosc();
            wysokosc = instance.getWysokosc();

            czlowiek= (Czlowiek) instance.getCzlowiek();

            gamePanel.setSwiat(instance);

            instance.initGUI();

            gamePanel.log("Wczytano grę");

            for (Organizm organizm : instance.organizmy) {
                Pair<Integer, Integer> polozenie = organizm.getPolozenie();
                instance.rysuj(polozenie);
            }



            JOptionPane.showMessageDialog(null, "Pomyślnie wczytano grę", "Sukces", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Nie udało się wczytać gry: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Nieprawidłowy format pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Błąd wczytywania danych: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    protected abstract void initGUI();

}
