import javax.swing.*;
import java.awt.*;

public class Score extends JPanel {
    private final JLabel bomb = new JLabel();
    private int nrBombs;
    Score(int nrBombs) {
        this.nrBombs = nrBombs;
        setBounds(50, 80, 80, 60);
        setBackground(Color.gray);
        bomb.setFont(new Font("SansSerif Bold", Font.BOLD, 25));
        bomb.setForeground(Color.black);
        bomb.setText(String.valueOf(nrBombs));
        ImageIcon justBomb = new ImageIcon("images/just_bomb.png");
        bomb.setIcon(justBomb);
        add(bomb);
    }

    void incrementBombs() {
        nrBombs++;
        bomb.setText(String.valueOf(nrBombs));
    }

    void decrementBombs() {
        nrBombs--;
        bomb.setText(String.valueOf(nrBombs));
    }
}
