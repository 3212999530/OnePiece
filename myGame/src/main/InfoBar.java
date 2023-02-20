package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Objects;

public class InfoBar {
    protected static int m = 6;
    protected Bar hp;
    protected Bar atk;
    protected Bar dfc;

    protected Bar money;

    protected Item npcCopy;
    //protected Bar name;
    protected JPanel info;
    protected JLabel buff1;
    protected JLabel buff2;
    protected JLabel buff3;

    public InfoBar() {
        info = new JPanel();
        info.setOpaque(false);
        info.setLayout(null);
        info.setBounds(0, 0, Main.width, Main.height);


        Label head = new Label("resources/路飞头像.png", 1, 1 + Map.m);
        JLabel name = new JLabel("  路飞");
        head.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.DARK_GRAY));
        name.setFont(new Font("宋体", 2, 26));
        name.setForeground(Color.WHITE);
        name.setBounds(Label.width * (2 + Map.m), Label.height, Label.width * 4, Label.height);
        Main.c.add(head);
        Main.c.add(name);


        hp = new Bar("resources/血.png", 2, 0);
        hp.setTxt(Main.user.hp.toString());
        atk = new Bar("resources/匕首.png", 2, 3);
        atk.setTxt(Main.user.atk.toString());
        dfc = new Bar("resources/护盾.png", 3, 0);
        dfc.setTxt(Main.user.dfc.toString());
        money = new Bar("resources/金币.png", 3, 3);
        money.setTxt(Main.user.money.toString());

        buff1=new JLabel(new ImageIcon("resources/攻.png"));
        buff1.setBounds(Label.width * (Map.m)+20, Label.height*4, Label.width, Label.height);

        buff2=new JLabel(new ImageIcon("resources/防.png"));
        buff2.setBounds(Label.width * (2 + Map.m)+20, Label.height*4, Label.width, Label.height);

        buff3=new JLabel(new ImageIcon("resources/退.png"));
        buff3.setBounds(Label.width * (4 + Map.m)+20, Label.height*4, Label.width, Label.height);

    }

    public void addToFrame(Container c) {
        c.add(info);
        c.add(hp);
        c.add(atk);
        c.add(dfc);
        c.add(money);
    }

    public void playerRefresh() {
        hp.setTxt(Main.user.hp.toString());
        atk.setTxt(Main.user.atk.toString());
        dfc.setTxt(Main.user.dfc.toString());
        money.setTxt(Main.user.money.toString());
        Main.c.remove(buff1);
        Main.c.remove(buff2);
        Main.c.remove(buff3);
        if(Main.user.buff1){
            Main.c.add(buff1,0);
        }
        if(Main.user.buff2){
            Main.c.add(buff2,0);
        }
        if(Main.user.buff3){
            Main.c.add(buff3,0);
        }
    }

    public void showInfo(Item npc, int x, int y) {
        info.removeAll();
        npcCopy=npc;
        if (npc != null) {
            JLabel txt = new JLabel("<html>" + npc.slogan + "</html>");
            txt.setFont(new Font("黑体", Font.BOLD, 20));
            txt.setHorizontalAlignment(SwingConstants.CENTER);

            txt.setVerticalAlignment(SwingConstants.TOP);
            txt.setForeground(Color.WHITE);
            txt.setOpaque(false);
            txt.setBounds((Map.m) * Label.width + 38, 10 + (8) * Label.height, Label.width * 4 + 45, Label.height * 3);

            Label head = new Label(npc.url, 7, 1 + Map.m);

            JLabel name = new JLabel(npc.name);
            head.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.DARK_GRAY));
            name.setFont(new Font("宋体", 2, 26));
            name.setForeground(Color.WHITE);
            name.setBounds(Label.width * (2 + Map.m), Label.height * 7, Label.width * 4, Label.height);

            while (Main.curSound != null && Main.curSound.getState() != Thread.State.TERMINATED) {
                Main.curSound.stop();
            }

            if (npc.sound != null) {
                Main.curSound = new Thread(npc.sound);
                Main.curSound.start();
            }

            if (Objects.equals(npc.type, "Enemy")) {
                Bar eHp = new Bar("resources/血.png", 12, 0);
                Bar eAtk = new Bar("resources/匕首.png", 12, 3);
                Bar eDfc = new Bar("resources/护盾.png", 13, 0);
                Bar eMoney = new Bar("resources/金币.png", 13, 3);

                eHp.setTxt(npc.hp.toString());
                eAtk.setTxt(npc.atk.toString());
                eMoney.setTxt(npc.money.toString());
                eDfc.setTxt(npc.dfc.toString());
                JLabel fightInfo = new JLabel();
                Integer dt = Main.user.attack(npc, false);
                if (dt > 0) {
                    dt = Main.user.hp - dt;
                    fightInfo.setText("扣血：" + dt);
                } else {
                    fightInfo.setText("无法战胜");
                }
                fightInfo.setForeground(Color.WHITE);
                fightInfo.setFont(new Font("黑体", Font.BOLD, 20));
                fightInfo.setBounds((Map.m + m / 2 - 1) * Label.width, 14 * Label.height, Label.width * 2, Label.height);

                JButton bt = new JButton("攻打");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);
                    }
                });
                info.add(fightInfo);
                info.add(bt);
                info.add(eHp);
                info.add(eAtk);
                info.add(eDfc);
                info.add(eMoney);
            }
            else if (Objects.equals(npc.type, "Medicine")) {
                JButton bt = new JButton("拾取");
                Bar eHp = new Bar("resources/血.png", 12, 0);
                eHp.setTxt("+" + npc.hp);

                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);
                    }
                });
                info.add(bt);
                info.add(eHp);

            }
            else if (Objects.equals(npc.type, "Shield")) {
                Bar eDfc = new Bar("resources/护盾.png", 12, 0);
                eDfc.setTxt("+" + npc.dfc);

                JButton bt = new JButton("拾取");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);
                    }
                });
                info.add(eDfc);
                info.add(bt);
            }
            else if (Objects.equals(npc.type, "Swords")) {
                Bar eAtk = new Bar("resources/匕首.png", 12, 0);
                eAtk.setTxt("+" + npc.atk);

                JButton bt = new JButton("拾取");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);

                    }
                });
                info.add(bt);
                info.add(eAtk);
            }
            else if (Objects.equals(npc.type, "Coin")) {
                Bar eAtk = new Bar("resources/金币.png", 12, 0);
                eAtk.setTxt("+" + npc.money);

                JButton bt = new JButton("拾取");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);

                    }
                });
                info.add(bt);
                info.add(eAtk);
            }
            else if (Objects.equals(npc.type, "Car")) {
                JButton bt = new JButton("进入");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);

                    }
                });
                info.add(bt);
            }
            else if (Objects.equals(npc.type, "Clue")) {
                JButton bt = new JButton("拾取");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);

                    }
                });
                info.add(bt);
            }
            else if (Objects.equals(npc.type, "Shop")) {
                class ShopBar {
                    JButton bt;
                    JLabel bkg;
                    JLabel pic;
                    Goods gd;
                    Bar icPic;
                    Bar dcPic;
                    Integer ic;
                    String icUrl;
                    String dcUrl;
                    Integer dc;

                    public ShopBar(Goods gd, int x, int y) {
                        bkg = new JLabel(new ImageIcon("resources/技能卷轴.png"));
                        bkg.setBounds((Map.m + y) * Label.width, (x) * Label.height, Label.width * 2, Label.height * 2);
                        pic = new JLabel(new ImageIcon(gd.url));
                        pic.setBounds((Map.m + y) * Label.width + 28, (x) * Label.height + 45, Label.width, Label.height);
                        //pic.setText(gd.name);
                        //pic.setFont(new Font("黑体", Font.BOLD, 24));
                        if (gd.ics == "hp") {
                            ic = Main.user.hp;
                            icUrl = "resources/血.png";
                        } else if (gd.ics == "atk") {
                            ic = Main.user.atk;
                            icUrl = "resources/匕首.png";
                        } else if (gd.ics == "dfc") {
                            ic = Main.user.dfc;
                            icUrl = "resources/护盾.png";
                        } else if (gd.ics == "money") {
                            ic = Main.user.money;
                            icUrl = "resources/金币.png";
                        }
                        if (gd.dcs == "hp") {
                            dc = Main.user.hp;
                            dcUrl = "resources/血.png";
                        } else if (gd.dcs == "atk") {
                            dc = Main.user.atk;
                            dcUrl = "resources/匕首.png";
                        } else if (gd.dcs == "dfc") {
                            dc = Main.user.dfc;
                            dcUrl = "resources/护盾.png";
                        } else if (gd.dcs == "money") {
                            dc = Main.user.money;
                            dcUrl = "resources/金币.png";
                        }
                        icPic = new Bar(icUrl, y + 3, x + 1);
                        icPic.setBounds((Map.m + y + 2) * Label.width, (x) * Label.height, Label.width * 2, Label.height);
                        icPic.setTxt("+" + gd.num1.toString());
                        icPic.txt.setFont(new Font("黑体", Font.BOLD, 24));
                        dcPic = new Bar(dcUrl, y + 3, x + 1);
                        dcPic.setBounds((Map.m + y + 4) * Label.width - 15, (x) * Label.height, Label.width * 2, Label.height);
                        dcPic.setTxt("-" + gd.num2.toString());
                        dcPic.txt.setFont(new Font("黑体", Font.BOLD, 24));

                        bt = new JButton("购买");
                        bt.setBounds((Map.m + y + 3) * Label.width - 20, (x + 1) * Label.height, Label.width * 2, Label.height);
                        bt.addActionListener(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                if (gd.dcs == "hp") {
                                    dc = Main.user.hp;
                                } else if (gd.dcs == "atk") {
                                    dc = Main.user.atk;
                                } else if (gd.dcs == "dfc") {
                                    dc = Main.user.dfc;
                                } else if (gd.dcs == "money") {
                                    dc = Main.user.money;
                                }
                                System.out.println(gd.dcs.equals("hp"));
                                System.out.println(dc.compareTo(gd.num2));
                                System.out.println(Objects.equals(dc, gd.num2));

                                if (dc.compareTo(gd.num2) > 0 || !gd.dcs.equals("hp") && Objects.equals(dc, gd.num2)) {
                                    dc -= gd.num2;
                                    if (gd.ics == "hp") {
                                        Main.user.hp += gd.num1;
                                    } else if (gd.ics == "atk") {
                                        Main.user.atk += gd.num1;

                                    } else if (gd.ics == "dfc") {
                                        Main.user.dfc += gd.num1;
                                    } else if (gd.ics == "money") {
                                        Main.user.money += gd.num1;
                                    }
                                    if (gd.dcs == "hp") {
                                        Main.user.hp -= gd.num2;
                                    } else if (gd.dcs == "atk") {
                                        Main.user.atk -= gd.num2;

                                    } else if (gd.dcs == "dfc") {
                                        Main.user.dfc -= gd.num2;
                                    } else if (gd.dcs == "money") {
                                        Main.user.money -= gd.num2;
                                    }
                                    playerRefresh();
                                }
                                Main.frame.requestFocus();
                            }
                        });
                    }

                    public void addTo() {

                        info.add(bkg);
                        info.add(icPic);
                        info.add(dcPic);
                        info.add(bt);
                        info.add(pic, 0);
                    }

                }

                ShopBar sb1 = new ShopBar(npc.goods[0], 9, 0);
                ShopBar sb2 = new ShopBar(npc.goods[1], 11, 0);
                ShopBar sb3 = new ShopBar(npc.goods[2], 13, 0);
                sb1.addTo();
                sb2.addTo();
                sb3.addTo();
            }
            else if (Objects.equals(npc.type, "Friend")) {
                JButton bt = new JButton("确认");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);
                    }
                });
                info.add(bt);
            }
            else if(Objects.equals(npc.type, "Boat")){
                JButton bt = new JButton("启航");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        npc.onPress(npc);
                        info.removeAll();
                    }
                });
                info.add(bt);
            }
            else if(Objects.equals(npc.type, "Bank")){
                JLabel rk = new JLabel("等级: "+npc.rank+" 升级所需: "+npc.money);
                rk.setFont(new Font("宋体", 2, 20));
                rk.setForeground(Color.WHITE);
                rk.setBounds(Label.width * (1 + Map.m), Label.height * 11, Label.width * 4, Label.height);
                JLabel lt = new JLabel("累计时间: "+(new Date().getTime()-npc.lastTime.getTime())/1000);
                lt.setFont(new Font("宋体", 2, 20));
                lt.setForeground(Color.WHITE);
                lt.setBounds(Label.width * (1 + Map.m), Label.height * 12, Label.width * 4, Label.height);

                JButton bt1 = new JButton("升级");
                bt1.setBounds((Map.m + m / 2 - 1) * Label.width, (13) * Label.height, Label.width * 2, Label.height);
                bt1.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(Main.user.money>=npc.money){

                            Main.user.money-=npc.money;
                            npc.money+=10;
                            npc.rank+=1;
                            playerRefresh();
                            rk.setText("等级: "+npc.rank+" 升级所需: "+npc.money);

                        }
                        Main.frame.requestFocus();

                    }
                });
                JButton bt2 = new JButton("收集");
                bt2.setBounds((Map.m + m / 2 - 1) * Label.width, (14) * Label.height, Label.width * 2, Label.height);
                bt2.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Date cur=new Date();
                        Main.user.money+=npc.rank*(int)(((cur.getTime()-npc.lastTime.getTime())/1000));
                        npc.lastTime=cur;
                        playerRefresh();
                        lt.setText("累计时间: 0");
                        Main.frame.requestFocus();

                    }

                });
                info.add(rk);
                info.add(lt);
                info.add(bt1);
                info.add(bt2);
            }
            else if(Objects.equals(npc.type, "Buff1")){
                JButton bt = new JButton("拾取");


                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        npc.onPress(npc);
                        info.removeAll();

                    }
                });
                info.add(bt);
            }
            else if(Objects.equals(npc.type, "Buff2")){
                JButton bt = new JButton("拾取");


                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        npc.onPress(npc);
                        info.removeAll();

                    }
                });
                info.add(bt);
            }
            else if(Objects.equals(npc.type, "Buff3")){
                JButton bt = new JButton("拾取");
                bt.setBounds((Map.m + m / 2 - 1) * Label.width, (15) * Label.height, Label.width * 2, Label.height);
                bt.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        npc.onPress(npc);
                        info.removeAll();

                    }
                });
                info.add(bt);
            }
            npc.afterPress();
            info.add(txt);
            info.add(head);
            info.add(name);
        }

        Main.c.remove(info);
        Main.c.add(info, 0);
    }
}
class Bar extends JPanel{
    protected JLabel txt;

    public Bar(String url,int i,int j){
        this.setLayout(new BorderLayout());
        this.add(new JLabel(new ImageIcon(url)),BorderLayout.WEST);
        txt=new JLabel("1211231");
        txt.setFont(new Font("黑体", Font.BOLD, 28));
        txt.setForeground(Color.WHITE);
        this.add(txt,BorderLayout.CENTER);
        this.setOpaque(false);
        this.setBounds((Map.m+j)* Label.width+18,(i)* Label.height, Label.width*3,Label.height);
    }
    public void setTxt(String s){
        txt.setText(s);
    }
}