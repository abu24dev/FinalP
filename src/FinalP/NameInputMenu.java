package FinalP;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NameInputMenu extends JFrame {

    private boolean isMultiplayer;
    private AudioPlayer menuMusic;
    private JTextField p1NameField;
    private JTextField p2NameField;

    public NameInputMenu(boolean isMultiplayer, AudioPlayer music) {
        this.isMultiplayer = isMultiplayer;
        this.menuMusic = music;

        setTitle("Enter Player Names");
        // زودنا العرض شوية (600) عشان العنوان ميتكتبش ناقص
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // خلفية النافذة
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                // طبقة سوداء أتقل شوية عشان التباين يبقى أحسن
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // --- العنوان ---
        JLabel title = new JLabel("ENTER NAMES");
        title.setFont(new Font("Arial", Font.BOLD, 35)); // كبرنا الخط
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // مسافات
        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(30));

        // --- خانة اللاعب الأول ---
        add(createLabel("Player 1 Name:"));
        add(Box.createVerticalStrut(10)); // مسافة صغيرة بين العنوان والخانة
        p1NameField = createTextField();
        add(p1NameField);
        add(Box.createVerticalStrut(20)); // مسافة بين اللاعبين

        // --- خانة اللاعب الثاني ---
        if (isMultiplayer) {
            add(createLabel("Player 2 Name:"));
            add(Box.createVerticalStrut(10));
            p2NameField = createTextField();
            add(p2NameField);
        } else {
            // لو سينجل، نضيف مسافة فاضية عشان الشكل يفضل متناسق
            add(Box.createVerticalStrut(80));
        }

        add(Box.createVerticalStrut(30));

        // --- زر المتابعة (NEXT) ---
        JButton nextBtn = new JButton("NEXT");
        // تأكد من وجود الصور دي أو استخدم ستايل عادي لو مش موجودة
        styleButtonWithImage(nextBtn, "Assets/btn.png", "Assets/btn2.png");

        // محاذاة الزر في النص
        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextBtn.addActionListener(e -> {
            String p1Name = p1NameField.getText().trim();
            String p2Name = isMultiplayer ? p2NameField.getText().trim() : "Computer";

            if (p1Name.isEmpty()) p1Name = "Player 1";
            if (isMultiplayer && p2Name.isEmpty()) p2Name = "Player 2";

            dispose();
            new CharacterSelectionMenu(isMultiplayer, menuMusic, p1Name, p2Name);
        });

        add(nextBtn);
        setVisible(true);
    }

    // دالة تصميم التكست فيلد الجديد
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        // تكبير حجم الخانة
        Dimension size = new Dimension(350, 50);
        tf.setPreferredSize(size);
        tf.setMaximumSize(size);

        // تنسيق الخط واللون
        tf.setFont(new Font("Arial", Font.BOLD, 20));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBackground(Color.WHITE); // لون أبيض صريح (أنظف)
        tf.setForeground(Color.DARK_GRAY);
        tf.setCaretColor(Color.BLACK); // لون مؤشر الكتابة

        // أهم سطر عشان يظبط في النص مع BoxLayout
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);

        // عمل برواز (Border) شكله نظيف ومسافة داخلية للكلام
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 2), // برواز رمادي
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // حشوة داخلية
        ));

        return tf;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 22)); // خط أوضح
        lbl.setForeground(new Color(0, 255, 255)); // سماوي
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        // إضافة ظل أسود خفيف للكلام عشان يظهر أكتر (اختياري)
        // lbl.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        return lbl;
    }

    // دالة الزرار (زي ما هي عندك)
    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        int width = 300; // صغرنا الزرار شوية عشان يتناسب مع الشاشة
        int height = 60;

        ImageIcon iconNormal = new ImageIcon(normalPath);
        Image imgNormal = iconNormal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(imgNormal));

        ImageIcon iconPressed = new ImageIcon(pressedPath);
        Image imgPressed = iconPressed.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setPressedIcon(new ImageIcon(imgPressed));

        b.setFont(new Font("Arial", Font.BOLD, 24));
        b.setForeground(Color.WHITE);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);

        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
        b.setPreferredSize(new Dimension(width, height));
        b.setMaximumSize(new Dimension(width, height)); // مهم للـ BoxLayout
    }
}