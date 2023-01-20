import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer extends JPanel {
    private final JLabel time = new JLabel();
    private final int[] timeArr = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private String timeString;
    GameTimer() {
        setBounds(60, 40, 70, 35);
        setBackground(Color.black);
        setLayout(new BorderLayout());
        time.setFont(new Font("SansSerif Bold", Font.BOLD, 40));
        time.setForeground(new Color(190, 0, 0));
        time.setText(createString(0, 0 , 0));
        time.setHorizontalAlignment(JLabel.CENTER);
        add(time, BorderLayout.CENTER);
    }

    private String translateIndex(int index) {
        if (index == 3)
            return "database/best_time_hard.txt";
        else if (index == 2) {
            return "database/best_time_medium.txt";
        }
        else
            return "database/best_time_easy.txt";
    }

    private boolean verify(int index) {
        if (value(index) == -1)
            return true;
        return value(index) >= Integer.parseInt(timeString);
    }

    private int value(int index) {
        File file = new File(translateIndex(index));
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return scanner.nextInt();
    }

    void setNewBestTime(int index) {
        if (verify(index)) {
            try {
                FileWriter fileWriter = new FileWriter(translateIndex(index));
                fileWriter.write(timeString);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final Timer t = new Timer();
    void startTimer() {
        TimerTask sleep = new TimerTask() {
            int count = 0;
            int i = 0;
            int j = 0;
            int k = 0;
            @Override
            public void run() {
                if (count < 1000) {
                    k++;
                    timeString = createString(i, j, k);
                    time.setText(timeString);
                    if (k == 9) {
                        j++;
                        k = -1;
                    }
                    if (j == 10) {
                        i++;
                        j = 0;
                    }
                    count++;
                } else
                    t.cancel();
            }
        };
        t.scheduleAtFixedRate(sleep, 1000, 1000);
    }

    void stopTimer() {
        t.cancel();
    }

    private String createString(int i, int j, int k) {
        return timeArr[i] + String.valueOf(timeArr[j]) + timeArr[k];
    }
}
