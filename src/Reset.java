import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Reset extends JButton implements ActionListener {
    private final GameFrame frame;
    private SourcePanel panel;
    private final int index;
    Reset(GameFrame frame, SourcePanel panel, int index) {
        this.frame = frame;
        this.panel = panel;
        this.index = index;
        setBounds(640, 85, 70, 70);
        setFocusable(false);
        addActionListener(this);
        setProcessState();
    }

    void setProcessState() {
        ImageIcon image = new ImageIcon("images/process_face.png");
        Icon resizedImage = resizeIcon(image);
        setIcon(resizedImage);
    }

    void setWinState() {
        ImageIcon image = new ImageIcon("images/win_face.png");
        Icon resizedImage = resizeIcon(image);
        setIcon(resizedImage);
    }

    void setLoseState() {
        ImageIcon image = new ImageIcon("images/lose_face.png");
        Icon resizedImage = resizeIcon(image);
        setIcon(resizedImage);
    }

    void setWowState() {
        ImageIcon image = new ImageIcon("images/wow_face.png");
        Icon resizedImage = resizeIcon(image);
        setIcon(resizedImage);
    }
    private static Icon resizeIcon(ImageIcon icon) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(70, 70,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this) {
            GameFrame.panel.setVisible(false);
            panel.setVisible(false);
            if (index == 3) {
                panel = new SourcePanel(frame, 99, 600, 500, 20, 24, 3);
            }
            if (index == 2) {
                panel = new SourcePanel(frame, 40, 450, 350, 14, 18, 2);
            }
            if (index == 1) {
                panel = new SourcePanel(frame, 10, 250, 200, 8, 10, 1);
            }
            panel.setVisible(true);
            frame.add(panel);
        }
        try {
            new AudioEffects("sounds/start.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
    }
}
