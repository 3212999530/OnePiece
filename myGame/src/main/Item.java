package main;

import java.util.Date;
import javax.swing.*;
import java.util.ArrayList;

public abstract class Item {

    protected ArrayList<Pt> area;
    protected Sound sound;
    protected Sound runSound;
    protected Item []left;
    protected String []change;
    protected Integer hp,atk,dfc,money;
    protected String name;
    protected String slogan;
    protected int x,y;
    protected String type;
    protected Label pic;
    protected String url;
    protected int dst;
    protected int dstX,dstY;
    protected Goods []goods;
    protected int rank;
    protected Date lastTime;


    public void afterPress(){
    }
    public void onPress(Item npc){

    }
}



class Water extends Item{
    public static String url="resources/wall.png";
    public Water(int x,int y){
        this.x=x;
        this.y=y;
        this.type="Wall";
        this.pic=new Label(url,x,y);
    }
}

class Tree extends Item{
    public static String url="resources/tree.png";
    public Tree(int x,int y){
        this.x=x;
        this.y=y;
        this.type="Wall";
        this.pic=new Label(url,x,y);
    }
}
class Wall extends Item{
    public static String url="resources/map3/wall3.jpg";
    public Wall(int x,int y){
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
        this.type="Wall";
    }
    public Wall(String url,int x,int y){
        this.url=url;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
        this.type="Wall";
    }
}
