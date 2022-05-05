import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;

public class ExchangePanel extends JPanel {

    private ImageIcon background;
    private Document document;
    private JLabel coinValueLabel, afterCalculate;
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
        int yButtonReset=300;
        int xButtonCulculate=200;
        int yButtonCulculate=400;
        int dx=120;

        JButton usdNis=createBotton(xButton,yButton,"usd-nis");
        this.add(usdNis);
        coinValueLabel =new JLabel();
        afterCalculate =new JLabel();
        coinValueLabel.setBounds(100,300,100,50);
        this.add(coinValueLabel);
        coinValueLabel.setVisible(false);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(200,300,100,50);
        this.add(usernameTextField);
        usernameTextField.setVisible(false);
        afterCalculate.setBounds(200,450,100,50);
        this.add(afterCalculate);
        coinValueLabel.setVisible(false);
        JButton euroNis=createBotton(xButton+dx,yButton,"euro-nis");
        this.add(euroNis);
        JButton euroDollar=createBotton(xButton+2*dx,yButton,"euro-dollar");
        this.add(euroDollar);
        JButton euroPound=createBotton(xButton+3*dx,yButton,"euro-pound");
        this.add(euroPound);
        JButton poundNis=createBotton(xButton+4*dx,yButton,"pound-nis");
        this.add(poundNis);
        JButton reset=createBotton(xButtonReset,yButtonReset,"reset");
        reset.setBackground(Color.CYAN);
        this.add(reset);
        reset.setVisible(false);
        JButton culculateExchange = createBotton(xButtonCulculate,yButtonCulculate,"culculate_exchange");
        culculateExchange.setBackground(Color.CYAN);
        this.add(culculateExchange);
        culculateExchange.setVisible(false);

        usdNis.addActionListener( (event)->{
                setRunning(true);
                run("pair_63","pid-63-bid");
                coinValueLabel.setVisible(true);
                usernameTextField.setVisible(true);
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
            usernameTextField.setVisible(true);
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
            usernameTextField.setVisible(true);
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
            usernameTextField.setVisible(true);
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
            usernameTextField.setVisible(true);
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
            afterCalculate.setVisible(false);
            usernameTextField.setVisible(false);
            usernameTextField.setText("");
            coinValueLabel.setText("");
            afterCalculate.setText("");
            setRunning(false);


        });
        culculateExchange.addActionListener((event)->{
            afterCalculate.setText("" + (Double.parseDouble(usernameTextField.getText()) * Double.parseDouble(coinValueLabel.getText())) +"");
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

    public JButton createBotton(int xButton ,int yButton,String name){
        JButton jButton = new JButton(name);
        jButton.setBackground(Color.WHITE);
        jButton.setBounds(xButton, yButton, 100, 50);
        return jButton;
    }
//    public JLabel createJlable(int yLabal,String name){
//        JLabel jLabel=new JLabel(name);
//        jLabel.setBackground(Color.CYAN);
//        jLabel.setBounds(400,yLabal,150,50);
//        return jLabel;
//    }

    public void run(String s1,String s2){
        new Thread(()->{
            this.setFocusable(true);
            this.requestFocus();
            while (isRunning){
                try {
                    this.document= Jsoup.connect("https://il.investing.com/currencies/streaming-forex-rates-majors").get();
                    Element coinValue =this.document.getElementById("" + s1 + "");
                    Elements coin= coinValue.getElementsByClass(""+ s2 + "");
                    coinValueLabel.setText("" + coin.get(0).text() + "");
                    coinValueLabel.setBackground(Color.WHITE);
                    coinValueLabel.setOpaque(true);
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();

    }

}
