import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameFrame extends JFrame{
    static SourcePanel panel;
    GameFrame() {
        panel = new SourcePanel(this, 40, 450, 350, 14, 18, 2);
        ImageIcon titleGif = new ImageIcon("images/text.gif");
        JLabel title = new JLabel();
        title.setIcon(titleGif);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(0, 0, 800, 100);

        ImageIcon logo = new ImageIcon("images/flagged.png");
        setIconImage(logo.getImage());
        setTitle("Minesweeper");
        getContentPane().setBackground(Color.gray);
        setSize(800, 750);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            new AudioEffects("sounds/start.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        add(title);
        add(panel);

        setVisible(true);
    }
}
