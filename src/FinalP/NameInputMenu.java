package FinalP;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class NameInputMenu extends JFrame {

    private boolean isMultiplayer;
    private AudioPlayer menuMusic;
    private JTextField p1NameField;
    private JTextField p2NameField;

    // متغير لحفظ الصعوبة المستلمة من الشاشة السابقة
    private String selectedDifficulty;

    // تم تعديل الكونستركتور لاستقبال الصعوبة (String difficulty)
    public NameInputMenu(boolean isMultiplayer, AudioPlayer music, String difficulty) {
        this.isMultiplayer = isMultiplayer;
        this.menuMusic = music;
        this.selectedDifficulty = difficulty; // حفظ القيمة لتمريرها لاحقاً

        setTitle("Enter Player Names");
        // نفس الأبعاد التي ضبطناها سابقاً
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // خلفية النافذة (نفس الكود القديم)
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Background.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                // طبقة سوداء لتوضيح الكلام
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // --- العنوان ---
        JLabel title = new JLabel("ENTER NAMES");
        title.setFont(new Font("Arial", Font.BOLD, 35));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(30));
        add(title);
        add(Box.createVerticalStrut(30));

        // --- خانة اللاعب الأول ---
        add(createLabel("Player 1 Name:"));
        add(Box.createVerticalStrut(10));
        p1NameField = createTextField();
        add(p1NameField);
        add(Box.createVerticalStrut(20));

        // --- خانة اللاعب الثاني ---
        if (isMultiplayer) {
            add(createLabel("Player 2 Name:"));
            add(Box.createVerticalStrut(10));
            p2NameField = createTextField();
            add(p2NameField);
        } else {
            // مسافة لظبط الشكل في السنجل
            add(Box.createVerticalStrut(80));
        }

        add(Box.createVerticalStrut(30));

        // --- زر المتابعة (NEXT) ---
        JButton nextBtn = new JButton("NEXT");
        // الحفاظ على ستايل الصور للأزرار
        styleButtonWithImage(nextBtn, "Assets/btn.png", "Assets/btn2.png");

        nextBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        nextBtn.addActionListener(e -> {
            String p1Name = p1NameField.getText().trim();
            String p2Name = isMultiplayer ? p2NameField.getText().trim() : "Computer";

            if (p1Name.isEmpty()) p1Name = "Player 1";
            if (isMultiplayer && p2Name.isEmpty()) p2Name = "Player 2";

            dispose();

            // --- التعديل هنا: تمرير الصعوبة للشاشة التالية (CharacterSelectionMenu) ---
            new CharacterSelectionMenu(isMultiplayer, menuMusic, p1Name, p2Name, selectedDifficulty);
        });

        add(nextBtn);
        setVisible(true);
    }

    // نفس دالة تصميم التكست فيلد (ستايل حديث)
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        Dimension size = new Dimension(350, 50);
        tf.setPreferredSize(size);
        tf.setMaximumSize(size);

        tf.setFont(new Font("Arial", Font.BOLD, 20));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.DARK_GRAY);
        tf.setCaretColor(Color.BLACK);

        tf.setAlignmentX(Component.CENTER_ALIGNMENT);

        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return tf;
    }

    // نفس دالة الليبل
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        lbl.setForeground(new Color(0, 255, 255));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    // نفس دالة ستايل الأزرار بالصور
    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        int width = 300;
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
        b.setMaximumSize(new Dimension(width, height));
    }
}