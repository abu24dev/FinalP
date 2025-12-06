package FinalP;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Fighting Game - Main Menu");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel الخلفية الأساسية
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // إضافة صورة خلفية
        setContentPane(new JPanel() {
            Image bg = new ImageIcon("Summer4.png").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        });
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Buttons
        JButton newGameBtn = new JButton("New Game");
        JButton highScoresBtn = new JButton("High Scores");
        JButton exitBtn = new JButton("Exit");


        styleButton(newGameBtn);
        styleButton(highScoresBtn);
//        styleButton(exitBtn);
        exitBtn.setFont(new Font("Arial", Font.BOLD, 24));
        exitBtn.setPreferredSize(new Dimension(300, 60));
        exitBtn.setBackground(Color.RED);
        exitBtn.setForeground(Color.WHITE);

        add(Box.createVerticalStrut(80));
        add(center(newGameBtn));
        add(Box.createVerticalStrut(20));
        add(center(highScoresBtn));
        add(Box.createVerticalStrut(20));
        add(center(exitBtn));

        // Actions
        newGameBtn.addActionListener(e -> {
            dispose();
            new GameModeMenu(); // فتح القائمة التالية
        });

        highScoresBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "High Scores Coming Soon!");
        });

        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Arial", Font.BOLD, 26));
        b.setPreferredSize(new Dimension(350, 70));
        b.setFocusPainted(false);
        b.setBackground( new Color(126, 122, 122, 255));
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
    }

    private JPanel center(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setOpaque(false);
        p.add(c);
        return p;
    }

    public static void main(String[] args) {
        new MainMenu();
    }
//    private static JLabel makeTitle() {
//        JLabel l = new JLabel("FIGHTING GAME", SwingConstants.CENTER);
//        l.setFont(new Font("Arial", Font.BOLD, 36));
//        l.setForeground(Color.WHITE);
//        return l;
//    }
}
