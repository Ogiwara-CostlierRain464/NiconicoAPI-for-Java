package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.FlvInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.entity.ThumbInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;

import java.io.*;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ogiwara on 2017/03/09.
 */
public class NicoDownloadVideo {
    private String videoUrl = "http://www.nicovideo.jp/watch/";
    private String flapiUrl = "http://flapi.nicovideo.jp/api/getflv/";
    private FlvInfo flvInfo;
    private ThumbInfo thumbInfo;
    private String videoId;
    public NicoDownloadVideo(String videoid,FlvInfo f,ThumbInfo t){
        videoId = videoid;
        flvInfo = f;
        thumbInfo = t;
    }

    public File execute(String destDir) throws NiconicoException,IOException{
        File dir = new File(destDir);

        if(!dir.isDirectory())
            throw new NiconicoException(destDir + " is not Directory");

        String fileName = thumbInfo.title;
        String fileType = thumbInfo.movieType;
        try(WebClient client = WebClient.get(videoUrl+videoId)){
            client.getEntity();
        }
        String entity = "";
        try(WebClient client = WebClient.get(flapiUrl+videoId)){
            entity = client.getEntity();
        }
        Pattern p1 = Pattern.compile("&url=.*");
        Matcher m1 = p1.matcher(entity);
        String url = "";
        if(m1.find()){
            int start = m1.start();
            int end = m1.end();
            url = entity.substring(start,end).replace("&url=","");
        }

        try(WebClient client = WebClient.get(URLDecoder.decode(url,"UTF-8"))){
            DataInputStream in = new DataInputStream(client.connection.getInputStream());
            DataOutputStream dataout = new DataOutputStream(new BufferedOutputStream(
                    new FileOutputStream(dir.getAbsoluteFile() + File.separator + fileName + "." + fileType)
            ));
            byte[] b = new byte[4096];
            int readByte = 0;
            while(-1 != (readByte = in.read(b)))
                dataout.write(b,0,readByte);

            in.close();
            dataout.close();
            return new File(dir.getAbsoluteFile() + File.separator + fileName + "." + fileType);
        }
    }
}
