package Project2;

import Project2.Organizmy.Organizm;
import javafx.util.Pair;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class RectangleWorld extends Swiat implements Serializable {
    final Pair<Integer, Integer>[] directions = new Pair[]{
            new Pair<>(0, -1),
            new Pair<>(0, 1),
            new Pair<>(-1, 0),
            new Pair<>(1, 0)

    };


    final Pair<Integer, Integer>[] sasiedzi = new Pair[]{
            new Pair<>(-1, -1),
            new Pair<>(-1, 0),
            new Pair<>(0, -1),
            new Pair<>(1, 0),
            new Pair<>(0, 1),
            new Pair<>(1, 1),
            new Pair<>(1, -1),
            new Pair<>(-1, 1)
    };
    private GamePanel gamePanel;
    private JButton[][] buttonGrid;

    public RectangleWorld(int szerokosc, int wysokosc, GamePanel gamePanel) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.gamePanel = gamePanel;
        Swiat.gamePanel = gamePanel;
        tura = 0;
        organizmy = new ArrayList<>();
        //initGUI();
    }

    public void initGUI() {
        buttonGrid = new JButton[szerokosc][wysokosc];
        gamePanel.createGrid(szerokosc, wysokosc,buttonGrid,organizmy);
    }

    @Override
    public void rysuj(Pair<Integer, Integer> polozenie) {
        int x = polozenie.getKey();
        int y = polozenie.getValue();
        Organizm organizm = getOrganizm(polozenie);

        JButton button = buttonGrid[x][y];
        if (organizm != null) {
            String className = organizm.getClass().getSimpleName();
            button.setIcon(new ImageIcon("src/Project2/Assets/" + className + ".png"));
        } else {
            button.setIcon(new ImageIcon("src/Project2/Assets/empty.png"));
        }
    }


    @Override
    public Pair<Integer, Integer>[] getAllowedMoves() {
        return directions;
    }

    @Override
    public Pair<Integer, Integer>[] getSasiedzi() {
        return sasiedzi;
    }
}
