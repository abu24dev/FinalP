package FinalP;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreMenu extends JFrame {

    private JPanel listPanel;
    private AudioPlayer menuMusic;

    public HighScoreMenu(AudioPlayer music) {
        this.menuMusic = music;
        setTitle("High Scores");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        JLabel title = new JLabel("HALL OF FAME", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.YELLOW);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // لوحة التحكم والتبويبات
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        filterPanel.setOpaque(false);

        JButton btnEasy = new JButton("EASY");
        JButton btnMedium = new JButton("MEDIUM");
        JButton btnHard = new JButton("HARD");

        styleTabButton(btnEasy);
        styleTabButton(btnMedium);
        styleTabButton(btnHard);

        filterPanel.add(btnEasy);
        filterPanel.add(btnMedium);
        filterPanel.add(btnHard);

        // قائمة عرض النتايج
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setMaximumSize(new Dimension(800, 400));

        centerPanel.add(filterPanel);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(scrollPane);

        add(centerPanel, BorderLayout.CENTER);

        // زر الرجوع
        JButton backBtn = new JButton("BACK");
        styleButtonWithImage(backBtn, "Assets/btn.png", "Assets/btn2.png");

        // الأكشن المهم لإصلاح الصوت
        backBtn.addActionListener(e -> {
            boolean wasMuted = (menuMusic != null && menuMusic.isMuted());
            if (menuMusic != null) menuMusic.stop();
            dispose();
            new MainMenu(wasMuted); // العودة للقائمة الرئيسية بنفس الحالة
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // تفعيل أزرار الفلتر
        btnEasy.addActionListener(e -> loadScores("EASY"));
        btnMedium.addActionListener(e -> loadScores("MEDIUM"));
        btnHard.addActionListener(e -> loadScores("HARD"));

        // تحميل الافتراضي
        loadScores("EASY");

        setVisible(true);
    }

    private void loadScores(String difficulty) {
        listPanel.removeAll();
        // جلب النتايج من HighScoreManager
        ArrayList<HighScoreManager.Score> scores = HighScoreManager.getScoresByDifficulty(difficulty);

        if (scores.isEmpty()) {
            JLabel empty = new JLabel("No scores yet for " + difficulty);
            empty.setFont(new Font("Arial", Font.ITALIC, 24));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalStrut(50));
            listPanel.add(empty);
        } else {
            // الهيدر
            JPanel header = createRow("RANK", "NAME", "TIME (Sec)", new Color(255, 215, 0));
            listPanel.add(header);
            listPanel.add(Box.createVerticalStrut(10));

            for (int i = 0; i < scores.size(); i++) {
                if (i >= 10) break; // عرض أول 10 فقط
                HighScoreManager.Score s = scores.get(i);
                JPanel row = createRow("#" + (i + 1), s.name, s.time + "s", Color.WHITE);
                listPanel.add(row);
                listPanel.add(Box.createVerticalStrut(5));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createRow(String c1, String c2, String c3, Color color) {
        JPanel p = new JPanel(new GridLayout(1, 3));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(700, 40));

        JLabel l1 = new JLabel(c1, JLabel.CENTER);
        JLabel l2 = new JLabel(c2, JLabel.CENTER);
        JLabel l3 = new JLabel(c3, JLabel.CENTER);

        Font font = new Font("Arial", Font.BOLD, 20);
        l1.setFont(font); l2.setFont(font); l3.setFont(font);
        l1.setForeground(color); l2.setForeground(color); l3.setForeground(color);

        p.add(l1); p.add(l2); p.add(l3);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(100, 100, 100)));
        return p;
    }

    private void styleTabButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 18));
        b.setBackground(new Color(50, 50, 50));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(150, 40));
        b.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        int width = 350; int height = 70;
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