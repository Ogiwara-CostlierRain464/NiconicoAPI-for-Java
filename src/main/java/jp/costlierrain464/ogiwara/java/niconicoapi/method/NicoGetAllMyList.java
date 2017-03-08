package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.MyListItem;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;

import java.io.IOException;
import java.util.List;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NicoGetAllMyList {
    final private String deflistUrl = "http://www.nicovideo.jp/api/deflist/list";
    final private String mylistgroupUrl = "http://nicovideo.jp/api/mylistgroup/list";
    final private String mylistUrl = "http://nicovideo.jp/api/mylist/list?group_id=";

    public List<MyListItem> getAllMyList() throws IOException{
        try(WebClient client = WebClient.get(deflistUrl)){
            String result = client.getEntity();
            return MyListItem.parse(result);
        }
    }
}
