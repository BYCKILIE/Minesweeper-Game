import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Board extends JPanel implements MouseListener {
    private final JButton[][] button;
    private final int index;
    private final int h;
    private final int w;
    private final Gameplay gameplay;
    private final GameTimer timer;
    private final Score score;
    private final Reset reset;

    public Board(int index, int nrBombs, int width, int height, int h, int w,
                 GameTimer timer, Score score, Reset reset) {
        this.score = score;
        this.timer = timer;
        this.reset = reset;

        gameplay = new Gameplay(nrBombs, h, w);
        button = new JButton[h][w];

        this.index = index;
        this.h = h;
        this.w = w;

        int start = (800 - (width - w)) / 2;
        setBounds(start, 150 + h, width - w, height - h);
        setLayout(new FlowLayout(FlowLayout.CENTER, -1, -1));
        setBackground(Color.gray);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                button[i][j] = new JButton();
                button[i][j].setFocusable(false);
                button[i][j].addMouseListener(this);
                button[i][j].setPreferredSize(new Dimension(25, 25));
                gameplay.initButtons(button[i][j]);
                add(button[i][j]);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    private boolean flag = true;

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == button[i][j]) {
                    if (gameplay.verify(i, j))
                        reset.setWowState();
                    if (!flag && gameplay.knowledgeCondition(i, j)) {
                        gameplay.preKnowledgeExecute(i, j, button);
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (e.getButton() == MouseEvent.BUTTON1 & e.getSource() == button[i][j]) {
                    reset.setProcessState();
                    if (flag) {
                        gameplay.generateColsRows(i, j);
                        gameplay.create();
                        gameplay.discover(i, j, button);
                        timer.startTimer();
                        flag = false;
                    } else {
                        if (gameplay.verify(i, j)) {
                            if (gameplay.knowledgeCondition(i, j)) {
                                gameplay.preKnowledgeRestore(i, j, button);
                                try {
                                    if (gameplay.knowledge(i, j, button, this)) {
                                        reset.setLoseState();
                                        timer.stopTimer();
                                    }
                                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            } else if (gameplay.discover(i, j, button) == -1) {
                                gameplay.gameOver(i, j, button, this);
                                reset.setLoseState();
                                timer.stopTimer();
                                try {
                                    new AudioEffects("sounds/lose_minesweeper.wav");
                                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                            if (gameplay.win()) {
                                gameplay.winBoard(button, this);
                                reset.setWinState();
                                timer.setNewBestTime(index);
                                timer.stopTimer();
                                try {
                                    new AudioEffects("sounds/win.wav");
                                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                }
                if (e.getButton() == MouseEvent.BUTTON3 & e.getSource() == button[i][j] && !flag) {
                    int m = gameplay.mark(i, j, button);
                    if (m == 1) {
                        score.incrementBombs();
                    }
                    if (m == -1) {
                        try {
                            new AudioEffects("sounds/click.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }
                        score.decrementBombs();
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
