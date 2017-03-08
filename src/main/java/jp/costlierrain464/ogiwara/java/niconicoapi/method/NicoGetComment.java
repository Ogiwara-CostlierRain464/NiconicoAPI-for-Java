package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.CommentInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.entity.FlvInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NicoGetComment {
    private String threadKeyUrl = "http://flapi.nicovideo.jp/api/getthreadkey?thread=";
    private String waybackKeyUrl = "http://flapi.nicovideo.jp/api/getwaybackkey?thread=";
    private FlvInfo flvInfo = null;

    public NicoGetComment(FlvInfo i){
        flvInfo = i;
    }

    public List<CommentInfo> execute(String id) throws NiconicoException,IOException {
        return execute(id,null);
    }

    public List<CommentInfo> execute(String id,Date date) throws NiconicoException,IOException{
        List<CommentInfo> list = new ArrayList<CommentInfo>();
        String threadKey = null;
        String force_184 = null;

        try(WebClient client1 = WebClient.get(threadKeyUrl+flvInfo.threadId)){
            String[] teps =client1.getEntity().split("&");
            for(String tmp : teps){
                String[] pair = tmp.split("=");
                if ("threadkey".equals(pair[0])) {
                    if (pair.length < 2 || StringUtils.isBlank(pair[1])) {
                        threadKey = null;
                    } else {
                        threadKey = pair[1];
                    }
                } else if ("force_184".equals(pair[0])) {
                    if (pair.length < 2 || StringUtils.isBlank(pair[1])) {
                        force_184 = null;
                    } else {
                        force_184 = pair[1];
                    }
                }
            }
        }

        String waybackKey = null;
        long when = 0;
        if(date != null){
            try(WebClient client = WebClient.get(waybackKeyUrl + flvInfo.threadId)){
                String[] teps = client.getEntity().split("&");
                for(String tmp : teps){
                    String[] pair = tmp.split("=");
                    if ("waybackkey".equals(pair[0])) {
                        if (pair.length < 2 || StringUtils.isBlank(pair[1])) {
                            waybackKey = null;
                        } else {
                            waybackKey = pair[1];
                        }
                    }
                }
                when = date.getTime() / 1000;
            }
        }

        long length = (flvInfo.l / 60) + 1;
        /*StringBuilder xml = new StringBuilder();
        if (StringUtils.isBlank(threadKey)) {
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xml.append("<packet>");
            xml.append("<thread ");
            xml.append("thread=\"" + flvInfo.threadId + "\" ");
            xml.append("version=\"20090904\" ");
            if (StringUtils.isNotBlank(waybackKey)) {
                xml.append("waybackkey=\"" + waybackKey + "\" ");
                xml.append("when=\"" + when + "\" ");
            }
            xml.append("user_id=\"" + flvInfo.userId + "\"");
            xml.append("/>");
            xml.append("<thread_leaves ");
            xml.append("thread=\"" + flvInfo.threadId + "\" ");
            if (StringUtils.isNotBlank(waybackKey)) {
                xml.append("waybackkey=\"" + waybackKey + "\" ");
                xml.append("when=\"" + when + "\" ");
            }
            xml.append("user_id=\"" + flvInfo.userId + "\"");
            xml.append(">");
            xml.append("0-" + length + ":100,1000");
            xml.append("</thread_leaves>");
            xml.append("</packet>");
        } else {
            // TODO 動作確認してない
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xml.append("<packet>");
            xml.append("<thread ");
            xml.append("thread=\"" + flvInfo.threadId + "\" ");
            xml.append("version=\"20090904\" ");
            if (StringUtils.isNotBlank(waybackKey)) {
                xml.append("waybackkey=\"" + waybackKey + "\" ");
                xml.append("when=\"" + when + "\" ");
            }
            xml.append("user_id=\"" + flvInfo.userId + "\"　");
            xml.append("threadkey=\"" + threadKey + "\"　");
            if (StringUtils.isNotBlank(force_184)) {
                xml.append("force_184=\"" + force_184 + "\"");
            }
            xml.append("/>");
            xml.append("<thread_leaves ");
            xml.append("thread=\"" + flvInfo.threadId + "\" ");
            if (StringUtils.isNotBlank(waybackKey)) {
                xml.append("waybackkey=\"" + waybackKey + "\" ");
                xml.append("when=\"" + when + "\" ");
            }
            xml.append("user_id=\"" + flvInfo.userId + "\" ");
            xml.append("threadkey=\"" + threadKey + "\"　");
            if (StringUtils.isNotBlank(force_184)) {
                xml.append("force_184=\"" + force_184 + "\"");
            }
            xml.append(">");
            xml.append("0-" + length + ":100,1000");
            xml.append("</thread_leaves>");
            xml.append("</packet>");
        }*///仕様変更
        //TODO 再確認
        //>引用 http://qiita.com/tor4kichi/items/2f19f533763fe6e3e479

        try(WebClient client = WebClient.get(flvInfo.ms + "thread?thread=" +
                flvInfo.threadId + "&version=20061206&res_from=-1000&scores=1&threadkey="+
                threadKey + "&force_184=" + force_184 + "&user_id=" + flvInfo.userId
                )){
            String responseXml = client.getEntity();
            list = CommentInfo.parse(id,responseXml);
        }
        //TODO: コメントが取得できない　クッキー関連?
        return list;
    }
}
