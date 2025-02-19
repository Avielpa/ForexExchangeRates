import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            ExchangePanel panel = new ExchangePanel(800, 608);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
        });
    }
}