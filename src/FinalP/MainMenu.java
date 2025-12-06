package FinalP;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private AudioPlayer menuMusic;
    private ImageIcon soundOnIcon;
    private ImageIcon soundOffIcon;
    private JButton soundBtn;

    public MainMenu() {
        setTitle("Fighting Game - Main Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuMusic = new AudioPlayer("Assets/menu.wav");
        menuMusic.playMusic();

        soundOnIcon = resizeIcon("Assets/sound_on.png", 50, 50);
        soundOffIcon = resizeIcon("Assets/sound_off.png", 50, 50);

        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Summer4.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // محاذاة لليمين
        topPanel.setOpaque(false);

        soundBtn = new JButton(soundOnIcon);
        styleSoundButton(soundBtn);

        soundBtn.addActionListener(e -> {
            menuMusic.toggleMute();
            // تبديل الأيقونة حسب الحالة
            if (menuMusic.isMuted()) {
                soundBtn.setIcon(soundOffIcon);
            } else {
                soundBtn.setIcon(soundOnIcon);
            }
        });
        topPanel.add(soundBtn);
        add(topPanel);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);



        JButton newGameBtn = new JButton("New Game");
        JButton highScoresBtn = new JButton("High Scores");
        JButton exitBtn = new JButton("Exit");


        styleButton(newGameBtn);
        styleButton(highScoresBtn);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 24));
        exitBtn.setPreferredSize(new Dimension(300, 60));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);

        add(Box.createVerticalStrut(80));
        add(center(newGameBtn));
        add(Box.createVerticalStrut(20));
        add(center(highScoresBtn));
        add(Box.createVerticalStrut(20));
        add(center(exitBtn));


        newGameBtn.addActionListener(e -> {
            dispose();
            new GameModeMenu(menuMusic);
        });

        highScoresBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "High Scores Coming Soon!");
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setPreferredSize(new Dimension(350, 70));
        b.setFocusPainted(false);
        b.setBackground( new Color(126, 122, 122, 255));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
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

    public static void main(String[] args) {
        new MainMenu();
    }
}
