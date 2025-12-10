package FinalP;

import javax.swing.*;
import java.awt.*;

public class DifficultySelectionMenu extends JFrame {

    private AudioPlayer menuMusic;

    public DifficultySelectionMenu(AudioPlayer music) {
        this.menuMusic = music;

        setTitle("Select Difficulty");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // الخلفية
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 150)); // تغميق
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // العنوان
        JLabel title = new JLabel("SELECT DIFFICULTY");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(50));
        add(title);
        add(Box.createVerticalStrut(50));

        // الأزرار
        JButton easyBtn = new JButton("EASY");
        JButton mediumBtn = new JButton("MEDIUM");
        JButton hardBtn = new JButton("HARD");

        // تطبيق الستايل بالصور (نفس الزرار المستخدم في القوائم الثانية)
        styleButtonWithImage(easyBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(mediumBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(hardBtn, "Assets/btn.png", "Assets/btn2.png");

        add(easyBtn);
        add(Box.createVerticalStrut(20));
        add(mediumBtn);
        add(Box.createVerticalStrut(20));
        add(hardBtn);

        // الأكشن لكل زرار (بينقل على شاشة الأسماء ويبعت مستوى الصعوبة)
        easyBtn.addActionListener(e -> {
            dispose();
            new NameInputMenu(false, menuMusic, "EASY");
        });

        mediumBtn.addActionListener(e -> {
            dispose();
            new NameInputMenu(false, menuMusic, "MEDIUM");
        });

        hardBtn.addActionListener(e -> {
            dispose();
            new NameInputMenu(false, menuMusic, "HARD");
        });

        setVisible(true);
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
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(width, height));
        b.setMaximumSize(new Dimension(width, height));
    }
}