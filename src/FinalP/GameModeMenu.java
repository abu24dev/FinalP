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
                // تحديث الأيقونة
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

        // ... داخل الكونستركتور ...

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn = new JButton("Multiplayer");
        JButton backBtn = new JButton("Back");

// --- التعديل هنا ---
        styleButtonWithImage(singleBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(multiBtn, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(backBtn, "Assets/btn.png", "Assets/btn2.png");
// -------------------

// إضافة الأزرار للواجهة
        add(Box.createVerticalStrut(60));
        add(center(title));
        add(Box.createVerticalStrut(40));
        add(center(singleBtn));
        add(Box.createVerticalStrut(20));
        add(center(multiBtn));
        add(Box.createVerticalStrut(20));
        add(center(backBtn));

        // Actions
        singleBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new Anim();
        });

        multiBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new Anim("Multiplayer");
        });

        backBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setPreferredSize(new Dimension(350, 70));
        b.setFocusPainted(false);
        b.setBackground(new Color(126, 122, 122, 255));
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
    // ضيف الدالة دي تحت styleButton القديمة
    // دالة لتنسيق الزرار بالصور (عادي ومضغوط)
    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        // 1. إعداد الأبعاد (نفس أبعادك القديمة)
        int width = 350;
        int height = 70;

        // 2. تحميل وتغيير حجم الصورة العادية
        ImageIcon iconNormal = new ImageIcon(normalPath);
        Image imgNormal = iconNormal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(imgNormal));

        // 3. تحميل وتغيير حجم صورة الضغط (Pressed)
        ImageIcon iconPressed = new ImageIcon(pressedPath);
        Image imgPressed = iconPressed.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setPressedIcon(new ImageIcon(imgPressed));

        // 4. تنسيق النص (أبيض وفي المنتصف تماماً)
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setForeground(Color.WHITE);
        b.setHorizontalTextPosition(JButton.CENTER); // النص أفقي في المنتصف
        b.setVerticalTextPosition(JButton.CENTER);   // النص رأسي في المنتصف

        // 5. إزالة الخلفيات والحدود الافتراضية
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);

        // 6. تحديد الحجم
        b.setPreferredSize(new Dimension(width, height));
    }

}


