package client.userInterface.menu;

import client.clientMain.ClientMain;
import client.clientMain.Constants;
import client.clientMain.sound.SoundManager;
import shared.enums.PlayerModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainMenu {
    public void open() {
        JFrame window = new JFrame();
        window.setTitle("Profex's warzone");
        Panel panel = new Panel(window);
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        panel.startThread();
    }

    private static class Panel extends JPanel implements Runnable {
        private static final Dimension screenSize = new Dimension(800, 500);
        private Thread thread;
        private final JFrame window;
        private final BufferedImage background;
        private JPanel largePanel, bottomPanel;
        private final JPanel mainPanel = new JPanel();
        private JComboBox<String> dropBox;
        private JTextField ipBox, portBox, userBox;
        private final JButton nameJoinButton = new JButton("join");
        private int walkAnimNum = 1;
        private final PlayerModelMenu playerModelMenu = new PlayerModelMenu();

        public Panel(JFrame window) {
            this.window = window;
            this.setPreferredSize(screenSize);
            this.setBackground(Color.black);
            this.setDoubleBuffered(true);
            try {
                background = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("menu/mainMenuBackground.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            createComponents();
            playerModelMenu.setPlayerImage(String.valueOf(dropBox.getSelectedItem()));
            SoundManager.playMainMenuSoundtrack();
        }

        public void startThread() {
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while (thread != null) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (walkAnimNum == 1) {
                    walkAnimNum = 2;
                } else if (walkAnimNum == 2) {
                    walkAnimNum = 3;
                } else if (walkAnimNum == 3) {
                    walkAnimNum = 4;
                } else {
                    walkAnimNum = 1;
                }
                largePanel.repaint();
            }
        }

        private void createComponents() {
            mainPanel.setPreferredSize(new Dimension(400, 0));
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));

            createPlayerModelPanel();
            createServerControlPanel();
            createDropBox();

            mainPanel.add(largePanel, BorderLayout.CENTER);
            mainPanel.add(dropBox, BorderLayout.NORTH);
            mainPanel.add(bottomPanel, BorderLayout.SOUTH);
            this.setLayout(new BorderLayout());
            this.add(mainPanel, BorderLayout.EAST);
        }

        private void createPlayerModelPanel() {
            largePanel = new LargePanel();
            largePanel.setBackground(new Color(8, 0, 255));
        }

        private class LargePanel extends JPanel {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                playerModelMenu.draw(g2, walkAnimNum);
            }
        }

        private void createServerControlPanel() {
            bottomPanel = new JPanel(new GridLayout(1, 2));
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.add(new JLabel("IP:"), BorderLayout.WEST);
            ipBox = new JTextField("localhost");
            leftPanel.add(ipBox, BorderLayout.CENTER);

            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(new JLabel("Port:"), BorderLayout.WEST);
            portBox = new JTextField("8080");
            rightPanel.add(portBox, BorderLayout.CENTER);

            bottomPanel.add(leftPanel);
            bottomPanel.add(rightPanel);
            userBox = new JTextField();

            JButton ipJoinButton = new JButton("Find server");
            bottomPanel.add(ipJoinButton);
            bottomPanel.setFocusable(true);

            ipJoinButton.addActionListener(e -> joinServer());
            nameJoinButton.addActionListener(e -> enterName());

            ipBox.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        joinServer();
                    }
                }
            });

            portBox.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        joinServer();
                    }
                }
            });

            userBox.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        enterName();
                    }
                }
            });
        }

        private void joinServer() {
            try {
                ClientMain.startGame(ipBox.getText(), Integer.parseInt(portBox.getText()));
                swapButtons();
            } catch (Exception ex) {
                System.out.println("no server");
            }
        }

        private void enterName() {
            try {
                ClientMain.setName(userBox.getText(), String.valueOf(dropBox.getSelectedItem()));
                SoundManager.stopMainMenuSoundtrack();
                window.dispose();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        public void swapButtons() {
            bottomPanel.removeAll();
            bottomPanel.add(new JLabel("Username:"));
            bottomPanel.add(userBox);
            bottomPanel.add(nameJoinButton);
            bottomPanel.revalidate();
            bottomPanel.repaint();
        }

        private void createDropBox() {
            String[] options = {PlayerModel.DEFAULT.name, PlayerModel.BANDIT.name, PlayerModel.BANDIT_2.name, PlayerModel.BANDIT_3.name,
                    PlayerModel.HAZMAT_SUIT.name, PlayerModel.REBEL.name, PlayerModel.SCAVENGER.name, PlayerModel.SCIENTIST.name, PlayerModel.SCIENTIST_2.name,
                    PlayerModel.SCIENTIST_3.name, PlayerModel.SCOUT.name, PlayerModel.SOLDIER.name, PlayerModel.SOLDIER_2.name};
            dropBox = new JComboBox<>(options);
            dropBox.addActionListener(e -> playerModelMenu.setPlayerImage(String.valueOf(dropBox.getSelectedItem())));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(background, 0, 0, 400, 500, null);
            g2.setColor(Color.black);
            g2.setFont(new Font("impact", Font.PLAIN, 55));
            g2.drawString("Profex's warzone", 10, 70);
            g2.setColor(Color.red);
            g2.setFont(Constants.font25);
            g2.drawString("Soundtrack by SUPREMER", 10, 485);
        }
    }
}
