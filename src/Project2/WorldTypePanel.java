package Project2;

import javax.swing.*;
import java.awt.*;

public class WorldTypePanel extends JPanel {
    public WorldTypePanel(JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Wybierz typ świata:");
        label.setFont(new Font("SansSerif", Font.BOLD, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font radioButtonFont = new Font("Lato", Font.PLAIN, 32);


        JRadioButton squareButton = new JRadioButton("Kwadratowy");
        squareButton.setFont(radioButtonFont);
        squareButton.setPreferredSize(new Dimension(300, 60));

        JRadioButton hexButton = new JRadioButton("Hexagonalny");
        hexButton.setFont(radioButtonFont);
        hexButton.setPreferredSize(new Dimension(300, 60));

        ButtonGroup group = new ButtonGroup();
        group.add(squareButton);
        group.add(hexButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(squareButton);
        buttonPanel.add(hexButton);

        JButton nextButton = new JButton("Dalej");
        nextButton.setFont(new Font("SansSerif", Font.PLAIN, 40));
        nextButton.setPreferredSize(new Dimension(200, 80));

        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> {
            if (squareButton.isSelected()) {
                BoardSizePanel.setWorldType("Rectangle");
            } else if (hexButton.isSelected()) {
                BoardSizePanel.setWorldType("Hexagonal");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a world type.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the visibility of the height field in Project2.BoardSizePanel
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof BoardSizePanel) {
                    ((BoardSizePanel) component).updateHeightVisibility();
                }
            }

            cardLayout.show(cardPanel, "BoardSize");
        });

        add(Box.createRigidArea(new Dimension(0, 20)));
        add(label);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(nextButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
    }
}
