package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldMap extends JPanel{
    protected JLabel bkg;
    protected MapBt mapBt[];
    public WorldMap(){
        this.setBounds(0,0,1100,880);
        this.setLayout(null);

        bkg=new JLabel(new ImageIcon("resources/worldMap/worldMap.png"));
        bkg.setBounds(0,0,1100,880);
        mapBt=new MapBt[9];
        mapBt[0]=new MapBt(new Dst(0,1,1),"resources/worldMap/谢尔兹镇(45,635).png",45,635,230,200);
        mapBt[1]=new MapBt(new Dst(1,14,1),"resources/worldMap/西罗布村(250,440).png",250,440,186,212);
        mapBt[2]=new MapBt(new Dst(2,14,18),"resources/worldMap/巴拉蒂(15,283).png",15,283,189,224);
        mapBt[3]=new MapBt(new Dst(4,14,9),"resources/worldMap/阿龙乐园(11,0).png",11,0,193,226);
        mapBt[4]=new MapBt(new Dst(3,14,1),"resources/worldMap/小花园(222,113).png",222,113,198,220);
        mapBt[5]=new MapBt(new Dst(5,7,1),"resources/worldMap/阿拉巴斯坦(531,63).png",531,63,225,190);
        mapBt[6]=new MapBt(new Dst(7,1,17),"resources/worldMap/幽灵船(826,11).png",826,11,186,202);
        mapBt[7]=new MapBt(new Dst(6,1,5),"resources/worldMap/磁鼓岛（667，313）.png",667,313,184,181);
        mapBt[8]=new MapBt(new Dst(8,6,1),"resources/worldMap/司法岛(872,616).png",900,274,188,208);
        //mapBt[9]=new MapBt(new Dst(0,1,1),"resources/worldMap/司法岛(872,616).png",872,616,187,204);
        this.add(bkg,0);
        for(int i=0;i<9;i++) this.add(mapBt[i],0);

    }
    public void showMap(){
        Main.c.add(this,0);
        Main.update();
    }
    public void closeMap(){
        Main.c.remove((this));
        Main.update();
    }
}


class MapBt extends JButton {

    protected Dst dst;
    public MapBt(Dst dst,String url,int x,int y,int width,int height){
        this.setIcon(new ImageIcon(url));
        this.setBorderPainted(false);
        this.dst=dst;
        this.setBounds(x,y,width,height);
        this.setContentAreaFilled(false);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main.graph.maps.get(dst.dstM).open){
                    if(Main.graph.curMap!=dst.dstM){
                        Main.graph.trsTo(dst);
                    }
                    Main.worldMap.closeMap();
                    Main.frame.requestFocus();

                }
            }
        });
    }


}