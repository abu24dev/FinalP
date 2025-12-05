package FinalP;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Fighting Game Menu");

        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon("5.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn  = new JButton("Multiplayer");
        JButton highBtn   = new JButton("HIGH SCORES");
        JButton exitBtn   = new JButton("Exit");

        Color btnColor = new Color(0, 0, 0, 180); // أسود شفاف

        styleMenuButton(singleBtn, btnColor);
        styleMenuButton(multiBtn, btnColor);
        styleMenuButton(highBtn, btnColor);

        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 18));


        backgroundPanel.add(makeTitle());
        backgroundPanel.add(singleBtn);
        backgroundPanel.add(multiBtn);
        backgroundPanel.add(highBtn);
        backgroundPanel.add(exitBtn);

        frame.setContentPane(backgroundPanel);


        //  Single Player
        singleBtn.addActionListener(e -> {
            frame.dispose();
            new Anim();
        });

        //  Multiplayer
        multiBtn.addActionListener(e -> {
            frame.dispose();
            new Anim("Multiplayer");
        });

        // زرار High Scores (لسه ملوش برمجة)
        highBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "High Scores Coming Soon!");

        });
        //Exit
        exitBtn.addActionListener(e -> System.exit(0));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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
    // (Constructors)
    // =================================================================

    public Anim() {
        setupGame(false); // false = single
    }

    public Anim(String mltp) {
        setupGame(true); // true = multi
    }

    private void setupGame(boolean isMultiplayer) {
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();

        AnimListener listener;
        if (isMultiplayer) {
            listener = new AnimGLEventListener2(); // كلاس المالتي
        } else {
            listener = new AnimGLEventListener3(); // كلاس السنجل
        }

        listener.setAudioPlayer(myMusic);

        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);

        glcanvas.addMouseListener(listener);

        Animator animator = new FPSAnimator(glcanvas, 24);
        animator.start();

        setTitle("Fighting Game - " + (isMultiplayer ? "Multiplayer" : "Single Player"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        getContentPane().add(glcanvas, BorderLayout.CENTER);

        setVisible(true);

        glcanvas.requestFocusInWindow();
    }
}