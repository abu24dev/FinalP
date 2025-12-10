package FinalP;

import javax.swing.*;
import java.awt.*;

public class GameModeMenu extends JFrame {
    private ImageIcon soundOnIcon;
    private ImageIcon soundOffIcon;
    private JButton soundBtn;

    public GameModeMenu(AudioPlayer menuMusic) {
        setTitle("Select Game Mode");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        soundOnIcon = resizeIcon("Assets/sound_on.png", 50, 50);
        soundOffIcon = resizeIcon("Assets/sound_off.png", 50, 50);


        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background2.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);

        if (menuMusic != null && menuMusic.isMuted()) {
            soundBtn = new JButton(soundOffIcon);
        } else {
            soundBtn = new JButton(soundOnIcon);
        }
        styleSoundButton(soundBtn);

        soundBtn.addActionListener(e -> {
            if (menuMusic != null) {
                menuMusic.toggleMute();
                if (menuMusic.isMuted()) {
                    soundBtn.setIcon(soundOffIcon);
                } else {
                    soundBtn.setIcon(soundOnIcon);
                }
            }
        });
        topPanel.add(soundBtn);
        add(topPanel);


        JLabel title = new JLabel("SELECT GAME MODE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);


        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn = new JButton("Multiplayer");
        JButton backBtn = new JButton("Back");

        styleButtonWithImage(singleBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(multiBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(backBtn, "Assets/btn.png", "Assets/btn2.png");

        add(Box.createVerticalStrut(60));
        add(center(title));
        add(Box.createVerticalStrut(40));
        add(center(singleBtn));
        add(Box.createVerticalStrut(20));
        add(center(multiBtn));
        add(Box.createVerticalStrut(20));
        add(center(backBtn));



        singleBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new DifficultySelectionMenu(menuMusic);
        });


        multiBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();

            new NameInputMenu(true, menuMusic, "MEDIUM");
        });


        backBtn.addActionListener(e -> {
            boolean isCurrentlyMuted = (menuMusic != null && menuMusic.isMuted());
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new MainMenu(isCurrentlyMuted);
        });

        setVisible(true);
    }

    private JPanel center(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);
        p.add(c);
        return p;
    }
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
    private void styleSoundButton(JButton b) {
        b.setPreferredSize(new Dimension(60, 60));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        int width = 350;
        int height = 70;
        ImageIcon iconNormal = new ImageIcon(normalPath);
        Image imgNormal = iconNormal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(imgNormal));
        ImageIcon iconPressed = new ImageIcon(pressedPath);
        Image imgPressed = iconPressed.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setPressedIcon(new ImageIcon(imgPressed));
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setForeground(Color.WHITE);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setPreferredSize(new Dimension(width, height));
    }
}