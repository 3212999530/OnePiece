package main;




import javax.swing.*;
import java.awt.*;


import java.lang.*;
import java.awt.event.*;



public class Main extends JFrame{


    protected static boolean inMap=false;

    protected static Thread bgm;
    protected JButton mapB;
    protected static Graph graph=new Graph();
    protected static Thread curSound;
    protected int infWidth,infHeight;
    protected static  int width=Label.width*(Map.m+6),height=Label.height*(Map.n);
    protected static WorldMap worldMap=new WorldMap();
    protected static Map map;
    protected static JFrame frame=new JFrame();
    protected static User user=new User(1,1);
    protected static Container c=frame.getContentPane();
    protected static InfoBar infoBar=new InfoBar();

    public Main() throws InterruptedException,Exception{


        infHeight=Map.n;
        infWidth=6;
        width= Label.width*(Map.m+infWidth);
        height= Label.height*(Map.n);
        frame.setTitle("11Princess");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(width+14,height+36);

        c.setLayout(null);
        infoBar.addToFrame(c);

        mapB=new JButton(new ImageIcon("resources/地图图标.png"));
        mapB.setBorderPainted(false);
        mapB.setBounds(Label.width*(4+Map.m),0,Label.width*2,Label.height*2);
        mapB.setContentAreaFilled(false);
        mapB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inMap) {
                    worldMap.closeMap();
                    update();
                    frame.requestFocus();
                    inMap = false;
                } else {
                    worldMap.showMap();
                    inMap = true;
                }
            }
        });
        graph.maps.get(0).open=true;
        c.add(mapB);
        map=graph.maps.get(0).map;
        graph.curMap=0;
        map.addTo();
        Main.bgm= new Thread(new Bgm(map.bgmPath));
        Main.bgm.start();
        Label rock=new Label("resources/rock.png",0,20);
        rock.setSize(330,1100);
        c.add(rock);
        frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int cc=e.getKeyCode();

                switch (cc){
                    case KeyEvent.VK_F1:
                        Main.user.hp+=100;
                        Main.user.atk+=100;
                        Main.user.dfc+=100;
                        Main.user.money+=1000;
                        infoBar.playerRefresh();
                        System.out.println("OK");
                        break;
                    case KeyEvent.VK_F2:
                        for(int i=0;i<9;i++){
                            graph.maps.get(i).open=true;
                        }
                        break;
                    case 'w','W',KeyEvent.VK_UP: user.pic.setIcon(new ImageIcon(user.url2));user.move(0); break;
                    case 'd','D',KeyEvent.VK_RIGHT: user.pic.setIcon(new ImageIcon(user.url4));user.move(1); break;
                    case 's','S',KeyEvent.VK_DOWN: user.pic.setIcon(new ImageIcon(user.url1));user.move(2); break;
                    case 'a','A',KeyEvent.VK_LEFT: user.pic.setIcon(new ImageIcon(user.url3));user.move(3); break;
                    case KeyEvent.VK_ENTER:
                        if(Main.infoBar.npcCopy!=null)
                            Main.infoBar.npcCopy.onPress(Main.infoBar.npcCopy);
                        break;
                }
                update();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.requestFocus();
        while(true){
        }
    }
    public static void update(){

        frame.revalidate();
        frame.repaint();
    }
    public static void main(String[] args) throws InterruptedException,Exception{
        new Main();
    }
}








