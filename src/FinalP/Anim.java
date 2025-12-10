package FinalP;

import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    AnimListener listener;
    JPanel pauseMenuPanel;
    AudioPlayer myMusic;

    // أبعاد قائمة التوقف (صغيرة)
    final int MENU_WIDTH = 300;
    final int MENU_HEIGHT = 250;

    public static void main(String[] args) {
        new MainMenu();
    }

    // الكونستركتور المعدل (يستقبل الصعوبة)
    public Anim(boolean isMultiplayer, int p1, int p2, String difficulty) {
        if (isMultiplayer) {
            // --- وضع Multiplayer ---
            AnimGLEventListener2 listener = new AnimGLEventListener2();
            listener.setPlayerChoices(p1, p2);
            setupGame(listener, "Multiplayer");
        } else {
            // --- وضع Single Player ---
            AnimGLEventListener3 listener = new AnimGLEventListener3();
            listener.setCharacters(p1, p2);
            listener.setDifficulty(difficulty); // تمرير الصعوبة
            setupGame(listener, "Single Player");
        }
    }

    // دالة تجهيز اللعبة
    private void setupGame(AnimListener gameListener, String title) {
        this.listener = gameListener;

        // تشغيل الموسيقى
        myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();
        listener.setAudioPlayer(myMusic);

        // إعداد الكانفاس
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);

        // إعداد الأنيميتور
        Animator animator = new FPSAnimator(glcanvas, 24);
        animator.start();

        // إعداد النافذة
        setTitle("Fighting Game - " + title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // إنشاء قائمة التوقف
        pauseMenuPanel = createPauseMenu(glcanvas);
        listener.setPausePanel(pauseMenuPanel);

        // استخدام LayeredPane لوضع القائمة فوق اللعبة
        JLayeredPane layeredPane = new JLayeredPane();
        glcanvas.setBounds(0, 0, 700, 700);
        pauseMenuPanel.setBounds(200, 200, 300, 250);

        layeredPane.add(glcanvas, Integer.valueOf(0)); // اللعبة تحت
        layeredPane.add(pauseMenuPanel, Integer.valueOf(1)); // القائمة فوق

        setContentPane(layeredPane);

        // Listener لضبط الأحجام عند تكبير/تصغير النافذة
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getContentPane().getWidth();
                int height = getContentPane().getHeight();

                glcanvas.setBounds(0, 0, width, height);

                int menuX = (width - MENU_WIDTH) / 2;
                int menuY = (height - MENU_HEIGHT) / 2;

                pauseMenuPanel.setBounds(menuX, menuY, MENU_WIDTH, MENU_HEIGHT);
            }
        });

        setVisible(true);
        glcanvas.requestFocusInWindow();
    }

    // --- دالة إنشاء قائمة التوقف ---
    private JPanel createPauseMenu(GLCanvas canvas) {
        JPanel p = new JPanel();
        // GridLayout: 3 صفوف، عمود واحد، مسافات (0 أفقي، 15 رأسي)
        p.setLayout(new GridLayout(3, 1, 0, 15));

        // لون الخلفية أسود نصف شفاف
        p.setBackground(new Color(0, 0, 0, 255));
        p.setVisible(false);

        JButton btnResume = new JButton("RESUME");
        JButton btnRematch = new JButton("REMATCH");
        JButton btnMenu = new JButton("MAIN MENU");

        // --- التعديل هنا: تطبيق ستايل الصور ---
        styleButtonWithImage(btnResume, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnRematch, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnMenu, "Assets/btn.png", "Assets/btn2.png");

        // --- الأكشنز ---

        // 1. Resume
        btnResume.addActionListener(e -> {
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        // 2. Rematch
        btnRematch.addActionListener(e -> {
            listener.resetGame();
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        // 3. Main Menu
        btnMenu.addActionListener(e -> {
            myMusic.stop();
            dispose();
            new MainMenu(); // العودة للقائمة الرئيسية
        });

        // إضافة هوامش للبانل عشان الزراير متلزقش في الحواف
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        p.add(btnResume);
        p.add(btnRematch);
        p.add(btnMenu);

        return p;
    }

    // --- دالة تنسيق الأزرار بالصور (مخصصة للحجم الصغير) ---
    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        // حجم الزرار هنا (240x55) مناسب لحجم القائمة (300x250)
        int width = 240;
        int height = 55;

        // تحميل وتغيير حجم الصورة العادية
        ImageIcon iconNormal = new ImageIcon(normalPath);
        Image imgNormal = iconNormal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(imgNormal));

        // تحميل وتغيير حجم صورة الضغط
        ImageIcon iconPressed = new ImageIcon(pressedPath);
        Image imgPressed = iconPressed.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setPressedIcon(new ImageIcon(imgPressed));

        // تنسيق النص
        b.setFont(new Font("Arial", Font.BOLD, 20)); // خط أصغر قليلاً (20)
        b.setForeground(Color.WHITE);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);

        // إزالة الحدود والخلفيات الافتراضية
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);

        // ملاحظة: GridLayout سيجبر الأزرار على ملء الخانة، لكن الصور ستظهر بالحجم المحدد
    }
}