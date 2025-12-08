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
    final int MENU_WIDTH = 300;
    final int MENU_HEIGHT = 250;

    public static void main(String[] args) {
        new MainMenu();
    }

    // =============================================================
    // ده الكونستركتور الوحيد اللي محتاجينه دلوقتي
    // بيستقبل نوع اللعبة + ارقام الشخصيات المختارة
    // =============================================================
    public Anim(boolean isMultiplayer, int p1, int p2) {
        if (isMultiplayer) {
            // --- وضع Multiplayer ---
            AnimGLEventListener2 listener = new AnimGLEventListener2();
            listener.setPlayerChoices(p1, p2); // بنبعت الشخصيات لليسنر

            setupGame(listener, "Multiplayer");
        } else {
            // --- وضع Single Player ---
            AnimGLEventListener3 listener = new AnimGLEventListener3();
            listener.setCharacters(p1, p2); // بنبعت الشخصيات لليسنر

            setupGame(listener, "Single Player");
        }
    }

    // دالة تجهيز اللعبة (مشتركة للنوعين)
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
        Animator animator = new FPSAnimator(glcanvas, 24); // 24 FPS
        animator.start();

        // إعداد النافذة
        setTitle("Fighting Game - " + title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // إعداد قائمة التوقف
        pauseMenuPanel = createPauseMenu(glcanvas);
        listener.setPausePanel(pauseMenuPanel);

        // استخدام LayeredPane عشان نحط القائمة فوق اللعبة
        JLayeredPane layeredPane = new JLayeredPane();
        glcanvas.setBounds(0, 0, 700, 700);
        pauseMenuPanel.setBounds(200, 200, 300, 250);

        layeredPane.add(glcanvas, Integer.valueOf(0)); // الطبقة 0 (تحت)
        layeredPane.add(pauseMenuPanel, Integer.valueOf(1)); // الطبقة 1 (فوق)

        setContentPane(layeredPane);

        // Listener عشان لو حجم الشاشة اتغير، نعدل حجم الكانفاس ومكان القائمة
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

    private JPanel createPauseMenu(GLCanvas canvas) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1, 10, 20));
        p.setBackground(new Color(0, 0, 0, 180));
        p.setVisible(false);

        JButton btnResume = new JButton("RESUME");
        JButton btnRematch = new JButton("REMATCH");
        JButton btnMenu = new JButton("MAIN MENU");

        styleButton(btnResume);
        styleButton(btnRematch);
        styleButton(btnMenu);

        // Resume Action
        btnResume.addActionListener(e -> {
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        // Rematch Action
        btnRematch.addActionListener(e -> {
            listener.resetGame();
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        // Main Menu Action
        btnMenu.addActionListener(e -> {
            myMusic.stop();
            dispose();
            new MainMenu();
        });

        p.add(btnResume);
        p.add(btnRematch);
        p.add(btnMenu);

        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        return p;
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 22));
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}