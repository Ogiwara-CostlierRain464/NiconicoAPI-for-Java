import com.github.axet.vget.VGet;
import jp.costlierrain464.ogiwara.java.niconicoapi.NiconicoAPIClient;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class test {

    static String MAIL = "メアド";
    static String PASS ="パスワード";

    static String VIDEO_ID = "sm30737982";

    public static void main(String[] args){
        WebClient.init();
        try{
            youtubeDownload();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void download(){
        NiconicoAPIClient client = new NiconicoAPIClient();
        try{
            client.login(MAIL,PASS);
            client.downloadVideo("sm30737982","C:\\Users\\ogiwara\\Downloads\\Niko");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void youtube() throws Exception{
        try(WebClient client = WebClient.get("http://www.youtube.com/get_video_info?video_id=" + "n1a7o44WxNo" + "&eurl="
                + URLEncoder.encode("https://youtube.googleapis.com/v/" + "n1a7o44WxNo", "UTF-8"))){
            puts(URLDecoder.decode(client.getEntity(),"UTF-8"));
        }
    }


    public static void youtubeDownload() throws IOException{
        String link = "https://www.youtube.com/watch?v=hz5N_5r-zQA";
        String path = "C:\\Users\\ogiwara\\Downloads\\Niko";
        VGet g = new VGet(new URL(link),new File(path));
        g.download();
    }

    public static void puts(String s) {
        System.out.println(s);
    }
}
