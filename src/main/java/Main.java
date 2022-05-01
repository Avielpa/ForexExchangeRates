import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame {
    public static void main(String[] args) {
    new Main();
    }

    public Main () {
        ExchangePanel exchangePanel=new ExchangePanel(0,0,800,608);

        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(exchangePanel);
        this.setSize(800,608);

    }


}
