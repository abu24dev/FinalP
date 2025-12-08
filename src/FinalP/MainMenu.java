package FinalP;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private AudioPlayer menuMusic;
    private ImageIcon soundOnIcon;
    private ImageIcon soundOffIcon;
    private JButton soundBtn;

    // الكونستركتور الأساسي (بيستخدم لما تفتح اللعبة أول مرة)
    public MainMenu() {
        this(false); // بيبدأ والصوت شغال افتراضياً
    }

    // --- التعديل هنا: كونستركتور بيستقبل حالة الصوت ---
    public MainMenu(boolean startMuted) {
        setTitle("Fighting Game - Main Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // تحميل الموسيقى والأيقونات
        menuMusic = new AudioPlayer("Assets/menu.wav");
        soundOnIcon = resizeIcon("Assets/sound_on.png", 50, 50);
        soundOffIcon = resizeIcon("Assets/sound_off.png", 50, 50);

        // --- ضبط حالة الصوت بناءً على startMuted ---
        if (startMuted) {
            // لو كان معمول ميوت، بنعمل ميوت للكائن الجديد ونحط أيقونة الصوت مقفول
            menuMusic.toggleMute();
            soundBtn = new JButton(soundOffIcon);
        } else {
            // لو شغال، بنشغل الموسيقى ونحط أيقونة الصوت شغال
            menuMusic.playMusic();
            soundBtn = new JButton(soundOnIcon);
        }

        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JButton newGameBtn = new JButton("New Game");
        JButton highScoresBtn = new JButton("High Scores");
        JButton exitBtn = new JButton("Exit");

        styleButtonWithImage(newGameBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(highScoresBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(exitBtn, "Assets/btn.png", "Assets/btn2.png");

        add(Box.createVerticalStrut(80));
        add(center(newGameBtn));
        add(Box.createVerticalStrut(20));
        add(center(highScoresBtn));
        add(Box.createVerticalStrut(20));
        add(center(exitBtn));

        // الانتقال للقائمة التالية (GameModeMenu) مع تمرير الموسيقى الحالية
        newGameBtn.addActionListener(e -> {
            dispose();
            // بنبعت كائن الموسيقى نفسه عشان GameModeMenu يقدر يتحكم فيه أو يعرف حالته
            new GameModeMenu(menuMusic);
        });

        highScoresBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "High Scores Coming Soon!");
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