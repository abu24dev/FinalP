package FinalP;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    public static void main(String[] args) {
        new MainMenu();
    }

    public Anim() {
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();
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

        glcanvas.requestFocusInWindow();
    }

    public Anim(String mltp) {
        AudioPlayer myMusic = new AudioPlayer("Assets/game.wav");
        myMusic.playMusic();
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
}