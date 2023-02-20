package main;


import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


class Enemy extends Item{
    @Override
    public void onPress(Item npc) {



        if (Main.user.attack(npc, false) > 0) {

            new Sound("resources/打击.mp3").run();
            Main.user.attack(npc, true);
            Main.map.clear(x, y);
            Main.infoBar.info.removeAll();



            if(npc.change!=null) {
                for (int i = 0; i < npc.change.length; i++) {
                    String change = npc.change[i];
                    String nature = new String();
                    int dt = 0;
                    int flag = 1;
                    int k = 1;
                    for (int j = 0; j < change.length(); j++) {
                        if (change.charAt(j) != ':') {
                            if (flag == 1)
                                nature += ((char) change.charAt(j));
                            else {
                                if (change.charAt(j) == '-') k = -1;
                                else dt = dt * 10 + change.charAt(j) - '0';
                            }
                        } else {
                            flag = 0;
                        }
                    }
                    dt *= k;
                    //设计中不应存在扣hp的情况
                    if (nature.equals("hp")) {

                        Main.user.hp += dt;
                    } else if (nature.equals("atk")) {
                        if (Main.user.atk + dt < 0) {
                            Main.user.atk = 0;
                        }
                        Main.user.atk += dt;
                    } else if (nature.equals("dfc")) {
                        if (Main.user.dfc + dt < 0) {
                            Main.user.dfc = 0;
                        }
                        Main.user.dfc += dt;
                    } else if (nature.equals("money")) {
                        if (Main.user.money + dt < 0) {
                            Main.user.money = 0;
                        }
                        Main.user.money += dt;
                    } else if (nature.equals("buff1")) {

                    } else if (nature.equals("buff2")) {

                    } else if (nature.equals("buff3")) {

                    }
                }
            }
            Main.map.clearScr();
            if(npc.left!=null) {
                for (int i = 0; i < npc.left.length; i++) {
                    Item left = npc.left[i];
                    if(Objects.equals(left.type, "Empty")) {
                        Main.map.clear(left.x, left.y);
                    }
                    else {
                        Main.map.mp[left.x][left.y] = left;


                    }
                }
            }
            Main.map.addTo();
            Main.update();
            Main.infoBar.playerRefresh();
            Main.update();
            npc.afterPress();

        }
        Main.frame.requestFocus();
    }

    public Enemy(String url, int x, int y, String name, String s, String sound, int hp, int atk, int dfc, int money){
        this.x=x;
        this.slogan=s;
        this.url=url;
        this.sound=new Sound(sound);
        this.y=y;
        this.name=name;
        this.type="Enemy";
        this.url=url;
        this.pic=new Label(url,x,y);
        this.hp=hp;
        this.atk=atk;
        this.dfc=dfc;
        this.money=money;
    }

    public Enemy(String url,int x,int y,String name,String s,String sound,int hp,int atk,int dfc,int money,Item []left,String []change ){
        this.x=x;
        this.slogan=s;
        this.url=url;
        this.left=left;
        this.change=change;
        this.sound=new Sound(sound);
        this.y=y;
        this.name=name;
        this.type="Enemy";
        this.url=url;
        this.pic=new Label(url,x,y);
        this.hp=hp;
        this.atk=atk;
        this.dfc=dfc;
        this.money=money;
    }

}
class Goods{
    protected String url;
    protected String name;
    protected String ics,dcs;
    protected Integer num1,num2;
    public Goods(String url,String ics,Integer num1,String dcs,Integer num2){

        this.ics=ics;
        this.dcs=dcs;
        this.url=url;
        this.num1=num1;
        this.num2=num2;
    }

}
class Shop extends Item{

    public Shop(String url,int x,int y,String name,String s,String sound,Goods gs[]){
        this.sound=new Sound(sound);

        this.goods=gs;
        this.type="Shop";
        this.slogan=s;
        this.name=name;
        this.x=x;
        this.y=y;
        this.url=url;
        this.pic=new Label(url,x,y);
    }



}
class Friend extends Item{
    @Override
    public void onPress(Item npc) {
        boolean ok=true;
        if(npc.change!=null)
            for(int i=0;i<npc.change.length;i++){
                String change=npc.change[i];
                String nature=new String();
                int dt=0;
                int flag=1;
                int k=1;
                for(int j=0;j<change.length();j++){
                    if(change.charAt(j)!=':'){
                        if(flag==1)
                            nature+=((char)change.charAt(j));
                        else{
                            if(change.charAt(j)=='-') k=-1;
                            else dt=dt*10+change.charAt(j)-'0';
                        }
                    }
                    else{
                        flag=0;
                    }
                }
                dt*=k;

                if(nature.equals("hp")){
                    if(Main.user.hp+dt<=0){
                        ok=false;
                        break;
                    }
                    Main.user.hp+=dt;
                }
                else if(nature.equals("atk")){
                    if(Main.user.atk+dt<0){
                        ok=false;
                        break;
                    }
                    Main.user.atk+=dt;
                }
                else if(nature.equals("dfc")){
                    if(Main.user.dfc+dt<0){
                        ok=false;
                        break;
                    }
                    Main.user.dfc+=dt;
                }
                else if(nature.equals("money")){
                    if(Main.user.money+dt<0){
                        ok=false;
                        break;
                    }
                    Main.user.money+=dt;
                }
                else if(nature.equals("buff1")){
                    Main.user.buff1=true;
                }
                else if(nature.equals("buff2")){
                    Main.user.buff2=true;
                }
                else if(nature.equals("buff3")){
                    Main.user.buff3=true;
                }
            }
        if(ok){
            Main.map.clear(x, y);
            Main.infoBar.info.removeAll();
            Main.map.clearScr();
            if(npc.left!=null) {
                for (int i = 0; i < npc.left.length; i++) {
                    Item left = npc.left[i];
                    if (Objects.equals(left.type, "Empty")) {
                        Main.map.clear(left.x, left.y);
                    } else {
                        if(Objects.equals(left.type, "Bank")) left.lastTime=new Date();
                        Main.map.mp[left.x][left.y] = left;


                    }
                }
            }

            Main.map.addTo();


            //showInfo(npc.left, npc.left.x, npc.left.y, c);
            npc.afterPress();
        }
        Main.update();
        Main.infoBar.playerRefresh();
        Main.frame.requestFocus();
    }

    public Friend(String url, int x, int y, String name, String s, String sound, Item []left, String []change ){
        this.sound=new Sound(sound);
        this.change=change;
        this.name=name;
        this.slogan=s;
        this.type="Friend";
        this.left=left;
        this.x=x;
        this.y=y;
        this.url=url;
        this.pic=new Label(url,x,y);

    }
    public Friend(String url,int x,int y,String name,String s,String sound){
        this.sound=new Sound(sound);
        this.left=null;
        this.change=null;
        this.slogan=s;
        this.type="Friend";
        this.name=name;
        this.x=x;
        this.y=y;
        this.url=url;
        this.pic=new Label(url,x,y);

    }
}

class Bank extends Item{


    //钱是否够  够的话就升级

    public Bank(String url, int x, int y, String name, String s, String sound, Date lastTime,int rank){
        this.lastTime=lastTime;
        this.rank=rank;
        this.money=10;
        this.sound=new Sound(sound);
        this.type="Bank";
        this.url=url;
        this.slogan=s;
        this.name=name;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Clue extends Item{
    @Override
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.infoBar.info.removeAll();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Clue(int x, int y, String s, String sound){
        this.sound=new Sound(sound);

        this.slogan=s;
        this.url="resources/线索.png";
        this.type="Clue";
        this.name="线索";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Medicine extends Item{
    @Override
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.hp += npc.hp;
        Main.infoBar.info.removeAll();
        Main.infoBar.playerRefresh();
        Main.update();
        npc.afterPress();
        Main.frame.requestFocus();
    }

    public Medicine(String url, int x, int y, String name, String s, String sound, int hp){
        this.sound=new Sound(sound);

        this.hp=hp;
        this.type="Medicine";
        this.url=url;
        this.slogan=s;
        this.name=name;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Swords extends Item{
    @Override
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.atk += npc.atk;
        Main.infoBar.info.removeAll();
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Swords(String url, int x, int y, String name, String s, String sound, int atk){
        this.sound=new Sound(sound);

        this.atk=atk;
        this.type="Swords";
        this.slogan=s;
        this.url=url;
        this.name=name;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Shield extends Item{
    @Override
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.dfc += npc.dfc;
        Main.infoBar.info.removeAll();
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Shield(String url, int x, int y, String name, String s, String sound, int dfc){
        this.sound=new Sound(sound);

        this.dfc=dfc;
        this.url=url;
        this.slogan=s;
        this.name=name;
        this.type="Shield";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}

class Boat extends Item{
    @Override
    public void onPress(Item npc) {

        Node cur=Main.graph.maps.get(Main.map.id);
        Main.graph.trsTo(cur.next.get(npc.dst));
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Boat(String url, int x, int y, String s, int dst){
        this.dst=dst;
        this.slogan=s;
        this.type="Boat";
        this.url=url;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);

    }
}
class Coin extends Item{
    @Override
    public void onPress(Item npc) {
        new Sound("resources/金币.mp3").run();
        Main.map.clear(x, y);
        Main.user.money += npc.money;
        Main.infoBar.info.removeAll();
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Coin(String url, int x, int y, String name, String s, String sound, int money){
        this.sound=new Sound(sound);

        this.slogan=s;
        this.name=name;
        this.type="Coin";
        this.money=money;
        this.url=url;
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Car extends Item{
    @Override
    public void onPress(Item npc) {
        npc.runSound.run();
        Main.user.x = npc.dstX;
        Main.user.y = npc.dstY;
        Main.user.pic.setLocation(Label.width * npc.dstY, Label.height * npc.dstX);
        Main.c.add(Main.user.pic, 0);
        Main.infoBar.info.removeAll();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Car(String url, int x, int y, String name, String s, String sound, int dstX, int dstY, String runSound){
        this.sound=new Sound(sound);
        this.runSound=new Sound(runSound);
        this.slogan=s;
        this.dstX=dstX;
        this.dstY=dstY;
        this.url=url;
        this.name=name;
        this.type="Car";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}

class  Blocker extends Item{
    public Blocker(String url,int x,int y,String name,String s,String sound){
        this.sound=new Sound(sound);
        this.slogan=s;
        this.url=url;
        this.name=name;
        this.type="Empty";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}


class Pt{
    protected int x;
    protected int y;
    public Pt(int x,int y){
        this.x=x;
        this.y=y;
    }
}

class Door extends Item{
    public Door(int x, int y, ArrayList<Pt> area){

        this.area=area;
        this.type="Door";
        this.x=x;
        this.y=y;
    }
}

class Empty extends Item{
    public Empty(int x,int y){
        this.type="Empty";
        this.x=x;
        this.y=y;
    }
}

class Buff1 extends Item{
    @Override
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.buff1 =true;
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }

    public Buff1(String url, int x, int y, String name, String s, String sound){
        this.sound=new Sound(sound);
        this.slogan=s;
        this.url=url;
        this.name=name;
        this.type="Buff1";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Buff2 extends Item{
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.buff2 =true;
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }
    public Buff2(String url,int x,int y,String name,String s,String sound){
        this.sound=new Sound(sound);
        this.slogan=s;
        this.url=url;
        this.name=name;
        this.type="Buff1";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}
class Buff3 extends Item{
    public void onPress(Item npc) {
        Main.map.clear(x, y);
        Main.user.buff3 =true;
        Main.infoBar.playerRefresh();
        Main.update();
        Main.frame.requestFocus();
        npc.afterPress();
    }
    public Buff3(String url,int x,int y,String name,String s,String sound){
        this.sound=new Sound(sound);
        this.slogan=s;
        this.url=url;
        this.name=name;
        this.type="Buff1";
        this.x=x;
        this.y=y;
        this.pic=new Label(url,x,y);
    }
}


