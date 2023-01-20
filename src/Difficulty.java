import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Difficulty extends JPanel implements ActionListener {
    private final JRadioButton hard = new JRadioButton("HARD");
    private final JRadioButton medium = new JRadioButton("MEDIUM");
    private final JRadioButton easy = new JRadioButton("EASY");
    private final GameFrame frame;
    private SourcePanel panel;
    Difficulty(GameFrame frame, SourcePanel panel, int index) {
        this.frame = frame;
        this.panel = panel;

        setBounds(0, 100, 800, 50);
        setBackground(Color.gray);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        ButtonGroup group = new ButtonGroup();
        group.add(hard);
        group.add(medium);
        group.add(easy);

        if (index == 1) {
            easy.setSelected(true);
        }
        if (index == 2) {
            medium.setSelected(true);
        }
        if (index == 3) {
            hard.setSelected(true);
        }

        hard.setFocusable(false);
        medium.setFocusable(false);
        easy.setFocusable(false);

        hard.setPreferredSize(new Dimension(100, 40));
        medium.setPreferredSize(new Dimension(100, 40));
        easy.setPreferredSize(new Dimension(100, 40));

        hard.addActionListener(this);
        medium.addActionListener(this);
        easy.addActionListener(this);

        hard.setBackground(Color.gray);
        medium.setBackground(Color.gray);
        easy.setBackground(Color.gray);

        add(hard);
        add(medium);
        add(easy);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hard) {
            GameFrame.panel.setVisible(false);
            panel.setVisible(false);
            panel = new SourcePanel(frame, 99, 600, 500, 20, 24, 3);
            panel.setVisible(true);
            frame.add(panel);
        }
        if (e.getSource() == medium) {
            GameFrame.panel.setVisible(false);
            panel.setVisible(false);
            panel = new SourcePanel(frame, 40, 450, 350, 14, 18, 2);
            panel.setVisible(true);
            frame.add(panel);
        }
        if (e.getSource() == easy) {
            GameFrame.panel.setVisible(false);
            panel.setVisible(false);
            panel = new SourcePanel(frame, 10, 250, 200, 8, 10, 1);
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
