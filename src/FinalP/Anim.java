package FinalP;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;


import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {


    public static void main(String[] args) {

        JFrame frame = new JFrame("Anim");

        // panel الخلفية
        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon("5.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn  = new JButton("Multiplayer");
        JButton highBtn   = new JButton("HIGH SCORES");
        JButton exitBtn   = new JButton("Exit");


        // Buttons Style
        Color btnColor = new Color(0, 0, 0, 180);
        singleBtn.setBackground(btnColor);
        singleBtn.setForeground(Color.WHITE);

        // تنسيق الأزرار
        Color btnColor = new Color(0, 0, 0, 180); // اسود شبه شفاف
        singleBtn.setBackground(btnColor);
        singleBtn.setForeground(Color.WHITE);

<<<<<<< HEAD

        styleMenuButton(singleBtn, btnColor);
        styleMenuButton(multiBtn, btnColor);
        styleMenuButton(highBtn, btnColor);
=======
        multiBtn.setBackground(btnColor);
        multiBtn.setForeground(Color.WHITE);

        highBtn.setBackground(btnColor);
        highBtn.setForeground(Color.WHITE);
>>>>>>> parent of 5db3fce (music)

        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);

        backgroundPanel.add(makeTitle());
        backgroundPanel.add(singleBtn);
        backgroundPanel.add(multiBtn);
        backgroundPanel.add(highBtn);
        backgroundPanel.add(exitBtn);

        frame.setContentPane(backgroundPanel);

<<<<<<< HEAD


        // =============================================================
        // الأكشن للأزرار (هنا بننادي على اللعبة الجديدة)
        // =============================================================

        // زرار Single Player

=======
        // باقي الأكشن
>>>>>>> parent of 5db3fce (music)
        singleBtn.addActionListener(e -> {
            frame.dispose();
            new Anim();
        });

        multiBtn.addActionListener(e -> {
            frame.dispose();
            new Anim("Multiplayer");
        });

        highBtn.addActionListener(e -> {
            frame.dispose();
            new Anim();
        });

        exitBtn.addActionListener(e -> System.exit(0));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

<<<<<<< HEAD



    public Anim() {
        GLCanvas glcanvas;
        Animator animator;

        AnimListener listener = new AnimGLEventListener3();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(24);
        animator.add(glcanvas);
        animator.start();

        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();

    // دالة مساعدة لتنسيق زراير القائمة
    private static void styleMenuButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);

    }
=======
>>>>>>> parent of 5db3fce (music)


    public Anim() {
        GLCanvas glcanvas;
        Animator animator;

        AnimListener listener = new AnimGLEventListener3();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(25);
        animator.add(glcanvas);
        animator.start();

        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
    public Anim(String mltp) {
        GLCanvas glcanvas;
        Animator animator;

        AnimListener listener = new AnimGLEventListener2();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(25);
        animator.add(glcanvas);
        animator.start();

        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();


    }
    private static JLabel makeTitle() {
        JLabel l = new JLabel("FIGHTING GAME", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, 26));
        return l;
    }

}
