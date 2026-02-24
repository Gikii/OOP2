package Project2;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import Project2.Organizmy.Organizm;

import Project2.Organizmy.zwierzeta.Czlowiek;
import javafx.util.Pair;

public class GamePanel extends JPanel implements Serializable {
    private JPanel gridPanel;
    private JTextArea logArea;
    private JButton[][] buttonGrid; // Dwuwymiarowa tablica przycisków
    protected Swiat swiat;

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
        registerKeyBindings();

    }

    public GamePanel() {
        setLayout(new BorderLayout());

        // Left panel for buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(500, getHeight()));


        JButton saveButton = createStyledButton("Save Game");
        JButton loadButton = createStyledButton("Load Game");
        JButton nextTurnButton = createStyledButton("Next Turn");

        leftPanel.add(nextTurnButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons

        leftPanel.add(saveButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons

        leftPanel.add(Box.createRigidArea(new Dimension(100, 10)));

        leftPanel.add(loadButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons



        nextTurnButton.addActionListener(e -> {
            swiat.wykonajTure();
        });

        saveButton.addActionListener(e -> {
            swiat.zapisz();
        });

        loadButton.addActionListener(e -> {
            swiat.wczytaj();
        });

        logArea = new JTextArea();
        logArea.setFont(new Font("Arial", Font.PLAIN, 20));
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        leftPanel.add(scrollPane,BorderLayout.CENTER);

        // Center panel for grid

        gridPanel = new JPanel(new BorderLayout());

        add(leftPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);


    }
    private void registerKeyBindings() {
        InputMap inputMap = gridPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gridPanel.getActionMap();

        Pair<Integer, Integer>[] kierunekRuchu = swiat.getAllowedMoves();
        Czlowiek czlowiek = (Czlowiek) swiat.getCzlowiek();


        if(swiat instanceof HexagonWorld){
            // Q key for action Q
            inputMap.put(KeyStroke.getKeyStroke("Q"), "actionQ");
            actionMap.put("actionQ", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(czlowiek.ustawKierunek(kierunekRuchu[4]))
                        swiat.wykonajTure();
                }
            });

            // E key for action E
            inputMap.put(KeyStroke.getKeyStroke("E"), "actionE");
            actionMap.put("actionE", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(czlowiek.ustawKierunek(kierunekRuchu[5]))
                        swiat.wykonajTure();
                }
            });
        }



        // W key for up
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //moveUp();
                if(czlowiek.ustawKierunek(kierunekRuchu[0]))
                    swiat.wykonajTure();
            }
        });

        // S key for down
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czlowiek.ustawKierunek(kierunekRuchu[1]))
                    swiat.wykonajTure();
            }

        });

        // A key for left
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czlowiek.ustawKierunek(kierunekRuchu[2]))
                    swiat.wykonajTure();
            }
        });

        // D key for right
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czlowiek.ustawKierunek(kierunekRuchu[3]))
                    swiat.wykonajTure();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "executeTurn");
        actionMap.put("executeTurn", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swiat.wykonajTure();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke("R"), "activateAbility");
        actionMap.put("activateAbility", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(czlowiek.aktywujUmiejetnosc()){
                    swiat.wykonajTure();
                }
            }
        });


    }


    public void createGrid(int width, int height, JButton[][] buttonGrid, ArrayList<Organizm> organizmy) {
        gridPanel.removeAll(); // Clear the panel
        JPanel squareGridPanel = new JPanel(new GridLayout(height, width));


        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = new JButton();
                button.putClientProperty("coordinates", new Pair<>(j, i));
                ImageIcon icon = new ImageIcon("src/Project2/Assets/empty.png");
                button.setIcon(icon);

                buttonGrid[j][i] = button;

                squareGridPanel.add(button);

                button.addActionListener(e -> {
                    JButton sourceButton = (JButton) e.getSource();
                    Pair<Integer, Integer> coordinates = (Pair<Integer, Integer>) sourceButton.getClientProperty("coordinates");
                    if(swiat.getOrganizm(coordinates)==null){
                        showAnimalSelectionMenu(sourceButton, coordinates);
                    }


                });
            }
        }



        gridPanel.add(squareGridPanel, BorderLayout.CENTER);

        gridPanel.revalidate(); // Refresh the panel
        gridPanel.repaint(); // Redraw the panel
    }




    public void createHexagonGrid(int width, int height, JButton[][] buttonGrid, ArrayList<Organizm> organizmy) {
        gridPanel.removeAll(); // Clear the panel
        JPanel hexGridPanel = new JPanel(null);

        int buttonSize=64;

        if(width>16 && width<19){
            buttonSize=54;
        }else if(width>18 && width<24){
            buttonSize=44;
        }else if(width>23){
            buttonSize=34;
        }

        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                JButton button = new JButton();
                button.setBounds(buttonSize*(height-j-1)+i*buttonSize+1,buttonSize/2*i+j*buttonSize/2+1,buttonSize,buttonSize);
                button.putClientProperty("coordinates", new Pair<>(j, i));
                ImageIcon icon = new ImageIcon("src/Project2/Assets/empty.png");
                button.setIcon(icon);
                buttonGrid[j][i] = button;

                hexGridPanel.add(button);

                button.addActionListener(e -> {
                    JButton sourceButton = (JButton) e.getSource();
                    Pair<Integer, Integer> coordinates = (Pair<Integer, Integer>) sourceButton.getClientProperty("coordinates");
                    if(swiat.getOrganizm(coordinates)==null){
                        showAnimalSelectionMenu(sourceButton, coordinates);
                    }
                });
            }

        }
        gridPanel.add(hexGridPanel, BorderLayout.CENTER);

        gridPanel.revalidate(); // Refresh the panel
        gridPanel.repaint(); // Redraw the panel


    }
    private void showAnimalSelectionMenu(JButton button, Pair<Integer, Integer> coordinates) {
        JPopupMenu animalMenu = new JPopupMenu();

        String[] animals = {"zwierzeta.Owca", "zwierzeta.Wilk", "zwierzeta.Antylopa", "zwierzeta.Lis","zwierzeta.Zolw", "rosliny.BarszczSosnowskiego","rosliny.Guarana", "rosliny.Mlecz", "rosliny.Trawa", "rosliny.WilczeJagody"}; // Example animals


        for (String animal : animals) {
            String simpleName = getSimpleName(animal);
            JMenuItem menuItem = new JMenuItem(simpleName);
            menuItem.addActionListener(e -> {
                log(simpleName + " Pojawił się na: " + coordinates.getKey() + ", " + coordinates.getValue());
                swiat.dodajOrganizm(animal, coordinates);
                swiat.rysuj(coordinates);
            });
            animalMenu.add(menuItem);
        }

        animalMenu.show(button, button.getWidth() / 2, button.getHeight() / 2);
    }
    public void log(String message) {
        logArea.append(message + "\n");
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(450, 50)); // Set preferred size
        button.setMaximumSize(new Dimension(450, 50)); // Ensure the button does not exceed this size
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0x2196F3)); // Set button background color
        button.setForeground(Color.BLACK); // Set button text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding
        return button;
    }
    private String getSimpleName(String className) {
        String[] parts = className.split("\\.");
        return parts[parts.length - 1];
    }
}
