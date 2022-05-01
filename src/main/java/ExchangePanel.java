import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ExchangePanel extends JPanel {

    private ImageIcon background;

    public ExchangePanel(int x,int y,int width,int hieght){
        this.setBounds(x, y, width, hieght);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setRequestFocusEnabled(true);
        this.background=new ImageIcon("background.jpg");
        try{
            Document document= Jsoup.connect("https://www.x-rates.com/").get();
            Element topArticleElement=document.getElementById("flagelement");
            System.out.println(topArticleElement);

        }catch (IOException e){
            e.printStackTrace();
        }
        int xButton=10;
        int yButton=10;
        int xButtonReset=700;
        int yButtonReset=300;
        int dx=120;
        JButton jButton1=createBotton(xButton,yButton,"usd-euro");
        this.add(jButton1);
        JLabel jLabel1=new JLabel();
        jLabel1.setBounds(100,300,100,50);
        this.add(jLabel1);
        jLabel1.setVisible(false);
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(200,300,100,50);
        this.add(usernameTextField);
        usernameTextField.setVisible(false);


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
//        int yLable=200;
        jButton1.addActionListener( (event)->{
            jLabel1.setText("usd");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton2.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(true);


            System.out.println("done");
        });
        jButton2.addActionListener((event)->{
            jLabel1.setText("nis");
            jLabel1.setVisible(true);
            usernameTextField.setVisible(true);
            jButton1.setVisible(false);
            jButton3.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setVisible(false);
            jButton6.setVisible(true);

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
        });
        jButton6.addActionListener((event)->{
            Main main=new Main();
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

//    public void run(){
//        new Thread(()->{
//            this.setFocusable(true);
//            this.requestFocus();
//
//
//
//
//
//        }).start();
//    }

}
