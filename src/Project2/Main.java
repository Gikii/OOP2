package Project2;

import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame = new JFrame("Symulator Świata - Kacper Mikołajuk 198254");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(2500, 1600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        WorldTypePanel worldTypePanel = new WorldTypePanel(cardPanel, cardLayout);
        BoardSizePanel boardSizePanel = new BoardSizePanel(cardPanel, cardLayout);
        GamePanel gamePanel = new GamePanel();

        cardPanel.add(worldTypePanel, "WorldType");
        cardPanel.add(boardSizePanel, "BoardSize");
        cardPanel.add(gamePanel, "Game");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
