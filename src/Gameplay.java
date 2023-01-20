import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

public class Gameplay extends Images {
    private final char[][] matrix;
    private final char[][] aux;
    private final int nrBombs;
    private int[] cols;
    private int[] rows;
    private final int h;
    private final int w;
    private static final int[] VH = new int[]{-1, 0, 1, 0, -1, -1, 1, 1};
    private static final int[] VW = new int[]{0, 1, 0, -1, -1, 1, 1, -1};

    public Gameplay(int nrBombs, int h, int w) {
        this.nrBombs = nrBombs;
        this.h = h;
        this.w = w;
        matrix = new char[h][w];
        aux = new char[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                matrix[i][j] = '0';
                aux[i][j] = '?';
            }
        }
    }

    void generateColsRows(int i, int j) {
        rows = new int[nrBombs];
        cols = new int[nrBombs];
        Random rand = new Random();
        outer:
        for (int k = 0; k < nrBombs; k++) {
            rows[k] = rand.nextInt(0, h);
            cols[k] = rand.nextInt(0, w);
            if (rows[k] == i & cols[k] == j) {
                k--;
                continue;
            }
            for (int l = 0; l < 8; l++) {
                if (rows[k] == i + VH[l] & cols[k] == j + VW[l]) {
                    k--;
                    continue outer;
                }
            }
            for (int l = 0; l < k; l++) {
                if (rows[l] == rows[k] & cols[l] == cols[k]) {
                    k--;
                    continue outer;
                }
            }
        }
    }

    void create() {
        for (int i = 0; i < nrBombs; i++) {
            if (matrix[rows[i]][cols[i]] != 'x') {
                matrix[rows[i]][cols[i]] = 'x';
                for (int j = 0; j < 8; j++) {
                    if (rangeFlag(rows[i] + VH[j], cols[i] + VW[j]))
                        if (matrix[rows[i] + VH[j]][cols[i] + VW[j]] != 'x')
                            matrix[rows[i] + VH[j]][cols[i] + VW[j]]++;
                }
            }
        }
    }

    void initButtons(JButton buttons) {
        setImageFacingDown(buttons);
    }

    int discover(int i, int j, JButton[][] buttons) {
        if (!rangeFlag(i, j)) {
            return -2;
        } else if (matrix[i][j] == 'x') {
            return -1;
        } else if (aux[i][j] == '.') {
            return 0;
        } else if (matrix[i][j] == '0') {
            aux[i][j] = '.';
            playerView(i, j, buttons);
            for (int k = 0; k < 8; ++k) {
                discover(i + VH[k], j + VW[k], buttons);
            }
            return 1;
        } else if (aux[i][j] == '!') {
            return 2;
        } else {
            aux[i][j] = '.';
            playerView(i, j, buttons);
            return 3;
        }
    }

    boolean rangeFlag(int i, int j) {
        return !(i < 0 | i > h - 1 | j < 0 | j > w - 1);
    }

    boolean flagInt(char c) {
        char nr = '0';
        for (int i = 0; i < 10; ++i) {
            if (c == nr)
                return true;
            nr++;
        }
        return false;
    }

    boolean knowledgeCondition(int i, int j) {
        return flagInt(matrix[i][j]) & aux[i][j] == '.';
    }

    boolean knowledge(int i, int j, JButton[][] buttons, MouseListener e)
            throws UnsupportedAudioFileException,
            LineUnavailableException, IOException {
        int flag = 0;
        for (int k = 0; k < 8; k++) {
            if (rangeFlag(i + VH[k], j + VW[k])) {
                if (matrix[i + VH[k]][j + VW[k]] == 'x' & aux[i + VH[k]][j + VW[k]] != '!') {
                    flag = 1;
                } else if (matrix[i + VH[k]][j + VW[k]] != 'x' & aux[i + VH[k]][j + VW[k]] == '!') {
                    flag = -1;
                    break;
                }
            }
        }
        if (flag == 1)
            return false;
        for (int k = 0; k < 8; k++) {
            if (rangeFlag(i + VH[k], j + VW[k])) {
                if (aux[i + VH[k]][j + VW[k]] != '!') {
                    if (flag == 0) {
                        discover(i + VH[k], j + VW[k], buttons);
                    } else if (discover(i + VH[k], j + VW[k], buttons) == -1) {
                        gameOver(i + VH[k], j + VW[k], buttons, e);
                        new AudioEffects("sounds/lose_minesweeper.wav");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void preKnowledgeExecute(int i, int j, JButton[][] buttons) {
        for (int k = 0; k < 8; k++) {
            if (rangeFlag(i + VH[k], j + VW[k])) {
                if (aux[i + VH[k]][j + VW[k]] == '?') {
                    setImage0(buttons[i + VH[k]][j + VW[k]]);
                }
            }
        }
    }

    void preKnowledgeRestore(int i, int j, JButton[][] buttons) {
        for (int k = 0; k < 8; k++) {
            if (rangeFlag(i + VH[k], j + VW[k])) {
                if (aux[i + VH[k]][j + VW[k]] == '?') {
                    setImageFacingDown(buttons[i + VH[k]][j + VW[k]]);
                }
            }
        }
    }

    int mark(int i, int j, JButton[][] buttons) {
        if (aux[i][j] == '?') {
            aux[i][j] = '!';
            setImageFlagged(buttons[i][j]);
            return -1;
        } else if (aux[i][j] == '!') {
            aux[i][j] = '?';
            setImageFacingDown(buttons[i][j]);
            return 1;
        }
        return 0;
    }

    void playerView(int i, int j, JButton[][] buttons) {
        if (matrix[i][j] == '0')
            setImage0(buttons[i][j]);
        if (matrix[i][j] == '1')
            setImage1(buttons[i][j]);
        if (matrix[i][j] == '2')
            setImage2(buttons[i][j]);
        if (matrix[i][j] == '3')
            setImage3(buttons[i][j]);
        if (matrix[i][j] == '4')
            setImage4(buttons[i][j]);
        if (matrix[i][j] == '5')
            setImage5(buttons[i][j]);
        if (matrix[i][j] == '6')
            setImage6(buttons[i][j]);
        if (matrix[i][j] == '7')
            setImage7(buttons[i][j]);
        if (matrix[i][j] == '8')
            setImage8(buttons[i][j]);
    }

    boolean verify(int i, int j) {
        return aux[i][j] != '!';
    }

    void gameOver(int i, int j, JButton[][] button, MouseListener e) {
        setImageRedBomb(button[i][j]);
        button[i][j].removeMouseListener(e);
        for (int k = 0; k < h; k++) {
            for (int l = 0; l < w; l++) {
                if (k != i | l != j) {
                    if (matrix[k][l] != 'x' && aux[k][l] == '!') {
                        setImageXFlagged(button[k][l]);
                        button[k][l].removeMouseListener(e);
                    } else if (aux[k][l] == '!') {
                        button[k][l].removeMouseListener(e);
                    } else if (matrix[k][l] == 'x' && aux[k][l] != '!') {
                        setImageBomb(button[k][l]);
                        button[k][l].removeMouseListener(e);
                    } else {
                        playerView(k, l, button);
                        button[k][l].removeMouseListener(e);
                    }
                }
            }
        }
    }

    boolean win() {
        for (int k = 0; k < h; k++) {
            for (int l = 0; l < w; l++) {
                if (matrix[k][l] != 'x' & aux[k][l] != '.')
                    return false;
            }
        }
        return true;
    }

    void winBoard(JButton[][] button, MouseListener e) {
        for (int k = 0; k < h; k++) {
            for (int l = 0; l < w; l++) {
                if (matrix[k][l] == 'x') {
                    button[k][l].removeMouseListener(e);
                    button[k][l].setEnabled(false);
                }
            }
        }
    }
}
