package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.ThumbInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;

import java.io.IOException;

/**
 * Created by ogiwara on 2017/03/09.
 */
public class NicoGetThumbInfo {
    private String methodUrl = "http://ext.nicovideo.jp/api/getthumbinfo/";

    public ThumbInfo execute(String id) throws IOException,NiconicoException{
        try(WebClient client = WebClient.get(methodUrl+id)){
            String xml = client.getEntity();
            return ThumbInfo.parse(xml);
        }
    }
}
