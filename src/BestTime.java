import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BestTime extends JPanel {
    private int value;
    BestTime(int index) {
        setBounds(640, 40, 70, 35);
        setBackground(Color.black);
        setLayout(new BorderLayout());
        setToolTipText("BEST TIME");
        setValue(index);
        JLabel time = new JLabel();
        time.setFont(new Font("SansSerif Bold", Font.BOLD, 40));
        time.setForeground(Color.gray);
        time.setText(createString());
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

    private void setValue(int index) {
        File file = new File(translateIndex(index));
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        value = scanner.nextInt();
    }

    private String createString() {
        if (value == -1) {
            return "N/A";
        }
        if (value < 10)
            return "00" + value;
        else if (value < 100)
            return "0" + value;
        else
            return String.valueOf(value);
    }
}
