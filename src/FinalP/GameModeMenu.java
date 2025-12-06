package FinalP;

import javax.swing.*;
import java.awt.*;

public class GameModeMenu extends JFrame {

    public GameModeMenu() {
        setTitle("Select Game Mode");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Summer4.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SELECT GAME MODE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn = new JButton("Multiplayer");
        JButton backBtn = new JButton("Back");

        styleButton(singleBtn);
        styleButton(multiBtn);

        backBtn.setFont(new Font("Arial", Font.BOLD, 24));
        backBtn.setPreferredSize(new Dimension(300, 60));
        backBtn.setBackground(Color.RED);
        backBtn.setForeground(Color.WHITE);

        add(Box.createVerticalStrut(60));
        add(center(title));
        add(Box.createVerticalStrut(40));
        add(center(singleBtn));
        add(Box.createVerticalStrut(20));
        add(center(multiBtn));
        add(Box.createVerticalStrut(20));
        add(center(backBtn));

        // Actions
        singleBtn.addActionListener(e -> {
            dispose();
            new Anim();
        });

        multiBtn.addActionListener(e -> {
            dispose();
            new Anim("Multiplayer");
        });

        backBtn.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        setVisible(true);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setPreferredSize(new Dimension(350, 70));
        b.setFocusPainted(false);
        b.setBackground(new Color(126, 122, 122, 255));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
    }

    private JPanel center(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);
        p.add(c);
        return p;
    }
}