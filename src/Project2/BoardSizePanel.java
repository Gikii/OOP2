package Project2;

import javax.swing.*;
import java.awt.*;

public class BoardSizePanel extends JPanel {
    private static String worldType;
    private JTextField widthField;
    private JTextField heightField;
    private JLabel heightLabel;

    public BoardSizePanel(JPanel cardPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());

        Font fieldFont = new Font("SansSerif", Font.PLAIN, 30);

        JLabel label = new JLabel("Podaj wymiary planszy:");
        label.setFont(new Font("SansSerif", Font.BOLD, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel widthLabel = new JLabel("Szerokość:");
        widthLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        widthField = new JTextField(5);
        widthField.setFont(fieldFont);
        widthField.setPreferredSize(new Dimension(200, 50));

        heightLabel = new JLabel("Wysokość:");
        heightLabel.setFont(new Font("SansSerif", Font.PLAIN, 30));
        heightField = new JTextField(5);
        heightField.setFont(fieldFont);
        heightField.setPreferredSize(new Dimension(200, 50));

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(widthLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(widthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(heightLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(heightField, gbc);

        JButton nextButton = new JButton("Dalej");
        nextButton.setFont(new Font("SansSerif", Font.PLAIN, 40));
        nextButton.setPreferredSize(new Dimension(150, 60));

        nextButton.addActionListener(e -> {
            String widthText = widthField.getText();
            String heightText = heightField.getText();

            try {
                int width = Integer.parseInt(widthText);
                int height = 0;
                boolean isHex = "Hexagonal".equals(worldType);
                if (isHex) {
                    height = width; // For hexagonal, height is same as width
                } else {
                    height = Integer.parseInt(heightText);
                }

                // Pass width and height to the game panel
                for (Component component : cardPanel.getComponents()) {
                    if (component instanceof GamePanel) {
                        GamePanel gamePanel = (GamePanel) component;
                        //gamePanel.setHexagonal(isHex);
                        //gamePanel.createGrid(width, height);
                        Swiat world = null;
                        if (isHex) {
                            //world = new HexagonWorld(width, height, gamePanel);
                            world.getInstance("Hexagon",width,height,gamePanel);
                            world.getInstance().initGUI();
                            world.getInstance().zaludnijSwiat();
                        } else {
                            world.getInstance("Rectangle",width,height,gamePanel);
                            world.getInstance().initGUI();
                            world.getInstance().zaludnijSwiat();

                        }
                        gamePanel.setSwiat(world.getInstance());
                        world.getInstance().wykonajTure();
                    }
                }

                cardLayout.show(cardPanel, "Game");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for width and height.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);

        add(label, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        updateHeightVisibility();
    }

    public static void setWorldType(String type) {
        worldType = type;
    }

    public static String getWorldType() {
        return worldType;
    }

    public void updateHeightVisibility() {
        if ("Hexagonal".equals(worldType)) {
            heightLabel.setVisible(false);
            heightField.setVisible(false);
        } else {
            heightLabel.setVisible(true);
            heightField.setVisible(true);
        }
    }
}
