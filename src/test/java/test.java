import jp.costlierrain464.ogiwara.java.niconicoapi.NiconicoAPIClient;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class test {

    static String MAIL = "メアド";
    static String PASS ="パスワード";

    static String VIDEO_ID = "sm30737982";

    public static void main(String[] args){
        NiconicoAPIClient client = new NiconicoAPIClient();
        try{
            client.login(MAIL,PASS);
            client.getComment(VIDEO_ID).stream().forEach(e->{
                puts(e.msg);
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void puts(String s) {
        System.out.println(s);
    }
}
