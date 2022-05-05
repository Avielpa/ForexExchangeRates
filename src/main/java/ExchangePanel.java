import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ExchangePanel extends JPanel {

    private ImageIcon background;
    private Document document;
    private JLabel jLabel1,jLabel2;

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

        JButton jButton1=createBotton(xButton,yButton,"usd-euro");
        this.add(jButton1);
        jLabel1=new JLabel();
        jLabel2=new JLabel();
        jLabel1.setBounds(100,300,100,50);
        this.add(jLabel1);
        jLabel1.setVisible(false);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(200,300,100,50);
        this.add(usernameTextField);
        usernameTextField.setVisible(false);
        jLabel2.setBounds(200,450,100,50);
        this.add(jLabel2);
        jLabel1.setVisible(false);
        JButton jButton2=createBotton(xButton+dx,yButton,"nis-euro");
        this.add(jButton2);
        JButton jButton3=createBotton(xButton+2*dx,yButton,"usd-euro");
        this.add(jButton3);
        JButton jButton4=createBotton(xButton+3*dx,yButton,"usd-euro");
        this.add(jButton4);
        JButton jButton5=createBotton(xButton+4*dx,yButton,"usd-euro");
        this.add(jButton5);
        JButton jButton6=createBotton(xButtonReset,yButtonReset,"reset");
        jButton6.setBackground(Color.CYAN);
        this.add(jButton6);
        jButton6.setVisible(false);
        JButton jButton7 = createBotton(xButtonCulculate,yButtonCulculate,"culculate_exchange");
        jButton7.setBackground(Color.CYAN);
        this.add(jButton7);
        jButton7.setVisible(false);

        jButton1.addActionListener( (event)->{
            new Thread(()->{
                this.setFocusable(true);
                this.requestFocus();
                while (jLabel1!=null){
                    try {
                        this.document= Jsoup.connect("https://il.investing.com/currencies/streaming-forex-rates-majors").get();
                        Element topArticleElement=this.document.getElementById("pair_63");
                        Elements dolar=topArticleElement.getElementsByClass("pid-63-bid");
                        jLabel1.setText("" + dolar.get(0).text() + "");
                        jLabel1.setBackground(Color.BLUE);
                        jLabel1.setOpaque(true);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    repaint();
                }
            }).start();
                jLabel1.setVisible(true);
                usernameTextField.setVisible(true);
                jButton2.setVisible(false);
                jButton3.setVisible(false);
                jButton4.setVisible(false);
                jButton5.setVisible(false);
                jButton6.setVisible(true);
                jButton7.setVisible(true);
                System.out.println("done");
        });
        jButton2.addActionListener((event)->{
            Element topArticleElement=this.document.getElementById("pair_63");
            Elements dolar=topArticleElement.getElementsByClass("pid-63-bid");
            jLabel1.setText("nis");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton1.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(true);
            jButton7.setVisible(true);

        });
        jButton3.addActionListener((event)->{
            jLabel1.setText("nis");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(true);
            jButton7.setVisible(true);
        });
        jButton4.addActionListener((event)->{
            jLabel1.setText("nis");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(true);
            jButton7.setVisible(true);
        });
        jButton5.addActionListener((event)->{
            jLabel1.setText("nis");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton1.setVisible(false);
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton6.setVisible(true);
            jButton7.setVisible(true);
        });
        jButton6.addActionListener((event)->{
            jButton1.setVisible(true);
            jButton2.setVisible(true);
            jButton3.setVisible(true);
            jButton4.setVisible(true);
            jButton5.setVisible(true);
            jButton6.setVisible(false);
            jButton7.setVisible(false);
            jLabel1.setVisible(false);
            jLabel2.setVisible(false);
            usernameTextField.setVisible(false);

        });
        jButton7.addActionListener((event)->{
            jLabel2.setText("" + (Double.parseDouble(usernameTextField.getText()) * Double.parseDouble(jLabel1.getText())) +"");
            jLabel2.setVisible(true);
        });





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

    public void run(){

    }

}
