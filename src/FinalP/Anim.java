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
    JPanel gameOverMenuPanel;
    JLabel gameOverLabel;
    AudioPlayer myMusic;

    final int MENU_WIDTH = 300;
    final int MENU_HEIGHT = 320;

    public static void main(String[] args) {
        new MainMenu();
    }

    public Anim(boolean isMultiplayer, int p1, int p2, String difficulty, String p1Name, String p2Name, boolean startMuted) {
        if (isMultiplayer) {
            AnimGLEventListener2 listener = new AnimGLEventListener2();
            listener.setPlayerChoices(p1, p2);
            listener.setPlayerNames(p1Name, p2Name);
            setupGame(listener, "Multiplayer", startMuted);
        } else {
            AnimGLEventListener3 listener = new AnimGLEventListener3();
            listener.setCharacters(p1, p2);
            listener.setDifficulty(difficulty);
            listener.setPlayerNames(p1Name, "CPU");
            setupGame(listener, "Single Player", startMuted);
        }
    }

    private void setupGame(AnimListener gameListener, String title, boolean startMuted) {
        this.listener = gameListener;

        myMusic = new AudioPlayer("Assets/game.wav");
        if (startMuted) {
            myMusic.toggleMute();
        }
        myMusic.playMusic();
        listener.setAudioPlayer(myMusic);

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


        pauseMenuPanel = createPauseMenu(glcanvas);
        listener.setPausePanel(pauseMenuPanel);

        createGameOverMenu();
        listener.setGameOverPanel(gameOverMenuPanel, gameOverLabel);

        JLayeredPane layeredPane = new JLayeredPane();
        glcanvas.setBounds(0, 0, 700, 700);

        pauseMenuPanel.setBounds(200, 200, MENU_WIDTH, MENU_HEIGHT);
        gameOverMenuPanel.setBounds(200, 200, MENU_WIDTH, MENU_HEIGHT);

        layeredPane.add(glcanvas, Integer.valueOf(0));
        layeredPane.add(pauseMenuPanel, Integer.valueOf(1));
        layeredPane.add(gameOverMenuPanel, Integer.valueOf(2));

        setContentPane(layeredPane);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getContentPane().getWidth();
                int height = getContentPane().getHeight();

                glcanvas.setBounds(0, 0, width, height);

                int menuX = (width - MENU_WIDTH) / 2;
                int menuY = (height - MENU_HEIGHT) / 2;

                pauseMenuPanel.setBounds(menuX, menuY, MENU_WIDTH, MENU_HEIGHT);
                gameOverMenuPanel.setBounds(menuX, menuY, MENU_WIDTH, MENU_HEIGHT);
            }
        });

        setVisible(true);
        glcanvas.requestFocusInWindow();
    }

    private JPanel createPauseMenu(GLCanvas canvas) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4, 1, 0, 10));
        p.setBackground(new Color(28, 28, 28, 255));
        p.setVisible(false);

        JButton btnResume = new JButton("RESUME");
        JButton btnRematch = new JButton("REMATCH");
        JButton btnControls = new JButton("CONTROLS");
        JButton btnMenu = new JButton("MAIN MENU");

        styleButtonWithImage(btnResume, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnRematch, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnControls, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnMenu, "Assets/btn.png", "Assets/btn2.png");

        btnResume.addActionListener(e -> {
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        btnRematch.addActionListener(e -> {
            listener.resetGame();
            p.setVisible(false);
            listener.isPaused = false;
            canvas.requestFocusInWindow();
        });

        btnControls.addActionListener(e -> {
            new ControlsMenu(true, myMusic);
        });

        btnMenu.addActionListener(e -> {
            boolean wasMuted = (myMusic != null && myMusic.isMuted());
            myMusic.stop();
            dispose();
            new MainMenu(wasMuted);
        });

        p.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        p.add(btnResume);
        p.add(btnRematch);
        p.add(btnControls);
        p.add(btnMenu);

        return p;
    }

    private void createGameOverMenu() {
        gameOverMenuPanel = new JPanel();
        gameOverMenuPanel.setLayout(new GridLayout(3, 1, 0, 15));
        gameOverMenuPanel.setBackground(new Color(28, 28, 28, 255));;
        gameOverMenuPanel.setVisible(false);

        gameOverLabel = new JLabel("GAME OVER", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gameOverLabel.setForeground(Color.YELLOW);


        JButton btnPlayAgain = new JButton("PLAY AGAIN");
        JButton btnMainMenu = new JButton("MAIN MENU");

        styleButtonWithImage(btnPlayAgain, "Assets/btn.png", "Assets/btn2.png");
        styleButtonWithImage(btnMainMenu, "Assets/btn.png", "Assets/btn2.png");


        btnPlayAgain.addActionListener(e -> {
            listener.resetGame();
            gameOverMenuPanel.setVisible(false);
        });

        btnMainMenu.addActionListener(e -> {
            boolean wasMuted = (myMusic != null && myMusic.isMuted());
            myMusic.stop();
            dispose();
            new MainMenu(wasMuted);
        });

        gameOverMenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        gameOverMenuPanel.add(gameOverLabel);
        gameOverMenuPanel.add(btnPlayAgain);
        gameOverMenuPanel.add(btnMainMenu);
    }

    private void styleButtonWithImage(JButton b, String normalPath, String pressedPath) {
        int width = 240;
        int height = 55;
        ImageIcon iconNormal = new ImageIcon(normalPath);
        Image imgNormal = iconNormal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setIcon(new ImageIcon(imgNormal));
        ImageIcon iconPressed = new ImageIcon(pressedPath);
        Image imgPressed = iconPressed.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        b.setPressedIcon(new ImageIcon(imgPressed));
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setForeground(Color.WHITE);
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.CENTER);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(false);
    }
}