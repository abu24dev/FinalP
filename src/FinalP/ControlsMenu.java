package FinalP;

import javax.swing.*;
import java.awt.*;

public class ControlsMenu extends JFrame {

    private boolean isFromGame; // لتحديد مصدر الدخول
    private AudioPlayer menuMusic;

    public ControlsMenu(boolean isFromGame, AudioPlayer music) {
        this.isFromGame = isFromGame;
        this.menuMusic = music;

        setTitle("Game Controls");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(isFromGame ? JFrame.DISPOSE_ON_CLOSE : JFrame.EXIT_ON_CLOSE);

        // الخلفية
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        setLayout(new BorderLayout());

        // العنوان
        JLabel title = new JLabel("HOW TO PLAY", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // منطقة التحكمات
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        // --- Player 1 Controls ---
        JPanel p1Panel = createControlPanel("PLAYER 1", Color.CYAN,
                "Move: A, D",
                "Jump: W",
                "Shield: S",
                "Attack 1: Z",
                "Attack 2: X",
                "Attack 3: C"
        );

        // --- Player 2 Controls ---
        JPanel p2Panel = createControlPanel("PLAYER 2", Color.GREEN,
                "Move: Left, Right",
                "Jump: Up",
                "Shield: Down",
                "Attack 1: J",
                "Attack 2: K",
                "Attack 3: L"
        );

        mainPanel.add(p1Panel);
        mainPanel.add(p2Panel);
        add(mainPanel, BorderLayout.CENTER);

        // زر الرجوع
        JButton backBtn = new JButton("BACK");
        styleButtonWithImage(backBtn, "Assets/btn.png", "Assets/btn2.png");

        // --- التعديل هنا (إصلاح زر الميوت وتداخل الصوت) ---
        backBtn.addActionListener(e -> {

            // 1. حفظ حالة الصوت الحالية قبل الخروج
            boolean wasMuted = (menuMusic != null && menuMusic.isMuted());

            // 2. إيقاف الموسيقى القديمة (عشان متشتغلش فوق الجديدة)
            if (!isFromGame && menuMusic != null) {
                menuMusic.stop();
            }

            dispose(); // إغلاق النافذة الحالية

            // 3. لو راجع للقائمة الرئيسية، ابعتلها حالة الصوت
            if (!isFromGame) {
                new MainMenu(wasMuted);
            }
        });
        // ------------------------------------------------

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createControlPanel(String title, Color color, String... controls) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitle.setForeground(color);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(20));
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(30));

        for (String s : controls) {
            JLabel lbl = new JLabel(s);
            lbl.setFont(new Font("Arial", Font.BOLD, 22));
            lbl.setForeground(Color.WHITE);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(lbl);
            panel.add(Box.createVerticalStrut(15));
        }

        return panel;
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