package FinalP;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private AudioPlayer menuMusic;
    private ImageIcon soundOnIcon;
    private ImageIcon soundOffIcon;
    private JButton soundBtn;

    // الكونستركتور الافتراضي (لأول مرة تشغيل)
    public MainMenu() {
        this(false);
    }

    // الكونستركتور المعدل (لاستقبال حالة الصوت عند الرجوع)
    public MainMenu(boolean startMuted) {
        setTitle("Fighting Game - Main Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // تحميل الصوت والأيقونات
        menuMusic = new AudioPlayer("Assets/menu.wav");
        soundOnIcon = resizeIcon("Assets/sound_on.png", 50, 50);
        soundOffIcon = resizeIcon("Assets/sound_off.png", 50, 50);

        // ضبط حالة الصوت بناءً على ما تم تمريره
        if (startMuted) {
            menuMusic.toggleMute(); // كتم الصوت
            soundBtn = new JButton(soundOffIcon);
        } else {
            menuMusic.playMusic(); // تشغيل الصوت
            soundBtn = new JButton(soundOnIcon);
        }

        // الخلفية
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // زر الصوت
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        styleSoundButton(soundBtn);

        soundBtn.addActionListener(e -> {
            menuMusic.toggleMute();
            if (menuMusic.isMuted()) {
                soundBtn.setIcon(soundOffIcon);
            } else {
                soundBtn.setIcon(soundOnIcon);
            }
        });
        topPanel.add(soundBtn);
        add(topPanel);

        // الأزرار الرئيسية
        JButton newGameBtn = new JButton("New Game");
        JButton highScoresBtn = new JButton("High Scores");
        JButton controlsBtn = new JButton("Controls");
        JButton exitBtn = new JButton("Exit");

        styleButtonWithImage(newGameBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(highScoresBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(controlsBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(exitBtn, "Assets/btn.png", "Assets/btn2.png");

        add(Box.createVerticalStrut(60));
        add(center(newGameBtn));
        add(Box.createVerticalStrut(20));
        add(center(highScoresBtn));
        add(Box.createVerticalStrut(20));
        add(center(controlsBtn));
        add(Box.createVerticalStrut(20));
        add(center(exitBtn));

        // --- Action Listeners ---

        newGameBtn.addActionListener(e -> {
            dispose();
            new GameModeMenu(menuMusic);
        });

        // زر الهايسكور يفتح القائمة الجديدة ويمرر الموسيقى
        highScoresBtn.addActionListener(e -> {
            dispose();
            new HighScoreMenu(menuMusic);
        });

        controlsBtn.addActionListener(e -> {
            dispose();
            new ControlsMenu(false, menuMusic);
        });

        exitBtn.addActionListener(e -> System.exit(0));

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

    public static void main(String[] args) {
        new MainMenu();
    }
}