package main;

import java.util.ArrayList;
import java.util.Objects;

public class User{
    public int x;
    public int y;
    public static String url1="resources/路飞正面照片.png",url2="resources/路飞背面照片.png",url3="resources/路飞左面照片.png",url4="resources/路飞右面照片.png";
    protected Integer hp=100,atk=300,dfc=1,money=300;
    protected boolean buff1,buff2,buff3;
    protected Label pic;
    public int coldNum;
    public int attack(Item enemy,boolean sit){
        int myAtk=atk,itAtk=enemy.atk,myDfc=dfc,itDfc= enemy.dfc;
        if(buff1){
            myAtk*=2;
            buff1=false;
        }

        if(buff2){
            myDfc*=2;
            buff2=false;
        }
        if(buff3){
            itDfc=(itDfc+1)/2;
            itAtk=(itAtk+1)/2;
            buff3=false;
        }
        if(myAtk<=itDfc){
            return -1;
        }
        int dt=((enemy.hp+myAtk-itDfc-1)/(myAtk-itDfc)-1)*(Math.max(itAtk - myDfc, 0));
        if(sit&&dt<hp){
            hp-=dt;
            money+=enemy.money;
        }
        return hp-dt;
    }
    public void move(int dir) {
        int nx = x, ny = y;
        switch (dir) {
            case 0:
                nx--;
                break;
            case 1:
                ny++;
                break;
            case 2:
                nx++;
                break;
            case 3:
                ny--;
                break;
        }
        if (Main.curSound != null) {
            Main.curSound.stop();
            while (Main.curSound.isAlive()) {

            }
        }
        if (Main.map.mp[nx][ny] == null) {
            if(Main.graph.curMap==6){
                if(coldNum!=0) coldNum--;
                else hp=Math.max(1,hp-1);
                Main.infoBar.playerRefresh();
            }

            Main.infoBar.showInfo(null, nx, ny);
            if (Main.graph.curMap == 7) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (Main.map.mask[x + i][y + j] == null && 0 < x + i && 0 < y + j && x + i < Map.n - 1 && y + j < Map.m - 1) {
                            Main.map.mask[x + i][y + j] = new Label("resources/map8/迷雾.png", x + i, y + j);
                            Main.c.add(Main.map.mask[x + i][y + j], 0);
                        }
                    }
                }
                x = nx;
                y = ny;
                pic.setBounds(pic.width * y, pic.height * x, pic.width, pic.height);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (Main.map.mask[x + i][y + j] != null && 0 < x + i && 0 < y + j && x + i < Map.n - 1 && y + j < Map.m - 1) {
                            Main.c.remove(Main.map.mask[x + i][y + j]);
                            Main.map.mask[x + i][y + j] = null;

                        }
                    }
                }
            } else {
                x = nx;
                y = ny;
            }
            pic.setBounds(pic.width * y, pic.height * x, pic.width, pic.height);


        } else if (Objects.equals(Main.map.mp[nx][ny].type, "Door")) {
            x = nx;
            y = ny;
            pic.setBounds(pic.width * y, pic.height * x, pic.width, pic.height);
            ArrayList<Pt> area = Main.map.mp[x][y].area;
            //Main.map.clearScr();
            for (Pt i : area) {
                Main.c.remove(Main.map.mask[i.x][i.y]);
                if (Main.map.mp[i.x][i.y] != null && Main.map.mp[i.x][i.y].pic != null)
                    Main.c.add(Main.map.mp[i.x][i.y].pic, 0);
                Main.map.mask[i.x][i.y] = null;
            }
            Main.c.remove(pic);
            Main.c.add(pic,0);
            Main.map.mp[x][y]=null;
            //Main.map.addTo();
        }
        else if(Objects.equals(Main.map.mp[nx][ny].type, "Wall")){
            Main.infoBar.showInfo(null,nx,ny);
        }
        else{
            Main.infoBar.showInfo(Main.map.mp[nx][ny],nx,ny);
        }

    }

    public User( int x, int y){
            this.x = x;
            this.y = y;
            pic = new Label(url1, x, y);
            coldNum=0;
        }
    }
