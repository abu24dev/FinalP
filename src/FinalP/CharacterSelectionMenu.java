package FinalP;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CharacterSelectionMenu extends JFrame {

    private boolean isMultiplayer;
    private AudioPlayer menuMusic;

    // متغيرات لحفظ الأسماء المستلمة من الشاشة السابقة
    private String p1Name;
    private String p2Name;

    // اختيارات الشخصيات (القيم الافتراضية)
    private int p1Choice = 0;
    private int p2Choice = 2;

    private JButton[] p1Buttons = new JButton[3];
    private JButton[] p2Buttons = new JButton[3];

    // مسارات الصور
    String[] iconPaths = {
            "Assets/ShinobiIcon.png",
            "Assets/FighterIcon.png",
            "Assets/SamuraiIcon.png" // تأكد من المسار ده
    };



    String[] charNames = {"Shinobi", "Fighter", "Samurai"};

    // الكونستركتور المعدل لاستقبال الأسماء
    public CharacterSelectionMenu(boolean isMultiplayer, AudioPlayer music, String name1, String name2) {
        this.isMultiplayer = isMultiplayer;
        this.menuMusic = music;
        this.p1Name = name1;
        this.p2Name = name2;

        setTitle("Select Characters");
        setSize(1000, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // إعداد الخلفية
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // رسم الخلفية وتغميقها
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                g.setColor(new Color(0, 0, 0, 100));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        setLayout(new BorderLayout());

        // العنوان الرئيسي
        JLabel title = new JLabel("CHOOSE YOUR FIGHTERS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // منطقة الاختيار (Split Panel)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 100, 0));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        // إنشاء أقسام اللاعبين باستخدام الأسماء الممررة
        JPanel p1Panel = createPlayerSection(p1Name, 1);
        JPanel p2Panel = createPlayerSection(p2Name, 2);

        mainPanel.add(p1Panel);
        mainPanel.add(p2Panel);
        add(mainPanel, BorderLayout.CENTER);

        // زر البدء (FIGHT)
        JButton startBtn = new JButton("FIGHT!");



        styleButtonWithImage(startBtn, "Assets/btn.png", "Assets/btn2.png");

        startBtn.addActionListener(e -> {
            if (menuMusic != null) menuMusic.stop();
            dispose();
            // بدء اللعبة وتمرير الاختيارات (يمكنك أيضاً تمرير الأسماء للعبة لاحقاً إذا أردت عرضها بالداخل)
            new Anim(isMultiplayer, p1Choice, p2Choice);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(startBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createPlayerSection(String titleText, int playerNum) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // عرض اسم اللاعب
        JLabel lbl = new JLabel(titleText);
        lbl.setFont(new Font("Arial", Font.BOLD, 28));
        // تلوين الاسم (أزرق للاعب 1، وأخضر/بنفسجي للاعب 2)
        lbl.setForeground(playerNum == 1 ? new Color(0, 255, 255) : (isMultiplayer ? new Color(0, 255, 0) : new Color(255, 0, 255)));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        panel.add(lbl);

        // إنشاء أزرار الشخصيات
        for (int i = 0; i < 3; i++) {
            int charIndex = i;
            JButton btn = new JButton(charNames[i]);

            // إعداد الصورة
            ImageIcon originalIcon = new ImageIcon(iconPaths[i]);
            Image img = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));

            // تنسيق النص والصورة
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setIconTextGap(10);

            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.setForeground(Color.WHITE);

            // خلفية سوداء صريحة (تعديلك السابق)
            btn.setBackground(Color.BLACK);
            btn.setOpaque(true);

            btn.setFocusPainted(false);

            Dimension btnSize = new Dimension(160, 140);
            btn.setPreferredSize(btnSize);
            btn.setMaximumSize(btnSize);
            btn.setMinimumSize(btnSize);

            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));

            if (playerNum == 1) p1Buttons[i] = btn;
            else p2Buttons[i] = btn;

            // الأكشن عند الضغط
            btn.addActionListener(e -> {
                if (playerNum == 1) {
                    p1Choice = charIndex;
                    updateSelectionVisuals(p1Buttons, charIndex, new Color(0, 255, 255));
                } else {
                    p2Choice = charIndex;
                    updateSelectionVisuals(p2Buttons, charIndex, isMultiplayer ? new Color(0, 255, 0) : new Color(255, 0, 255));
                }
            });

            panel.add(btn);
            panel.add(Box.createVerticalStrut(15));
        }

        // تفعيل الاختيار الافتراضي
        if (playerNum == 1) updateSelectionVisuals(p1Buttons, p1Choice, new Color(0, 255, 255));
        else updateSelectionVisuals(p2Buttons, p2Choice, isMultiplayer ? new Color(0, 255, 0) : new Color(255, 0, 255));

        return panel;
    }

    private void updateSelectionVisuals(JButton[] buttons, int selectedIndex, Color color) {
        for (int i = 0; i < buttons.length; i++) {
            if (i == selectedIndex) {
                // المختار: حدود ملونة وخلفية رمادي غامق
                buttons[i].setBorder(new LineBorder(color, 4));
                buttons[i].setBackground(new Color(64, 64, 64));
            } else {
                // غير المختار: حدود رمادية وخلفية سوداء
                buttons[i].setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
                buttons[i].setBackground(Color.BLACK);
            }
        }
    }
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