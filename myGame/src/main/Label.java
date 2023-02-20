package main;

import javax.swing.*;

public class Label extends JLabel {
    //protected JLabel img=new JLabel();
    protected int x=0,y=0;

    public final static int width=55,height=55;
    public Label(String url, int x, int y){
        this.x=x;
        this.y=y;
        this.setIcon(new ImageIcon(url));
        this.setBounds(width*y,height*x,width,height);
        this.setOpaque(false);
    }

}
