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

    public Anim() {
        setupGame(new AnimGLEventListener3(), "Single Player");
    }

    public Anim(String mltp) {
        setupGame(new AnimGLEventListener2(), "Multiplayer");
    }

    private void setupGame(AnimListener gameListener, String title) {
        this.listener = gameListener;

        myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();
        listener.setAudioPlayer(myMusic);

        // 1. تعريف الكانفاس هنا عشان نستخدمه
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);

        Animator animator = new FPSAnimator(glcanvas, 24);
        animator.start();

        setTitle("Fighting Game - " + title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // =======================================================
        // 2. بنبعت الـ glcanvas للدالة عشان نعرف نرجعله الـ Focus
        // =======================================================
        pauseMenuPanel = createPauseMenu(glcanvas);

        listener.setPausePanel(pauseMenuPanel);

        JLayeredPane layeredPane = new JLayeredPane();
        glcanvas.setBounds(0, 0, 700, 700);
        pauseMenuPanel.setBounds(200, 200, 300, 250);

        layeredPane.add(glcanvas, Integer.valueOf(0));
        layeredPane.add(pauseMenuPanel, Integer.valueOf(1));

        setContentPane(layeredPane);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // هات العرض والطول الجداد بتوع الشاشة
                int width = getContentPane().getWidth();
                int height = getContentPane().getHeight();

                // 1. خلي اللعبة تاخد المساحة الجديدة كلها
                glcanvas.setBounds(0, 0, width, height);

                // 2. احسب نص الشاشة الجديد عشان تحط القائمة فيه
                int menuX = (width - MENU_WIDTH) / 2;
                int menuY = (height - MENU_HEIGHT) / 2;

                pauseMenuPanel.setBounds(menuX, menuY, MENU_WIDTH, MENU_HEIGHT);
            }
        });

        setVisible(true);
        glcanvas.requestFocusInWindow();
    }

    // غيرنا الدالة عشان تستقبل GLCanvas
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

        // --- التعديل هنا ---

        // 1. Resume
        btnResume.addActionListener(e -> {
            p.setVisible(false);
            listener.isPaused = false;
            // السطر السحري: رجع التركيز للعبة فوراً
            canvas.requestFocusInWindow();
        });

        // 2. Rematch
        btnRematch.addActionListener(e -> {
            listener.resetGame();
            p.setVisible(false);
            listener.isPaused = false;
            // السطر السحري: رجع التركيز للعبة فوراً
            canvas.requestFocusInWindow();
        });

        // 3. Main Menu
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
//    private void setupGameSingle(boolean isMultiplayer) {
//        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
//        myMusic.playMusic();
//
////        AnimListener listener;
////        if (isMultiplayer) {
////            listener = new AnimGLEventListener2(); // كلاس المالتي
////        } else {
////            listener = new AnimGLEventListener3(); // كلاس السنجل
////        }
//        AnimGLEventListener3 listener = new AnimGLEventListener3();
//
//        listener.setAudioPlayer(myMusic);
//
//        GLCanvas glcanvas = new GLCanvas();
//        glcanvas.addGLEventListener(listener);
//        glcanvas.addKeyListener(listener);
//
//        glcanvas.addMouseListener(listener);
//
//        Animator animator = new FPSAnimator(glcanvas, 24);
//        animator.start();
//
//        setTitle("Fighting Game - " + (isMultiplayer ? "Multiplayer" : "Single Player"));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(700, 700);
//        setLocationRelativeTo(null);
//
//        getContentPane().add(glcanvas, BorderLayout.CENTER);
//
//        setVisible(true);
//
//        glcanvas.requestFocusInWindow();
//    }
//    private void setupGameMulti(boolean isMultiplayer) {
//        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
//        myMusic.playMusic();
//
////        AnimListener listener;
////        if (isMultiplayer) {
////            listener = new AnimGLEventListener2(); // كلاس المالتي
////        } else {
////            listener = new AnimGLEventListener3(); // كلاس السنجل
////        }
//        AnimGLEventListener2 listener = new AnimGLEventListener2();
//        listener.setAudioPlayer(myMusic);
//        GLCanvas glcanvas = new GLCanvas();
//        glcanvas.addGLEventListener(listener);
//        glcanvas.addKeyListener(listener);
//
//        glcanvas.addMouseListener(listener);
//
//        Animator animator = new FPSAnimator(glcanvas, 24);
//        animator.start();
//
//        setTitle("Fighting Game - " + (isMultiplayer ? "Multiplayer" : "Single Player"));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(700, 700);
//        setLocationRelativeTo(null);
//
//        getContentPane().add(glcanvas, BorderLayout.CENTER);
//
//        setVisible(true);
//
//        glcanvas.requestFocusInWindow();
//    }
