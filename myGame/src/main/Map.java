package main;
import java.util.Date;
import java.util.ArrayList;

public class Map {
    public String bgmPath;
    protected  Item mp[][];
    protected  Label floor[][];
    protected  Label mask[][];
    protected int id;
    protected final static int n=16,m=20;
    public Map(){

    }
    public void clear(int x,int y){
        Main.c.remove(mp[x][y].pic);
        mp[x][y]=null;
    }

    public void  setWater(int x1,int x2,int y1,int y2){
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                mp[i][j]=new Water(i,j);
            }
        }
    }
    public void  setTree(int x1,int x2,int y1,int y2){
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                mp[i][j]=new Tree(i,j);
            }
        }
    }
    public void  setWall(int x1,int x2,int y1,int y2){
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                mp[i][j]=new Wall(i,j);
            }
        }
    }
    public void  setWall(String url,int x1,int x2,int y1,int y2){
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                mp[i][j]=new Wall(url,i,j);
            }
        }
    }
    public void  setNull(int x1,int x2,int y1,int y2){
        for(int i=x1;i<=x2;i++){
            for(int j=y1;j<=y2;j++){
                mp[i][j]=null;
            }
        }
    }
    public void clearScr(){
        for(int i=0;i<Map.n;i++){
            for(int j=0;j<Map.m;j++){
                //if(Objects.equals(mp[i][j].type, "MultiMan")) Main.c.remove(mp[i][j].personalities.get(mp[i][j].cur).pic);
                if(mp[i][j]!=null&&mp[i][j].pic!=null) Main.c.remove(mp[i][j].pic);
                if(mask[i][j]!=null) Main.c.remove(mask[i][j]);
            }
        }
        for(int i=1;i<Map.n-1;i++){
            for(int j=1;j<Map.m-1;j++){
                Main.c.remove(floor[i][j]);
            }
        }
    }
    public void addTo(){
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                //if(Objects.equals(mp[i][j].type, "MultiMan")) Main.c.add(mp[i][j].personalities.get(mp[i][j].cur).pic);
                if(mp[i][j]!=null&&mp[i][j].pic!=null) Main.c.add(mp[i][j].pic);
            }
        }
        for(int i=1;i<n-1;i++){
            for(int j=1;j<m-1;j++){
                Main.c.add(floor[i][j]);
                if(mask[i][j]!=null) Main.c.add(mask[i][j],0);
            }
        }
        Main.c.remove(Main.user.pic);
        Main.c.add(Main.user.pic,0);
    }
}
class Dst{
    int dstM,dstY,dstX;
    public Dst(int dstM,int dstX,int dstY){
        this.dstM=dstM;
        this.dstX=dstX;
        this.dstY=dstY;
    }
}
class Node{

    protected ArrayList<Dst> next;
    protected Map map;
    protected boolean open;
    protected Node(Map map){
        this.next=new ArrayList<Dst>();
        this.map=map;
        this.open=false;
    }

}
class Graph{
    protected ArrayList<Node> maps;
    protected int curMap=0;
    public Graph(){
        maps=new ArrayList<Node>();
        Node map1=new Node(new Map1());
        map1.next.add(new Dst(1,14,1));
        //在map1里面可以添加更多传送地图下标
        map1.map.id=0;
        map1.open=true;
        maps.add(map1);
        //西罗布

        Node map2=new Node(new Map2());
        map2.next.add(new Dst(0,2,13));
        map2.next.add(new Dst(2,14,18));
        map2.map.id=1;
        maps.add(map2);
        //海上餐厅
        Node map3=new Node(new Map3());
        map3.next.add(new Dst(1,7,18));
        map3.next.add(new Dst(3,14,1));
        map3.next.add(new Dst(4,14,9));
        map3.map.id=2;
        maps.add(map3);
        //小花园
        Node map4=new Node(new Map4());
        map4.next.add(new Dst(2,2,18));
        map4.next.add(new Dst(5,7,1));
        map4.map.id=3;
        maps.add(map4);
        //阿龙乐园
        Node map5=new Node(new Map5());
        map5.next.add(new Dst(2,2,1));
        map5.map.id=4;
        maps.add(map5);
        //阿拉巴斯坦
        Node map6=new Node(new Map6());
        map6.next.add(new Dst(3,9,18));
        map6.next.add(new Dst(6,1,5));
        map6.next.add(new Dst(7,1,17));
        map6.map.id=5;
        maps.add(map6);
        //磁鼓岛
        Node map7=new Node(new Map7());
        map7.next.add(new Dst(5,13,8));
        map7.next.add(new Dst(8,6,1));
        map7.map.id=6;
        maps.add(map7);
        //幽灵岛
        Node map8=new Node(new Map8());
        map8.next.add(new Dst(5,13,11));
        map8.next.add(new Dst(8,1,4));
        map8.map.id=7;
        maps.add(map8);
        //司法岛
        Node map9=new Node(new Map9());
        map9.next.add(new Dst(6,13,7));
        map9.next.add(new Dst(7,14,2));
        map9.map.id=8;
        maps.add(map9);
    }
    public void trsTo(Dst curDst){
        Main.map.clearScr();
        maps.get(curDst.dstM).map.addTo();
        curMap= curDst.dstM;
        maps.get(curMap).open=true;
        Main.map=maps.get(curMap).map;
        Main.map.addTo();
        Main.user.x = curDst.dstX;
        Main.user.y = curDst.dstY;
        Main.user.pic.setLocation(Label.width * Main.user.y, Label.height *Main.user.x);
        Main.c.remove(Main.user.pic);
        Main.c.add(Main.user.pic, 0);
        Main.bgm.stop();
        Main.bgm= new Thread(new Bgm(Main.map.bgmPath));
        Main.bgm.start();
        //Main.user.move(-1);//用于幽灵岛消雾
    }

}


class Map1 extends Map{
    public Map1(){
        floor=new Label[n][m];

        mask=new Label[n][m];
        for(int i=1;i<n-1;i++){
            for(int j=1;j<m-1;j++){
                floor[i][j]=new Label("resources/floor.png",i,j);
            }
        }
        bgmPath="resources/map1.mp3";



        mp=new Item[n][m];
        for(int i=0;i<n;i++){
            mp[i][0]=new Water(i,0);
            mp[i][m-1]=new Water(i,m-1);
            for(int j=1;j<m-1;j++){
                if(i==0||i==n-1){
                    mp[i][j]=new Water(i,j);
                }
            }
        }
        setWater(4,9,1,2);
        setWater(8,9,3,3);
        setWater(10,15,1,1);
        setWater(13,15,2,18);
        setWater(2,4,4,4);
        setWater(4,12,5,9);
        setWater(1,3,8,9);
        setWater(1,1,8,18);
        setTree(8,12,10,10);
        setTree(11,12,11,18);
        setTree(8,12,10,10);
        setTree(2,12,18,18);
        setTree(2,2,14,17);
        setTree(8,12,10,10);
        setTree(5,5,11,13);
        setTree(2,4,11,11);
        setTree(3,8,14,14);
        setTree(11,11,14,14);
        setTree(7,7,15,15);
        setTree(7,7,17,17);
        setTree(10,10,14,14);
        Item []left=new Item[]{new Swords("resources/map1/和道一文字.png",10,17,"和道一文字","索隆三把佩刀中最直，品质中等的无上大快刀，世上只有21把","resources/map1/和道一文字.mp3",16)};
        mp[10][17]=new Friend("resources/map1/十字架上的索隆.png",10,17,"索隆","为什么来救我，你是恶魔的儿子吗？算了，在这倒下还不如拼一场，当回海贼，请收下这把----和道一文字，虽然不是什么名刀，但也十分锋利。","resources/map1/索隆.mp3",left,null);
        mp[10][11]=new Friend("resources/map1/露露.png",10,11,"露露","告诉你个秘密，要想从这里离开你得保证身上留有30贝利，你可以帮我救救被绑在十字架上的大哥哥吗，我做了饭团还没来得及送给他吃，呜呜呜.....","resources/map1/露露.mp3");
        String []change=new String[]{"money:-30"} ;
        left=new Item[]{new Boat("resources/bigBoat.png",1,13,"一艘驶向西罗布村的船",0)};
        mp[4][13]=new Friend("resources/map1/娜美.png",4,13,"娜美","嘻嘻，这宝箱里的金币我拿走了，我急需1万贝利，你可以给我30贝利吗，作为交换，我可以带你驶向下一座岛屿","resources/map1/娜美.mp3",left,change);
        mp[1][4]=new Friend("resources/map1/克比.png",1,4,"克比","我叫克比，这里是亚尔丽塔女士的海贼船，我是被抓上来的，一直在这里被迫做苦力，你看起来很饿，我这里有一些物资可以给你，你能救我出去吗？","resources/map1/克比.mp3");
        mp[3][5]=new Shield("resources/破碎的盾.png",3,5,"破碎的盾牌","某个士兵的盾牌？噢，还破了...","resources/破碎的盾.mp3",3);
        mp[1][7]=new Medicine("resources/肉棒.png",1,7,"一根肉棒","一根遗落的肉棒，还有点酸味儿...","resources/肉.mp3",16);
        mp[3][7]=new Swords("resources/锈迹斑斑的刀.png",3,7,"生锈的刀","这把锈迹斑斑的剑似乎有些年头...","resources/生锈的剑.mp3",4);
        mp[4][3]=new Enemy("resources/小兵.png",4,3,"小兵","你小子是谁，不知道这里是谁的海贼船吗？把小命留下吧！","resources/小兵.mp3",4,2,3,5);
        mp[9][4]=new Enemy("resources/map1/亚尔丽塔.png",9,4,"亚尔丽塔","哦吼吼，小帅哥，你说我是这个世界上最美丽的女人吗？","resources/map1/亚尔丽塔.mp3",10,5,6,5);
        mp[5][10]=new Enemy("resources/海军.png",5,10,"海军","你小子从海上回来，看起来很可疑呀，不会是海贼吧？先抓起来送到蒙卡上校那里！","resources/海军.mp3",4,2,4,5);
        mp[9][14]=new Enemy("resources/map1/贝鲁梅伯.png",9,14,"贝鲁梅伯","居然还有人会来救这个无药可救的白痴？我可是蒙卡上校的儿子，你敢动我，肯定吃不了兜着走！","resources/map1/贝鲁梅伯.mp3",8,4,5,5);
        mp[7][16]=new Enemy("resources/map1/蒙卡.png",7,16,"蒙卡","在这里我的官最大，一切想抵抗我的人都要被杀头，我的规矩就是规矩！","resources/map1/蒙卡.mp3",20,9,10,5);
        mp[12][2]=new Car("resources/boat.png",12,2,"小船","一艘划向岸边的小船",null,3,10,"resources/划船.mp3");
        mp[2][10]=new Car("resources/boat.png",2,10,"小船","一艘划回孤岛的小船",null,12,3,"resources/划船.mp3");
        mp[6][15]=new Car("resources/熊掌.png",6,15,"熊掌","熊掌？好奇怪，能吃吗？",null,3,12,"resources/传送.mp3");
        mp[2][12]=new Car("resources/熊掌.png",2,12,"熊掌","熊掌？好奇怪，能吃吗？",null,5,15,"resources/传送.mp3");
        mp[4][12]=new Coin("resources/被打开的宝箱.png",4,12,"宝箱","一堆...哦不有小偷！","resources/打开宝箱.mp3",0);
        mp[5][4]=new Coin("resources/一级宝箱.png",5,4,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[7][3]=new Coin("resources/一级宝箱.png",7,3,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[11][3]=new Clue(11,3,"左下角的小船可以带你前往谢尔兹镇，那里有你想得到的东西","resources/线索.mp3");
        mp[4][16]=new Clue(4,16,"看到角落里那个熊掌了吗，那可是王下七武海之一暴君熊的传送印记，说不定它会带你到你想去的地方","resources/线索.mp3");
        mp[9][16]=new Clue(9,16,"海贼猎人罗罗诺亚·索隆，三刀流剑术高强，但为了保护露露一家人选择自己代替她们被残暴的蒙卡上校捆绑在十字架上","resources/线索.mp3");
        Goods[] gs=new Goods[3];
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[8][12]=new Shop("resources/商城.png",8,12,"商店","欢迎光临","resources/开门.mp3",gs);

    }

}


//西罗布村
class Map2 extends Map{
    public Map2(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map2/floor2.png",i,j);
            }
        }
        bgmPath="resources/map2.mp3";
        mp=new Item[n][m];
        setWater(0,1,0,3);
        setWater(0,2,4,19);
        setWater(3,3,4,4);
        setWater(5,6,5,9);
        setWater(3,4,7,9);
        setWater(3,5,15,19);
        setWater(6,12,15,15);
        setWater(10,12,14,14);
        setWater(6,15,19,19);
        setWater(12,15,17,18);
        setWater(14,15,12,16);
        setWater(10,13,12,12);
        setWater(10,11,7,11);
        setWater(8,9,7,9);
        setWater(15,15,0,11);
        setWater(13,14,8,8);
        setWater(2,14,0,0);
        setWater(5,6,1,3);
        setWater(8,11,4,5);
        setWater(11,12,2,3);
        setWater(13,13,3,3);
        setWater(5,11,4,9);
        setNull(2,2,10,14);
        setNull(6,6,5,9);
        setNull(2,2,10,14);
        setNull(7,7,6,6);
        setNull(10,11,6,6);
        setNull(8,9,5,7);
        setNull(6,6,3,3);


        ArrayList<Pt> tmp=new ArrayList<>();
        for(int i=12;i<15;i++){
            for(int j=9;j<12;j++){
                mask[i][j]=new Label("resources/wall.png",i,j);
                tmp.add(new Pt(i,j));
            }
        }
        mask[12][8]=new Label("resources/wall.png",12,8);
        tmp.add(new Pt(12,8));

        mp[12][8]=new Door(12,8,tmp);
        mp[8][2]=new Blocker("resources/map2/破损的商城.png",8 ,2,"破损的商店","咳咳咳，好多灰尘门也破了，估计有些年头了",null);
        mp[6][4]=new Blocker("resources/巨石.png",6 ,4,"布满藤曼的巨石","该死，它似乎移不动！！",null);
        Item []left;
        String []change=new String[]{"buff1","money:-10"} ;
        Goods[] gs=new Goods[3];

        mp[8][7]=new Friend("resources/map2/商人1.png",8,7,"远游的学者","我这里有个远海的宝物，听说可以力大无比！只卖你10个金币",null,null,change);

        change=new String[]{"buff2","money:-10"} ;
        mp[9][7]=new Friend("resources/map2/商人2.png",9,7,"怪异的巫师","快过来！别买哪个矮子的东西！我这里的宝物最上乘，能坚毅不倒！只卖你10个金币",null,null,change);

        change=new String[]{"buff3","money:-10"} ;
        mp[8][5]=new Friend("resources/map2/商人3.png",8,5,"和蔼的巫师","快过来！别买哪个瘦子的东西！我这里的宝物最上乘，能让敌人虚弱！只卖你10个金币",null,null,change);
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        left=new Item[]{
                new Shop("resources/商城.png",8,2,"商店","欢迎光临","resources/开门.mp3",gs),

        };
        mp[4][2]=new Friend("resources/map2/乌索普.png",4 ,2,"乌索普","我是勇敢的海上战士乌索普！我和可雅小姐是青梅竹马，可是那里的管家不知道为什么每次都赶我走，只能偷偷去给她讲笑话。帮你把  老商店  修好你帮我带个话呗！",null,left,null);

        left=new Item[]{
                new Bank("resources/map2/金库.png",2,10,"金库","欢迎光临","resources/开门.mp3",new Date(),1)
        };
        mp[7][12]=new Friend("resources/map2/管家克洛.png",7 ,12,"管家克洛","你是什么人，我是不会告诉你公寓左上角有个金库的，赶紧出去！",null,left,null);
        left=new Item[]{
                new Empty(6,4)
        };
        mp[4][6]=new Friend("resources/map2/耶稣布.png",4 ,6,"耶稣布","好久不见了呀路飞，想成为像香克斯那样的海贼吗，我可以教你点东西，对了那块碍事的石头该移一移了。",null,left,null);
        mp[14][5]=new Friend("resources/map2/村民.png",14 ,5,"村民","最近这里可不太安宁，好像来了一批行踪诡异的人，还有告诉你个秘密，我发现这附近有个暗门，但貌似有人守着。",null);
        mp[10][2]=new Friend("resources/map2/乌索普海贼团.png",10 ,2,"三小只","你想要加入乌索普海贼团吗，我们老大可厉害了，会修造好多东西，他在在里边呢，没有他这里的商店都用不了了。",null);
        mp[6][12]=new Friend("resources/map2/可雅.png",6 ,12,"可雅","咳咳...这几天怎么不见乌索普了，你有见到他吗？",null);
        mp[11][16]=new Friend("resources/map2/梅丽.png",11,16,"梅丽","求求你保护好可雅小姐，为了报答你我可以为你提供一艘好船——阳光梅丽号",null);

        left=new Item[]{
                new Enemy("resources/精英2.png",6,7,"阴兵","嘿嘿小子，你被我们包围啦",null,4,2,3,5),
                new Enemy("resources/精英2.png",6,11,"阴兵","嘿嘿小子，你被我们包围啦",null,4,2,3,5),
                new Enemy("resources/精英2.png",5,10,"阴兵","嘿嘿小子，你被我们包围啦",null,4,2,3,5),
                new Enemy("resources/精英2.png",7,10,"阴兵","嘿嘿小子，你被我们包围啦",null,4,2,3,5)
        };
        mp[6][9]=new Enemy("resources/小兵.png",6,9,"奇怪的船员","嘿嘿，来吧！",null,4,2,3,5,left,null);

        mp[11][6]=new Enemy("resources/精英2.png",11,6,"身强力壮的船员","别想从这里过去！",null,4,2,3,5);
        mp[5][3]=new Enemy("resources/小怪2.png",5,3,"楞头船员","克洛船长回来了，我们要重返大海了！",null,4,2,3,5);
        mp[12][7]=new Enemy("resources/map2/摩奇.png",12,7,"摩奇","你瞅啥？我又没藏啥东西。",null,4,2,3,5);
        mp[13][10]=new Enemy("resources/map2/利基.png",13,10,"利基","你小子是谁，不知道这里是谁的海贼船吗？把小命留下吧！",null,4,2,3,5);
        mp[11][13]=new Enemy("resources/map2/猫人兄弟.png",11,13,"猫人兄弟","我们是山姆和布治，两个人加起来就是猫人兄弟，参上！",null,4,2,3,5);
        mp[9][17]=new Enemy("resources/map2/百计克洛.png",9,17,"百计克洛","为什么要来妨碍我，就差一点点，就可以顺利拿到可雅家的财产了！",null,4,2,3,5);


        mp[2][1]=new Swords("resources/map2/弹弓.png",2,1,"弹弓","一把木弹弓...等等上面似乎有一行字 “耶稣布” ","resources/生锈的剑.mp3",4);

        mp[4][13]=new Clue(4,13,"一封三年前的申请信：老爷，我是被海贼残害的可怜人，能不能给我一点饭吃，让我进去打杂就行---克洛","resources/线索.mp3");
        mp[3][5]=new Clue(3,5,"杰姆，代号Mr.5，在遥远的一个国家，有个组织正在密谋一场大事......","resources/线索.mp3");
        mp[12][11]=new Clue(12,11,"三天后...“百计”...集合进攻可雅宅邸...","resources/线索.mp3");




        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[2][12]=new Shop("resources/商城.png",2,12,"商店","欢迎光临","resources/开门.mp3",gs);


        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[2][14]=new Shop("resources/商城.png",2,14,"商店","欢迎光临","resources/开门.mp3",gs);

        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[9][10]=new Shop("resources/商城.png",9,10,"商店","欢迎光临","resources/开门.mp3",gs);


        mp[15][1]=new Boat("resources/bigBoat.png",15,1,"一艘驶向谢尔兹镇的船",0);
        mp[5][18]=new Boat("resources/bigBoat.png",5,18,"一艘驶向巴拉蒂的船",1);

    }

}



//海上餐厅
class Map3 extends Map{
    public Map3(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map3/floor3.png",i,j);
            }
        }
        bgmPath="resources/map3.mp3";
        setWall(6,9,13,18);
        setWall(9,11,1,5);
        setWall(2,4,4,12);
        setNull(3,3,4,9);
        setNull(4,4,9,9);
        setWater(0,1,0,19);
        setWater(2,3,11,11);
        setWater(2,15,0,0);
        setWater(10,10,1,4);
        setWater(11,15,1,1);
        setWater(15,15,2,19);
        setWater(2,14,19,19);
        setWater(7,8,14,19);
        setWall(14,14,5,16);
        setWall(7,12,10,11);
        setWall(10,10,8,9);
        setWall(12,12,5,5);
        setWall(10,10,6,6);

        setWall(5,8,1,8);
        setNull(6,8,2,6);

        mp[1][1]=new Boat("resources/bigBoat.png",1,1,"一艘驶向阿龙乐园的船",2);
        mp[1][18]=new Boat("resources/bigBoat.png",1,18,"一艘驶向小花园的船",1);
        mp[15][18]=new Boat("resources/bigBoat.png",15,18,"一艘驶向西罗布村的船",0);


        mp[12][2]=new Clue(12,2,"身份卡：乔拉可尔·米霍克，人称鹰眼，王下七武海之一，是世界第一大剑豪，据说和传说中四皇之一的香克斯实力不相上下","resources/线索.mp3");
        mp[11][9]=new Clue(11,9,"听说这里的厨师长哲普曾是某个海贼团的船长，说不定他有什么办法能让你武力大增","resources/线索.mp3");

        mp[3][4]=new Enemy("resources/map3/小八.png",3,4,"小八","娜美跟我们鱼人族回去了，想救她吗，先击败我吧！",null,4,2,3,5);
        mp[5][13]=new Enemy("resources/map3/克利克.png",5,13,"克利克","我是东海霸主克利克！小子听说你想英雄救美啊，先过了我这关，试试有没有这个实力吧！",null,4,2,3,5);
        mp[10][7]=new Enemy("resources/map3/阿金.png",10,7,"阿金","这里的饭真好吃呀，要不是老大的命令，我肯定不来搞破坏",null,4,2,3,5);
        mp[10][12]=new Enemy("resources/map3/霍波迪.png",10,12,"霍波迪","这家餐厅让我很不满意！什么厨师啊做菜这么难吃，滚开！(心里：一个个这么绅士真是抢了我的风头)",null,4,2,3,5);
        mp[13][3]=new Enemy("resources/map3/鹰眼.png",13,3,"鹰眼","小子，你还不够强，等什么时候有能力了来和我这把世界最强的黑刀试试，我在这里等着你！",null,4,2,3,5);

        mp[4][16]=new Friend("resources/map3/伊卡莱姆.png",4 ,16,"伊卡莱姆","mamama~我是阿拉伯斯坦国王军队长伊卡莱姆，我们赶紧上船吧，国王在等着我们！",null);
        mp[8][3]=new Friend("resources/map3/哲普.png",8 ,3,"哲普","臭小子你哪来的，我这里可是和平美好的巴拉蒂，我哪有什么武功秘笈",null);
        mp[13][7]=new Friend("resources/map3/山治.png",13 ,7,"山治","我是副厨师长，很抱歉昨天服务员都跑完了，看你一副很饿的样子，我马上给你做一份让你这辈子都难以忘怀的海鲜炒饭！",null);
        mp[13][16]=new Friend("resources/map3/派迪.png",13 ,16,"派迪","欢迎来到海上餐厅巴拉蒂！小哥想吃点啥呀？",null);
        mp[6][10]=new Friend("resources/map3/薇薇.png",6 ,10,"薇薇","呜呜呜...有人在我的国家预谋一场大政变，那里现在战乱纷纷，你能助我一臂之力吗？我可以提供船，在东偏北的方向，伊卡先生在等着我们\n",null);

        mp[6][2]=new Swords("resources/map3/书柜.png",6,2,"书柜","一堆料理食谱，翻一番说不定有收获” ","resources/生锈的剑.mp3",4);

        mp[11][17]=new Blocker("resources/map3/海鲜炒饭.png",11 ,17,"海鲜炒饭","这碗炒饭看起来味道不错",null);
        mp[10][15]=new Blocker("resources/map3/餐桌.png",10 ,15,"餐桌","只有汤了！",null);
        mp[12][12]=new Blocker("resources/map3/餐桌.png",12 ,12,"餐桌","哇！好丰盛",null);



    }

}


//小花园
class Map4 extends Map{
    public Map4(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map4/小花园背景.png",i,j);
            }
        }
        bgmPath="resources/map4.mp3";
        setWater(0,5,0,13);
        setWater(6,7,9,13);
        setWater(6,15,0,0);
        setWater(15,15,1,15);
        setWater(14,14,3,8);
        setWater(13,13,7,8);
        setWater(10,15,16,19);
        setWater(0,9,19,19);
        setWater(0,8,16,18);
        setWater(0,0,14,15);
        setWater(9,12,2,2);
        setWater(9,10,3,7);
        setWater(7,8,6,7);
        setWater(11,11,7,8);
        setWater(10,10,8,13);
        setNull(2,4,2,5);
        setNull(5,5,5,5);
        setNull(3,5,8,11);

        for(int i=1;i<=14;i++){
            for(int j=14;j<=15;j++){
                mp[i][j]=new Blocker("resources/map4/红土大陆.png",i ,j,"红土大陆","被封住了，你得想办法离开这里",null);
            }
        }
        Goods[] gs=new Goods[3];

        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[6][3]=new Shop("resources/商城.png",6,3,"商店","欢迎光临","resources/开门.mp3",gs);
        Item []left;

        left=new Item[]{
                new Friend("resources/map4/解封的布洛基.png",9 ,10,"布洛基","草帽小子，我记住你了，过了这片红土大陆，新世界可不好闯啊",null,new Item[]{
                        new Empty(9,14),
                        new Empty(9,15)
                },null)
        };
        mp[9][10]=new Blocker("resources/map4/蜡像版布洛基.png",9 ,10,"蜡像布洛基","小伙子，能不能帮我解开这些蜡，太麻烦了，只要打败了左上角的那个混蛋，我可以带你离开这片红土大陆",null);
        mp[12][7]=new Blocker("resources/map4/锁.png",12 ,7,"锁","被锁住了",null);


        mp[6][6]=new Enemy("resources/精英2.png",6,6,"碍事的船员","别想从这里过去！",null,4,2,3,5);
        mp[5][5]=new Enemy("resources/map4/不吉利二人组.png",5,5,"不吉利二人组","******",null,4,2,3,5);
        mp[3][3]=new Enemy("resources/map4/Mr.3.png",3,3,"Mr.3","草帽小子！听说你想去阿拉巴斯坦，就凭你这个毛头小子，可不那么容易",null,4,2,3,5,left,null);


        left=new Item[]{
                new Empty(12,7)
        };
        mp[4][10]=new Friend("resources/map4/东利.png",4 ,10,"东利","小伙子，看你骨骼清奇，这里有一把金钥匙，只要100贝利，要不要换呀",null,left,null);

        mp[11][9]=new Buff1("resources/map4/红色.png",11 ,9,"红","想来试试吗，交出50贝利吧",null);

        mp[14][12]=new Buff2("resources/map4/绿色.png",14 ,12,"绿","想来试试吗，交出50贝利吧",null);


        mp[12][13]=new Buff3("resources/map4/粉色.png",12 ,13,"粉","想来试试吗，交出50贝利吧",null);

        mp[14][9]=new Swords("resources/map4/蓝色1.png",14,9,"蓝","想来试试吗，交出50贝利吧","resources/生锈的剑.mp3",4);

        String []change=new String[]{"atk:20"} ;
        left=new Item[]{
                new Friend("resources/map4/技能卷轴.png",11 ,12,"人妖国地图","在遥远的玛卡巴卡国度，生活着一群幸福的人类",null, new Item[]{new Friend("resources/map4/Mr.2.png",8 ,3,"Mr.2","小草帽酱，是你带来的地图吗，我好喜欢，这样吧，我教你人妖拳法怎么样？",null,null,change)},null),

        };
        mp[11][12]=new Friend("resources/map4/紫色.png",11 ,12,"紫","想来试试吗，交出50贝利吧",null,left,null);

        mp[8][3]=new Friend("resources/map4/Mr.2.png",8 ,3,"Mr.2","路飞酱，你知道怎么去人妖国吗，和我一起去神的国度吧",null);

        mp[12][5]=new Friend("resources/map4/Miss.黄金周.png",12 ,5,"Miss.黄金周","我有个小屋子，里面有我画的各种色彩，每种颜色都对应着不同的效果，但是锁上了",null);

        mp[9][19]=new Boat("resources/bigBoat.png",9,19,"一艘驶向谢尔兹镇的船",1);
        mp[15][1]=new Boat("resources/bigBoat.png",15,1,"一艘驶向巴拉蒂的船",0);

    }

}

//阿龙乐园
class Map5 extends Map{
    public Map5(){

        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map5/阿龙墙.png",i,j);
            }
        }
        bgmPath="resources/map5.mp3";
        setWater(0,0,0,19);
        setWater(1,15,19,19);
        setWater(15,15,0,18);
        setWater(1,14,0,0);
        setWall("resources/map5/冰.png",1,14,1,18);
        setNull(4,7,2,5);
        setNull(9,13,2,4);
        setNull(8,8,4,4);
        setNull(13,13,5,17);
        setNull(14,14,7,11);
        setNull(9,12,15,17);
        setNull(8,8,15,15);
        setNull(4,7,14,16);
        setNull(7,7,12,13);
        setNull(6,7,9,11);
        setNull(8,8,10,10);
        setNull(9,11,6,13);
        mp[15][9]=new Boat("resources/bigBoat.png",15,9,"一艘驶向巴拉蒂的船",0);

        mp[8][4]=new Enemy("resources/map5/克罗欧比.png",8,4,"克罗欧比","草帽小子，你还是来了，阿龙大哥特派我守住这里！",null,4,2,3,5);
        mp[13][4]=new Enemy("resources/map5/海牛.png",13,4,"鱼人小兵","何人在此造次，不知道这里是阿龙大哥的地盘吗？",null,4,2,3,5);
        mp[13][15]=new Enemy("resources/map5/老鼠上校.png",13,15,"老鼠上校","你小子是来送钱的吗，和那个倒霉蛋娜美一样？",null,4,2,3,5);
        mp[8][15]=new Enemy("resources/map5/啾.png",8,15,"啾","啾，草帽小子路飞，要想见阿龙大哥，先过我这关！",null,4,2,3,5);
        mp[7][11]=new Enemy("resources/map5/阿龙.png",7,11,"阿龙","卑贱的人类，是你残害了我的同胞吗，你们从一出生，就注定被我们踩在脚下！",null,4,2,3,5);

        mp[9][3]=new Friend("resources/map5/夏莉.png",9 ,3,"夏莉","小伙子，我看你气运不错，要不要到我的占卜屋里试一试？",null);
        mp[5][15]=new Blocker("resources/map5/甚平.png",5 ,15,"甚平","年轻人，你长得好像我好朋友艾斯常常和我念叨的欧豆豆（弟弟）啊。",null);
        mp[10][9]=new Friend("resources/map5/娜美.png",10 ,9,"娜美","路飞~~你来救我了吗，呜呜呜，让我加入你们吧，我这里有个卷轴，上面记载了雷电之力，应该能起到一点帮助",null);
        Item []left;

        left=new Item[]{
                new Friend("resources/map5/coldWater.png",5 ,2,"寒水","永不冰冻的高山寒水，可以解开沙墙诅咒",null){
                    public void afterPress(){
                        Main.graph.maps.get(5).map.mp[9][15]=null;
                    }
                }
        };
        mp[5][2]=new Friend("resources/map5/蓝占卜球.png",5 ,2,"蓝占卜球","愿意为你的未来花上50贝利吗",null,left,new String[]{"money:-50"});


        mp[5][5]=new Friend("resources/map5/绿占卜球.png",5 ,5,"绿占卜球","愿意为你的未来花上50贝利吗",null,null,new String[]{"money:-50"});
        mp[7][2]=new Friend("resources/map5/红占卜球.png",7 ,2,"红占卜球","愿意为你的未来花上50贝利吗",null,null,new String[]{"money:-50"});
        mp[7][5]=new Friend("resources/map5/紫占卜球.png",7 ,5,"紫占卜球","愿意为你的未来花上50贝利吗",null,null,new String[]{"money:-50"});

        mp[11][7]=new Swords("resources/map3/书柜.png",11,7,"书柜","装满了娜美画的航海图，翻翻看，说不定有什么好东西藏着","resources/生锈的剑.mp3",4);
        mp[11][8]=new Swords("resources/map3/书柜.png",11,8,"书柜","装满了娜美画的航海图，翻翻看，说不定有什么好东西藏着","resources/生锈的剑.mp3",4);
        mp[11][11]=new Swords("resources/map3/书柜.png",11,11,"书柜","装满了娜美画的航海图，翻翻看，说不定有什么好东西藏着","resources/生锈的剑.mp3",4);
        mp[11][12]=new Swords("resources/map3/书柜.png",11,12,"书柜","装满了娜美画的航海图，翻翻看，说不定有什么好东西藏着","resources/生锈的剑.mp3",4);

        Goods[] gs=new Goods[3];
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[11][3]=new Shop("resources/商城.png",11,3,"商店","欢迎光临","resources/开门.mp3",gs);

        mp[11][16]=new Clue(11,16,"交易记录：三年前，阿龙从老鼠上校手里买到这片土地（原娜美故乡），后和老鼠上校在背后勾结哄骗娜美10000贝利可以赎回，娜美不过是他们提钱的工具罢了","resources/线索.mp3");

    }

}


//阿拉巴斯坦
class Map6 extends Map{
    public Map6(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map6/沙漠背景.png",i,j);
            }
        }
        bgmPath="resources/map6.mp3";
        setWater(0,0,0,19);
        setWater(1,15,19,19);
        setWater(15,15,0,18);
        setWater(1,14,0,0);
        setWall("resources/map6/沙漠.png",1,14,1,18);
        setNull(2,5,2,4);
        setNull(3,3,5,16);
        setNull(2,4,6,8);
        setNull(2,5,12,16);
        setNull(7,7,1,16);
        setNull(6,8,6,9);
        setNull(6,12,2,2);
        setNull(10,12,3,5);
        setNull(6,10,15,15);
        setNull(7,8,16,16);
        setNull(10,10,11,15);
        setNull(9,9,11,12);
        setNull(11,13,8,11);

        mp[7][0]=new Boat("resources/bigBoat.png",7,0,"一艘驶向小花园的船",0);


        Item []left;

        left=new Item[]{
                new Empty(10,2)
        };
        mp[10][2]=new Blocker("resources/map6/贝尔.png",10 ,2,"贝尔","隼之贝尔，奉命坚守此地。",null);
        mp[4][14]=new Friend("resources/map6/寇布拉.png",4 ,14,"寇布拉国王","草帽小子，拯救这个国家就靠你了，我可以给你我的传国玉玺，他可以保你不被我的国王军攻击，在此之前，你得先v我200贝利看看实力",null,left,null);

        mp[5][2]=new Enemy("resources/map6/Mr.4.png",5,2,"Mr.4","小子，这国家我们老大要定了，别来搅局！",null,4,2,3,5);
        mp[7][6]=new Enemy("resources/map6/反叛军小兵.png",7,6,"反叛军","你是国王那边派来的卧底吗，别怪我不客气！",null,4,2,3,5);
        mp[7][11]=new Enemy("resources/map6/寇沙.png",7,11,"寇沙","别想阻拦我，这个国家由我来拯救！",null,4,2,3,5);
        mp[7][15]=new Enemy("resources/map6/Mr.1.png",7,15,"Mr.1","我的身体可是像刀子一样锋利，当心点，小伙子。",null,4,2,3,5);
        mp[3][5]=new Enemy("resources/map6/香蕉鳄鱼.png",3,5,"香蕉鳄鱼","小子，你看起来好好吃啊，yummy~",null,4,2,3,5);
        mp[3][10]=new Enemy("resources/map6/加卡.png",3,10,"加卡","胡狼加卡在此等候多时，尔等休想取吾国王性命！",null,4,2,3,5);
        left=new Item[]{
                mp[14][8]=new Boat("resources/bigBoat.png",14,8,"一艘驶向磁鼓岛的船",1),
                mp[14][11]=new Boat("resources/bigBoat.png",14,11,"幽灵船的小艇",2)
        };
        mp[12][9]=new Enemy("resources/map6/克洛克达尔.png",12,9,"Mr.0","毛头小子罢了，沙暴！",null,4,2,3,5,left,null);
        mp[9][11]=new Friend("resources/map6/艾斯.png",9 ,11,"艾斯","呦，好久不见了呀路飞，你小子可以啊，七武海都刷起来了",null){
            public void afterPress(){
                Main.graph.maps.get(4).map.mp[5][15]=new Friend("resources/map5/甚平.png",5 ,15,"甚平","路飞君，你已经见过艾斯了吧，他把你托付给我了，既然他愿意把性命赌在你身上，那我就把这毕生所学都授予你吧，学会了鱼人空手道，能让你的物理攻击+50",null,null,new String[]{"atk:50"});
                ;
            }
        };

        left=new Item[]{
                new Friend("resources/map6/欺诈鸟（后）.png",6 ,9,"欺诈鸟","小子你也太单纯了吧，100贝利我就收下喽，嘎嘎嘎！",null)
        };
        mp[6][9]=new Friend("resources/map6/欺诈鸟（前）.png",6 ,9,"欺诈鸟","我可是大名鼎鼎的魔术师，给我100贝利，我给你变出1000贝利来！",null,left,null);
        mp[8][8]=new Friend("resources/map6/骆驼.png",8 ,8,"骆驼","看到我早上背上的行李了吗，不知道被哪个混蛋偷走了。",null);
        left=new Item[]{
                new Friend("resources/map6/罗宾（后）.png",3 ,7,"罗宾","我果然没看错你，这新时代的大船，载我一个吧，为Mr.0工作了这么久，我也收了不少佣金，这里是300贝利，你先拿去。",null)
        };
        mp[3][7]=new Blocker("resources/map6/罗宾（前）.png",3 ,7,"罗宾","草帽小子路飞，新时代的领军人物，比起协助Mr.0，我更乐意解读历史.",null);
        mp[11][4]=new Friend("resources/map6/历史正文.png",11 ,4,"历史正文","历史正文里记载了得到One Piece的关键线索，据说全部集齐便可前往拉夫德鲁，埋藏海贼王秘密的地方。",null,left,null);

        Goods[] gs=new Goods[3];
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[3][3]=new Shop("resources/商城.png",3,3,"商店","欢迎光临","resources/开门.mp3",gs);

        mp[4][8]=new Clue(4,8,"妮可·罗宾，一个对历史正文十分感兴趣的女人，要是你拿到了历史正文，说不定有机会让她助你一臂之力。","resources/线索.mp3");
        mp[5][12]=new Clue(5,12,"无能的昏君寇布拉，都是因为你，全国不得平安，我必率领一众反叛军取你狗命！——寇沙","resources/线索.mp3");
        mp[8][16]=new Clue(8,16,"“可不能让那些家伙进来，这沙墙只有存储足够的水资源才能破解，我们这个国家是不可能有的，哈哈哈哈.....”","resources/线索.mp3");

        mp[9][15]=new Blocker("resources/map6/沙墙.png",9,15,"沙墙","一堆被降下诅咒的沙子，坚不可摧，屹立不倒",null);//
    }

}

//磁鼓岛
class Map7 extends Map{
    public Map7(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map7/雪.png",i,j);
            }
        }
        bgmPath="resources/map7.mp3";
        setWater(0,0,0,19);
        setWater(1,15,19,19);
        setWater(15,15,0,18);
        setWater(1,14,0,0);
        setWall("resources/map7/雪树.png",1,14,1,18);
        setNull(1,2,4,6);
        setNull(3,4,5,5);
        setNull(5,5,3,17);
        setNull(3,7,10,14);
        setNull(4,6,8,8);
        setNull(5,10,2,2);
        setNull(5,11,17,17);
        setNull(7,10,3,5);
        setNull(10,10,6,7);
        setNull(11,13,7,7);
        setNull(9,11,14,16);
        setNull(10,10,11,13);
        setNull(11,11,11,11);
        setNull(12,13,10,12);

        mp[0][5]=new Boat("resources/bigBoat.png",0,5,"一艘驶向阿拉巴斯坦的船",0);
        mp[14][7]=new Boat("resources/bigBoat.png",14,7,"一艘驶向司法岛的船",1);

        mp[5][9]=new Friend("resources/map7/波琪.png",5 ,9,"波琪","呦呵，这不是路飞嘛，我们老大在里面等着你。",null);
        mp[9][5]=new Friend("resources/map7/多尔顿.png",9 ,5,"多尔顿","救救这个国家吧，它已经危在旦夕了。",null);
        mp[5][3]=new Blocker("resources/map7/乔巴（前）.png",5,3,"乔巴","你是什么人，我可是怪物，别靠近我！",null);

        Item []left=new Item[]{
                new Friend("resources/map7/乔巴（后）.png",5,3,"乔巴","是库蕾娃医娘让你来的吗，她说我是名医？我才不会高兴呢混蛋！",null){
                    @Override
                    public void afterPress() {
                        Main.user.coldNum=-1;
                    }
                }
        };
        mp[13][11]=new Friend("resources/map7/Dr.库蕾娃.png",13 ,11,"Dr.库蕾娃","医生？我可不是什么医生，我只是个爱喝酒的老太婆罢了，你要是真想治好风寒，去见见我那怪物徒弟吧。",null,left,null);

        mp[10][7]=new Enemy("resources/map7/瓦尔波.png",10,7,"瓦尔波","你居然治好了风寒？医生不是都被我杀了吗，可恶！",null,4,2,3,5);
        mp[5][15]=new Enemy("resources/map7/汉堡.png",5,15,"汉堡","看我大力金刚拳，捶你小胸口！",null,4,2,3,5);
        mp[10][12]=new Enemy("resources/map7/福克西.png",10,12,"福克西","呦吼吼，小草帽，戴上我的拳套了吧，来一场真男人的对决吧！",null,4,2,3,5);
        mp[7][4]=new Blocker("resources/map7/樱花树.png",7,4,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[7][5]=new Blocker("resources/map7/樱花树.png",7,5,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[10][2]=new Blocker("resources/map7/樱花树.png",10,2,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[3][11]=new Blocker("resources/map7/樱花树.png",3,11,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[3][12]=new Blocker("resources/map7/樱花树.png",3,12,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[3][13]=new Blocker("resources/map7/樱花树.png",3,13,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[7][11]=new Blocker("resources/map7/樱花树.png",7,11,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[7][12]=new Blocker("resources/map7/樱花树.png",7,12,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[7][13]=new Blocker("resources/map7/樱花树.png",7,13,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        mp[7][14]=new Blocker("resources/map7/樱花树.png",7,14,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null);
        left=new Item[]{
                new Medicine("resources/map7/棉花糖.png",10,3,"棉花糖","乔巴最爱的棉花糖，吃了可以回复50点体力。",null,50)
        };
        mp[10][3]=new Friend("resources/map7/樱花树.png",10,3,"樱花树","曾经有一位伟大的庸医，他对于医术并不精通，但是相信通过自己对科学的研究，可以让这个病危的国家重新恢复生机，这满城的樱花树便是他的遗作，他相信这些樱花树可以让人们感受到生命的美好。",null,left,null);

        Goods[] gs=new Goods[3];
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[8][4]=new Shop("resources/商城.png",8,4,"商店","欢迎光临","resources/开门.mp3",gs);

        String []change=new String[]{"buff1","money:-10"} ;

        mp[3][10]=new Friend("resources/map2/商人1.png",3,10,"远游的学者","我这里有个远海的宝物，听说可以力大无比！只卖你10个金币",null,null,change);

        change=new String[]{"buff2","money:-10"} ;
        mp[3][14]=new Friend("resources/map2/商人2.png",3,14,"怪异的巫师","快过来！别买哪个矮子的东西！我这里的宝物最上乘，能坚毅不倒！只卖你10个金币",null,null,change);

        change=new String[]{"buff3","money:-10"} ;
        mp[7][10]=new Friend("resources/map2/商人3.png",7,10,"和蔼的巫师","快过来！别买哪个瘦子的东西！我这里的宝物最上乘，能让敌人虚弱！只卖你10个金币",null,null,change);

        mp[10][14]= new Swords("resources/map7/拳套.png",10,14,"拳套","似乎是什么人故意扔下的拳套，装上攻击力+20",null,20);
        mp[9][14]=new Coin("resources/一级宝箱.png",9,14,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[9][16]=new Coin("resources/一级宝箱.png",9,16,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[11][14]=new Coin("resources/一级宝箱.png",11,14,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[11][16]=new Coin("resources/一级宝箱.png",11,16,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);

        mp[2][6]= new Clue(2,6,"寒冷的磁鼓岛，来到这里的人都会染上风寒，不及时医治的话会持续消耗你的生命力，但是就是在这个地方，却罕见的没有几个医生。",null);

        mp[4][8]= new Friend("resources/map7/火苗.png",4,8,"小火苗","可以让你在10s内不受风寒影响，但这只是缓兵之计，尽快找到医生",null){
            public void afterPress(){
                Main.user.coldNum+=10;
            }
        };
        mp[6][8]= new Friend("resources/map7/火苗.png",6,8,"小火苗","可以让你在10s内不受风寒影响，但这只是缓兵之计，尽快找到医生",null){
            public void afterPress(){
                Main.user.coldNum+=10;
            }
        };

    }

}

//幽灵岛
class Map8 extends Map{
    public Map8(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n-1;i++){
            for(int j=1;j<m-1;j++){
                floor[i][j]=new Label("resources/map8/墓地2.png",i,j);
                mask[i][j]=new Label("resources/map8/迷雾.png",i,j);
            }
        }
        bgmPath="resources/map8.mp3";
        setWater(0,0,0,19);
        setWater(1,15,19,19);
        setWater(15,15,0,18);
        setWater(1,14,0,0);
        setWall("resources/map8/墓地1.png",1,14,1,18);
        setNull(1,13,17,17);
        setNull(7,7,12,16);
        setNull(5,6,12,12);
        setNull(5,5,13,15);
        setNull(2,4,15,15);
        setNull(2,2,2,14);
        setNull(3,5,2,2);
        setNull(3,5,4,4);
        setNull(3,5,6,6);
        setNull(3,7,10,10);
        setNull(4,4,8,9);
        setNull(5,6,8,8);
        setNull(6,6,7,7);
        setNull(7,9,9,9);
        setNull(9,9,6,9);
        setNull(7,8,6,6);
        setNull(7,7,2,5);
        setNull(8,14,2,2);
        setNull(13,13,15,16);
        setNull(9,12,15,15);
        setNull(12,12,13,14);
        setNull(11,13,13,13);
        setNull(9,9,11,15);
        setNull(10,13,11,11);
        setNull(13,13,4,10);
        setNull(9,12,4,4);
        setNull(11,11,5,9);

        mp[0][17]=new Boat("resources/bigBoat.png",0,17,"一艘驶向阿拉巴斯坦的船",0);
        mp[15][2]=new Boat("resources/bigBoat.png",15,2,"一艘驶向司法岛的船",1);

        mp[13][2]=new Blocker("resources/map8/上锁的门.png",13,2,"上锁的门","你需要一把钥匙来打开它",null);
        //堵着钥匙的障碍物
        mp[11][4]=new Blocker("resources/map8/大木箱.png",11,4,"被大木箱堵住了","被堵住了，想办法移开它，或者找人帮忙",null);


        mp[7][2]=new Enemy("resources/map8/月光莫利亚.png",7,2,"月光莫利亚","没想到能碰到你啊，草帽路飞，就是你打败了沙鳄鱼吗，我可不会手下留情！",null,4,2,3,5);
        mp[13][17]=new Enemy("resources/map8/地狱三头犬.png",13,17,"地狱犬","我们是狐狸和狼，不是狗！",null,4,2,3,5);
        mp[13][7]=new Enemy("resources/map8/僵尸罗拉.png",13,7,"僵尸罗拉","那小姑娘的影子在我这，有本事就来拿！",null,4,2,3,5);
        mp[9][11]=new Enemy("resources/map8/僵尸树.png",9,11,"僵尸树","僵尸，僵尸，我就是个僵尸诶！",null,4,2,3,5);
        mp[2][15]=new Enemy("resources/map8/巴索罗米熊.png",2,15,"巴索罗米·熊","我奉命来此调查，这里很多人的影子都消失了，一旦出现在阳光下，他们便会焚烧殆尽，更可怕的是，有人拿这些影子专门制作僵尸人偶。",null,4,2,3,5);
        mp[5][10]=new Enemy("resources/map8/库玛西.png",5,10,"库玛西","佩***娜****大人***",null,4,2,3,5);

        Item []left=new Item[]{
                new Enemy("resources/map8/霍古巴克.png",4,9,"霍古巴克","莫利亚大人的直觉果然没错，有小老鼠溜进来了。",null,4,2,3,5)
        };
        mp[5][8]=new Friend("resources/map8/假装迷路的村民.png",5 ,8,"假装迷路的村民","你知道这里的王吗，别惊扰了它！",null,left,null);


        left=new Item[]{
                new Friend("resources/map8/布鲁克.png",6 ,7,"布鲁克","呦吼吼吼，谢谢你帮我找回影子，路飞桑，在你的身上我看到了20几年前那个大人物的影子，请收下我的小提琴，它肯定可以派上用场。",null,new Item[]{
                        new Swords("resources/map8/布鲁克的小提琴.png",6,7,"布鲁克的小提琴","布鲁克的至宝小提琴，可以对敌人造成精神伤害20点","resources/生锈的剑.mp3",20)
                },null)

        };
        mp[6][7]=new Blocker("resources/map8/布鲁克.png",6,7,"布鲁克","呦吼吼吼，我是吃了黄泉果实的音乐家布鲁克，能帮我找回影子吗？我可以教你我的成名曲哦，宾克斯的萨内诶~",null);
        mp[10][9]=new Enemy("resources/map8/霜月龙马.png",10,9,"霜月龙马","你有见过那个骷髅头吗，真是没骨气的家伙。",null,4,2,3,5,left,null);

        left=new Item[]{
                new Friend("resources/map8/罗拉.png",5 ,4,"罗拉","我果真没有看错，谢谢你少年！我已经帮你移开那个大木箱了，勇往直前吧！",null,new Item[]{
                        new Empty(11,4)
                },null)

        };
        mp[5][4]=new Blocker("resources/map8/罗拉.png",5 ,4,"罗拉","你能帮我找回我的影子吗，我天生力大，找回影子后，说不定可以帮你移开某些东西",null);
        mp[13][7]=new Enemy("resources/map8/僵尸罗拉.png",13,7,"僵尸罗拉","那小姑娘的影子在我这，有本事就来拿！",null,4,2,3,5,left,null);
        mp[7][17]=new Friend("resources/map8/佩罗娜.png",7 ,17,"佩罗娜","这可不是你这种小孩能来的地方，别把命留在这了！",null);

        left=new Item[]{
                new Friend("resources/map8/僵尸.png",12,14,"僵尸","客人，请进！",null)
        };
        mp[12][14]=new Blocker("resources/map8/僵尸.png",12,14,"僵尸","你见到阿布萨罗姆大人了吗，他可是墓地之王，没有他的指令，我可不敢放你过去。",null);
        mp[2][8]=new Enemy("resources/map8/阿布萨罗姆.png",2,8,"阿布萨罗姆","不给我推妹子的微信，小爷我可不放你过去！",null,4,2,3,5,left,null);

        left=new Item[]{
                new Empty(13,2)
        };
        mp[9][4]=new Friend("resources/map4/钥匙.png",9 ,4,"钥匙","看起来似乎是某个门的钥匙",null,left,null);

        mp[5][12]=new Coin("resources/一级宝箱.png",5,12,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[5][6]=new Coin("resources/一级宝箱.png",5,6,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[11][13]=new Coin("resources/一级宝箱.png",11,13,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[13][13]=new Coin("resources/一级宝箱.png",13,13,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);
        mp[10][4]=new Coin("resources/一级宝箱.png",10,4,"宝箱","一堆金币，可能五枚？两枚？好吧可能是空的...","resources/打开宝箱.mp3",10);

        mp[5][2]=new Clue(5,2,"这里的影子僵尸也分等级，据说有个曾经是世界第一剑豪的家伙也被制作成僵尸了。","resources/线索.mp3");

    }

}

//司法岛
class Map9 extends Map{
    public Map9(){
        floor=new Label[n][m];
        mask=new Label[n][m];
        mp=new Item[n][m];
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++){
                floor[i][j]=new Label("resources/map9/大理石白（人）.png",i,j);
            }
        }
        bgmPath="resources/map9.mp3";
        setWater(0,0,0,19);
        setWater(1,15,19,19);
        setWater(15,15,0,18);
        setWater(1,14,0,0);
        setWall("resources/map9/goldWall.png",1,14,1,18);
        setNull(6,6,1,5);
        setNull(5,7,2,3);
        setNull(2,11,3,3);
        setNull(9,11,4,5);
        setNull(2,3,3,5);
        setNull(1,1,4,4);
        setNull(3,3,6,12);
        setNull(2,4,9,12);
        setNull(5,7,12,12);
        setNull(5,7,6,8);
        setNull(7,7,9,16);
        setNull(6,8,14,16);
        setNull(8,11,9,9);
        setNull(9,11,8,10);
        setNull(11,11,11,17);
        setNull(10,13,13,17);


        mp[6][0]=new Boat("resources/bigBoat.png",6,0,"一艘驶向磁鼓岛的船",0);
        mp[0][4]=new Boat("resources/bigBoat.png",0,4,"幽灵船的小艇",1);

        mp[3][6]=new Enemy("resources/map9/布鲁诺.png",3,6,"布鲁诺","可曾听说过海军六式，其中的铁块我可是修炼到了极致，要来试试吗？",null,4,2,3,5);
        mp[3][3]=new Clue(3,3,"司法岛，据说是世界政府的三大核心机构之一，目前由世界政府直辖的组织CP9暗中操控着。","resources/线索.mp3");

        String []change=new String[]{"buff1","money:-10"} ;

        mp[5][6]=new Friend("resources/map2/商人1.png",5,6,"远游的学者","我这里有个远海的宝物，听说可以力大无比！只卖你10个金币",null,null,change);

        change=new String[]{"buff2","money:-10"} ;
        mp[5][8]=new Friend("resources/map2/商人2.png",5,8,"怪异的巫师","快过来！别买哪个矮子的东西！我这里的宝物最上乘，能坚毅不倒！只卖你10个金币",null,null,change);

        change=new String[]{"buff3","money:-10"} ;
        mp[7][6]=new Friend("resources/map2/商人3.png",7,6,"和蔼的巫师","快过来！别买哪个瘦子的东西！我这里的宝物最上乘，能让敌人虚弱！只卖你10个金币",null,null,change);

        mp[6][3]=new Friend("resources/map9/卡莉法.png",6 ,3,"卡莉法","哪里来的小老鼠，这可不是你这等闲之辈可以来的地方！",null);
        mp[7][12]=new Blocker("resources/map9/卡库.png",7,12,"卡库","匹诺曹？我可没听说过，你以为我9年义务教育白上的？",null);
        mp[9][3]=new Blocker("resources/map9/怪物乔巴.png",9,3,"失控的乔巴","蓝.....波......球.....",null);
        mp[8][9]=new Blocker("resources/map8/上锁的门.png",8,9,"上锁的门","想办法打开它。",null);
        mp[2][10]=new Blocker("resources/map3/书柜.png",2,10,"书柜","记载了如何控制自身，再找找。",null);
        mp[2][11]=new Blocker("resources/map3/书柜.png",2,11,"书柜","记载了如何控制自身，再找找。",null);
        mp[4][10]=new Blocker("resources/map3/书柜.png",4,10,"书柜","记载了如何控制自身，再找找。",null);


        Item []left=new Item[]{
                new Friend("resources/map9/蓝波球.png",4 ,11,"蓝波球","乔巴秘制的定心丸，快去为他服用！",null,new Item[]{
                        new Friend("resources/map9/乔巴（后）.png",9,3,"乔巴","阿里嘎多，路飞桑~","resources/生锈的剑.mp3")
                },null)

        };
        mp[4][11]=new Friend("resources/map3/书柜.png",4,11,"书柜","记载了如何控制自身，再找找。",null,left,null);

        left=new Item[]{
                new Enemy("resources/map9/卡莉法（后）.png",6,3,"觉醒的卡莉法","擅闯司法岛者，格杀勿论。",null,4,2,3,5),
                new Enemy("resources/map9/卡库（后）.png",7,12,"觉醒的卡库","擅闯司法岛者，格杀勿论。",null,4,2,3,5)

        };
        mp[6][7]=new Friend("resources/map9/斯潘达姆.png",6,7,"斯潘达姆","你是谁，我怎么没见过你？我可是CP9的长官，有入侵者，卡莉法、卡库，快服用我为你们准备的恶魔果实！",null,left,null);

        left=new Item[]{
                new Empty(8,9)
        };
        mp[11][5]=new Friend("resources/map4/钥匙.png",11,5,"钥匙","似乎可以打开某扇门。",null,left,null);

        Goods[] gs=new Goods[3];
        gs[0]=new Goods("resources/血瓶.png","hp",10,"money",5);
        gs[1]=new Goods("resources/木盾.png","dfc",3,"money",5);
        gs[2]=new Goods("resources/弓箭.png","atk",3,"money",5);
        mp[10][4]=new Shop("resources/商城.png",10,4,"商店","欢迎光临","resources/开门.mp3",gs);

        mp[7][15]=new Swords("resources/map9/审判之剑.png",7,15,"审判之剑","司法岛的镇岛之宝，传说是斩下史上最恶劣的罪犯的宝剑，装上+攻击力50.",null,50);
        mp[10][9]=new Enemy("resources/map9/路奇.png",10,9,"路奇","你是怎么进来的，难道司法岛被攻破了？",null,4,2,3,5);
        mp[11][11]=new Enemy("resources/map9/变身后的路奇.png",11,11,"觉醒的路奇","还没完呢，你当真以为CP9是吃素的？",null,4,2,3,5);

        mp[10][13]=new Friend("resources/map9/香克斯.png",10 ,13,"香克斯","好久不见啊路飞，看起来我们要站在对立面了呀，哈哈哈哈，对了，前段时间我参演了大电影《红发歌姬》，你小子有没有去看啊？",null);
        mp[10][14]=new Friend("resources/map9/白胡子.png",10 ,14,"爱德华·纽盖特","咕啦啦啦啦，年轻人，新时代已经没有能够承载我的船了，One Piece是真实存在的，接下来就靠你了！",null);
        mp[10][15]=new Friend("resources/map9/黑胡子.png",10 ,15,"马歇尔·D·蒂奇","贼哈哈哈哈，人的梦想是不会终结的，你很有骨气，但是，海贼王肯定是我！",null);
        mp[13][13]=new Friend("resources/map9/罗杰.png",13 ,13,"哥尔·D·罗杰","年轻人，你终于来了，想要我留下的秘宝吗？继续前行吧，One Piece在等着你！",null);
        mp[13][14]=new Friend("resources/map9/凯多.png",13 ,14,"凯多","呦喽喽喽喽，海贼王吗？就凭你这小子还不够。",null);
        mp[13][15]=new Friend("resources/map9/Big Mom.png",13 ,15,"夏洛特·玲玲","mama，mama，凯多，你听说了吗，这小子要当海贼王，真是笑死人！",null);

        mp[11][16]=new Blocker("resources/map6/历史正文.png",11 ,16,"历史正文","恭喜吃货路飞到此已经全部通关，而要想成为海贼王，不是要打败所有人，而是要成为这片大海上最自由的人，在此祝贺通关选手事事顺心、平安快乐。",null);





    }

}


