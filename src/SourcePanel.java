import javax.swing.*;
import java.awt.*;

public class SourcePanel extends JPanel {
    SourcePanel(GameFrame frame, int nrBombs, int width, int height, int h, int w, int index) {
        GameTimer timer = new GameTimer();
        Score score = new Score(nrBombs);
        Reset reset = new Reset(frame, this, index);
        setSize(800, 750);
        setBackground(Color.gray);
        setLayout(null);
        add(timer);
        add(score);
        add(new BestTime(index));
        add(reset);
        add(new Difficulty(frame, this, index));

        add(new Board(index, nrBombs, width, height, h, w, timer, score, reset));
    }
}
