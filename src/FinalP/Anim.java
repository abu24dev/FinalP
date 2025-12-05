package FinalP;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    // =================================================================
    // 1. دالة Main: دي المسؤولة عن القائمة الرئيسية (Menu)
    // =================================================================
    public static void main(String[] args) {

        JFrame frame = new JFrame("Fighting Game Menu");

        // إعداد خلفية القائمة
        JPanel backgroundPanel = new JPanel() {
            // تأكد إن الصورة 5.png موجودة جنب ملفات المشروع
            Image bg = new ImageIcon("5.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // رسم الخلفية لتملأ الشاشة
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new GridLayout(5, 1, 10, 10));

        // تعريف أزرار القائمة
        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn  = new JButton("Multiplayer");
        JButton highBtn   = new JButton("HIGH SCORES");
        JButton exitBtn   = new JButton("Exit");

        // تنسيق الأزرار
        Color btnColor = new Color(0, 0, 0, 180); // أسود شفاف

        styleMenuButton(singleBtn, btnColor);
        styleMenuButton(multiBtn, btnColor);
        styleMenuButton(highBtn, btnColor);

        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 18));

        // إضافة العناصر للوحة
        backgroundPanel.add(makeTitle());
        backgroundPanel.add(singleBtn);
        backgroundPanel.add(multiBtn);
        backgroundPanel.add(highBtn);
        backgroundPanel.add(exitBtn);

        frame.setContentPane(backgroundPanel);

        // =============================================================
        // الأكشن للأزرار (هنا بننادي على اللعبة الجديدة)
        // =============================================================

        // زرار Single Player
        singleBtn.addActionListener(e -> {
            frame.dispose(); // نقفل القائمة
            new Anim();      // نفتح اللعبة (سنجل)
        });

        // زرار Multiplayer
        multiBtn.addActionListener(e -> {
            frame.dispose(); // نقفل القائمة
            new Anim("Multiplayer"); // نفتح اللعبة (مالتي)
        });

        // زرار High Scores (لسه ملوش برمجة)
        highBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "High Scores Coming Soon!");
            // لو عايز ترجعه للقائمة تاني:
            // new Anim(); // غلط هنا، المفروض تفتح شاشة سكور
        });

        // زرار Exit
        exitBtn.addActionListener(e -> System.exit(0));

        // إعدادات نافذة القائمة
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // دالة مساعدة لتنسيق زراير القائمة
    private static void styleMenuButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);
    }

    private static JLabel makeTitle() {
        JLabel l = new JLabel("FIGHTING GAME", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 36));
        l.setForeground(Color.WHITE); // لون الخط أبيض عشان يبان على الخلفية
        return l;
    }

    // =================================================================
    // 2. الجزء الخاص بتشغيل اللعبة نفسها (Constructors)
    // =================================================================

    // كونسركتور السنجل بلاير
    public Anim() {
        setupGame(false); // false = single
    }

    // كونستركتور المالتي بلاير
    public Anim(String mltp) {
        setupGame(true); // true = multi
    }

    // الدالة الموحدة اللي بتشغل اللعبة والصوت والماوس
    private void setupGame(boolean isMultiplayer) {
        // 1. تشغيل الصوت (تأكد من وجود game.wav في Assets)
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();

        // 2. اختيار الليسنر المناسب
        AnimListener listener;
        if (isMultiplayer) {
            listener = new AnimGLEventListener2(); // كلاس المالتي
        } else {
            listener = new AnimGLEventListener3(); // كلاس السنجل
        }

        // 3. تسليم "العهدة" (الصوت) لليسنر عشان الزراير تتحكم فيه
        listener.setAudioPlayer(myMusic);

        // 4. إعداد الـ Canvas
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);

        // >>> هام جداً: تفعيل الماوس عشان زراير الصوت والـ Pause تشتغل <<<
        glcanvas.addMouseListener(listener);

        // 5. إعداد الأنيميتور
        Animator animator = new FPSAnimator(glcanvas, 25);
        animator.start();

        // 6. إعداد نافذة اللعب
        setTitle("Fighting Game - " + (isMultiplayer ? "Multiplayer" : "Single Player"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // إضافة الكانفاس للشاشة
        getContentPane().add(glcanvas, BorderLayout.CENTER);

        setVisible(true);

        // التركيز على اللعبة عشان الكيبورد يشتغل علطول
        glcanvas.requestFocusInWindow();
    }
}