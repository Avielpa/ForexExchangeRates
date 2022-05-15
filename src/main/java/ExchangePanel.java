import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;

public class ExchangePanel extends JPanel {

    private ImageIcon background;
    private Document document;
    private JLabel coinValueLabel, afterCalculate,coinValueText;;
    private JTextField amountOfMoney;
    private boolean isRunning = false;

    public ExchangePanel(int x,int y,int width,int hieght){
        this.setBounds(x, y, width, hieght);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setRequestFocusEnabled(true);
        this.background=new ImageIcon("background.jpg");
        int xButton=10;
        int yButton=10;
        int xButtonReset=650;
        int yButtonReset=500;
        int xButtonCulculate=200;
        int yButtonCulculate=400;
        int dx=120;
        int regulatWidth=100;
        int regularHeight=50;
        int culculateWidth=200;
        int rculculateHeight=50;


        JButton usdNis=createBotton(xButton,yButton,"usd-nis",regulatWidth,regularHeight);
        JButton euroNis=createBotton(xButton+dx,yButton,"euro-nis",regulatWidth,regularHeight);
        JButton euroDollar=createBotton(xButton+2*dx,yButton,"euro-dollar",regulatWidth,regularHeight);
        JButton euroPound=createBotton(xButton+3*dx,yButton,"euro-pound",regulatWidth,regularHeight);
        JButton poundNis=createBotton(xButton+4*dx,yButton,"pound-nis",regulatWidth,regularHeight);
        JButton reset=createBotton(xButtonReset,yButtonReset,"reset",regulatWidth,regularHeight);
        JButton culculateExchange = createBotton(xButtonCulculate,yButtonCulculate,"culculate_exchange",culculateWidth,rculculateHeight);
        coinValueLabel =new JLabel();
        afterCalculate =new JLabel();
        coinValueText =new JLabel();
        amountOfMoney = new JTextField();


        this.add(usdNis);
        this.add(coinValueLabel);
        this.add(coinValueText);
        this.add(amountOfMoney);
        this.add(euroNis);
        this.add(euroDollar);
        this.add(euroPound);
        this.add(poundNis);
        this.add(afterCalculate);
        this.add(reset);
        this.add(culculateExchange);

        coinValueText.setBounds(100,300,150,50);
        coinValueLabel.setBounds(250,300,50,50);
        afterCalculate.setBounds(200,450,100,50);
        amountOfMoney.setBounds(culculateExchange.getX()+culculateExchange.getWidth(),yButtonCulculate,100,50);
        coinValueLabel.setVisible(false);
        amountOfMoney.setVisible(false);
        coinValueLabel.setVisible(false);
        reset.setVisible(false);
        culculateExchange.setVisible(false);

        reset.setBackground(Color.CYAN);
        culculateExchange.setBackground(Color.CYAN);



        usdNis.addActionListener( (event)->{
                setRunning(true);
                run("pair_63","pid-63-bid");
                coinValueLabel.setVisible(true);
                coinValueText.setVisible(true);
                amountOfMoney.setVisible(true);
                euroNis.setVisible(false);
                euroDollar.setVisible(false);
                euroPound.setVisible(false);
                poundNis.setVisible(false);
                reset.setVisible(true);
            culculateExchange.setVisible(true);
                System.out.println("done");
        });
        euroNis.addActionListener((event)->{
            setRunning(true);
            run("pair_64","pid-64-bid");
            coinValueLabel.setVisible(true);
            coinValueText.setVisible(true);
            amountOfMoney.setVisible(true);
            usdNis.setVisible(false);
            euroDollar.setVisible(false);
            euroPound.setVisible(false);
            poundNis.setVisible(false);
            reset.setVisible(true);
            culculateExchange.setVisible(true);

        });
        euroDollar.addActionListener((event)->{
            setRunning(true);
            run("pair_1","pid-1-bid");
            coinValueLabel.setVisible(true);
            coinValueText.setVisible(true);
            amountOfMoney.setVisible(true);
            usdNis.setVisible(false);
            euroNis.setVisible(false);
            euroPound.setVisible(false);
            poundNis.setVisible(false);
            reset.setVisible(true);
            culculateExchange.setVisible(true);
        });
        euroPound.addActionListener((event)->{
            setRunning(true);
            run("pair_6","pid-6-bid");
            coinValueLabel.setVisible(true);
            coinValueText.setVisible(true);
            amountOfMoney.setVisible(true);
            usdNis.setVisible(false);
            euroNis.setVisible(false);
            euroDollar.setVisible(false);
            poundNis.setVisible(false);
            reset.setVisible(true);
            culculateExchange.setVisible(true);
        });
        poundNis.addActionListener((event)->{
            setRunning(true);
            run("pair_65","pid-65-bid");
            coinValueLabel.setVisible(true);
            coinValueText.setVisible(true);
            amountOfMoney.setVisible(true);
            usdNis.setVisible(false);
            euroNis.setVisible(false);
            euroDollar.setVisible(false);
            euroPound.setVisible(false);
            reset.setVisible(true);
            culculateExchange.setVisible(true);
        });
        reset.addActionListener((event)->{
            usdNis.setVisible(true);
            euroNis.setVisible(true);
            euroDollar.setVisible(true);
            euroPound.setVisible(true);
            poundNis.setVisible(true);
            reset.setVisible(false);
            culculateExchange.setVisible(false);
            coinValueLabel.setVisible(false);
            coinValueText.setVisible(false);
            afterCalculate.setVisible(false);
            amountOfMoney.setVisible(false);
            amountOfMoney.setText("");
            coinValueLabel.setText("");
            afterCalculate.setText("");
            setRunning(false);


        });
        culculateExchange.addActionListener((event)->{
            afterCalculate.setText("" + (Double.parseDouble(amountOfMoney.getText()) * Double.parseDouble(coinValueLabel.getText())) +"");
            afterCalculate.setVisible(true);
            afterCalculate.setBackground(Color.WHITE);
            afterCalculate.setOpaque(true);
        });
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.background.paintIcon(this,graphics,0,0);
    }

    public JButton createBotton(int xButton ,int yButton,String name,int width,int height){
        JButton jButton = new JButton(name);
        jButton.setBackground(Color.WHITE);
        jButton.setBounds(xButton, yButton, width, height);
        return jButton;
    }

    public void run(String s1,String s2){
        new Thread(()->{
            this.setFocusable(true);
            this.requestFocus();
            while (isRunning){ //
                try {
                    this.document= Jsoup.connect("https://il.investing.com/currencies/streaming-forex-rates-majors").get();
                    Element coinValue =this.document.getElementById("" + s1 + "");
                    Elements coin= coinValue.getElementsByClass(""+ s2 + "");
                    coinValueLabel.setText("" + coin.get(0).text() + "");
                    coinValueLabel.setBackground(Color.WHITE);
                    coinValueLabel.setOpaque(true);
                    coinValueText.setText("The Current Value Is:");
                    coinValueText.setBackground(Color.WHITE);
                    coinValueText.setOpaque(true);
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();

    }

}
