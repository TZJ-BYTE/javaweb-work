import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Manager {
    public static void main(String[] args) {
        Usezip uzip=new Usezip();
        //向数据库传输所有的拼图图片和音频文件
        //uzip.uploadZipFile("allzip.zip");
        //需要上传的文件太大，无法上传
        try {
            // 尝试下载数据文件
            uzip.downloadZipFile("/", "allzip.zip");
            // 尝试解压数据文件
            uzip.zips("allzip.zip", "/");
            System.out.println("ZIP文件下载和解压成功");
        } catch (Exception e) {
            System.out.println("ZIP文件下载或解压失败，将继续执行其他任务...");
            e.printStackTrace(); // 打印异常信息
        }
        new Window();
        giveGameServer();

    }
    public static void giveGameServer(){
        new GameServer();
    }
    public static void giveServerStatus(){
        new ServerStatus();
    }
}