package FinalP;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Fighting Game Menu");

        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon("Summer4.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn  = new JButton("Multiplayer");
        JButton highBtn   = new JButton("HIGH SCORES");
        JButton exitBtn   = new JButton("Exit");
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        backgroundPanel.add(Box.createVerticalStrut(40));
        backgroundPanel.add(makeCenteredPanel(makeTitle()));
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(makeCenteredPanel(singleBtn));
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(makeCenteredPanel(multiBtn));
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(makeCenteredPanel(highBtn));
        backgroundPanel.add(Box.createVerticalStrut(10));
        backgroundPanel.add(makeCenteredPanel(exitBtn));




        Color btnColor = new Color(126, 122, 122, 255); // أسود شفاف

        styleMenuButton(singleBtn, btnColor);
        styleMenuButton(multiBtn, btnColor);
        styleMenuButton(highBtn, btnColor);

        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 18));




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
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void styleMenuButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);


        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
    }


    private static JLabel makeTitle() {
        JLabel l = new JLabel("FIGHTING GAME", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 36));
        l.setForeground(Color.WHITE);
        return l;
    }
    private static JPanel makeCenteredPanel(JComponent comp) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER));

        comp.setPreferredSize(new Dimension(300, 70));

        p.add(comp);
        return p;
    }



    // =================================================================
    // (Constructors)
    // =================================================================

    public Anim() {
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();

//        AnimListener listener;
//        if (isMultiplayer) {
//            listener = new AnimGLEventListener2(); // كلاس المالتي
//        } else {
//            listener = new AnimGLEventListener3(); // كلاس السنجل
//        }
        AnimGLEventListener3 listener = new AnimGLEventListener3();

        listener.setAudioPlayer(myMusic);

        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);

        glcanvas.addMouseListener(listener);

        Animator animator = new FPSAnimator(glcanvas, 24);
        animator.start();

        setTitle("Fighting Game - " +  "Single Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        getContentPane().add(glcanvas, BorderLayout.CENTER);

        setVisible(true);

        glcanvas.requestFocusInWindow(); // false = single
    }

    public Anim(String mltp) {
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();

//        AnimListener listener;
//        if (isMultiplayer) {
//            listener = new AnimGLEventListener2(); // كلاس المالتي
//        } else {
//            listener = new AnimGLEventListener3(); // كلاس السنجل
//        }
        AnimGLEventListener2 listener = new AnimGLEventListener2();
        listener.setAudioPlayer(myMusic);
        GLCanvas glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);

        glcanvas.addMouseListener(listener);

        Animator animator = new FPSAnimator(glcanvas, 24);
        animator.start();

        setTitle("Fighting Game - " +  "Multiplayer" );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        getContentPane().add(glcanvas, BorderLayout.CENTER);

        setVisible(true);

        glcanvas.requestFocusInWindow();
    } // true = multi


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
}