package main;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bgm implements Runnable{
    protected String path;
    private Player player ;
    @Override
    public void run() {
        try {
            while(true){
                System.out.println("开始播放");
                player = new Player(new FileInputStream(new File(path)));
                player.play();
                System.out.println("结束播放"+ player.toString());
            }
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public Bgm(String path){
        this.path=path;
    }
}
class Sound implements Runnable{
    protected String path;
    protected Player player ;
    @Override
    public void run() {
        try {
            player = new Player(new FileInputStream(new File(path)));
            player.play();
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public Sound(String path){
        this.path=path;
    }

}


