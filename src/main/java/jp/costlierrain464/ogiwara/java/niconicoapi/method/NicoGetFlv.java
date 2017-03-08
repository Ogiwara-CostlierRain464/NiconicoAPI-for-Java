package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.FlvInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NicoGetFlv {
    private String methodUrl = "http://flapi.nicovideo.jp/api/getflv/";

    public FlvInfo execute(String id) throws IOException,NiconicoException{
        StringBuilder url = new StringBuilder(methodUrl);
        url.append(id);
        if(id.startsWith("nm"))
            url.append("?as3=1");

        try(WebClient client = WebClient.get(url.toString())){
            String entity = client.getEntity();
            String[] results = entity.split("&");
            FlvInfo info = new FlvInfo();
            for(String str : results){//Refrectionでどうにかならない?
                String[] pair = str.split("=");
                pair[0] = URLDecoder.decode(pair[0], "UTF-8");
                if (pair.length == 2) {
                    pair[1] = URLDecoder.decode(pair[1], "UTF-8");
                }

                if ("thread_id".equals(pair[0])) {
                    info.threadId = pair[1];
                } else if ("l".equals(pair[0])) {
                    info.l = Integer.parseInt(pair[1]);
                } else if ("url".equals(pair[0])) {
                    info.url = pair[1];
                } else if ("link".equals(pair[0])) {
                    info.link = pair[1];
                } else if ("ms".equals(pair[0])) {
                    info.ms = pair[1];
                } else if ("ms_sub".equals(pair[0])) {
                    info.msSub = pair[1];
                } else if ("user_id".equals(pair[0])) {
                    info.userId = pair[1];
                } else if ("is_premium".equals(pair[0])) {
                    info.isPremium = pair[1];
                } else if ("time".equals(pair[0])) {
                    info.time = Long.parseLong(pair[1]);
                } else if ("done".equals(pair[0])) {
                    info.done = Boolean.parseBoolean(pair[1]);
                } else if ("hms".equals(pair[0])) {
                    info.hms = pair[1];
                } else if ("hmsp".equals(pair[0])) {
                    info.hmsp = pair[1];
                } else if ("hmst".equals(pair[0])) {
                    info.hmst = pair[1];
                } else if ("hmstk".equals(pair[0])) {
                    info.hmstk = pair[1];
                } else if ("rpu".equals(pair[0])) {
                    info.rpu = pair[1];
                }
            }
            if(StringUtils.isBlank(info.threadId))
                throw new NiconicoException("Failed to get threadId");
            return info;
        }
    }
}
