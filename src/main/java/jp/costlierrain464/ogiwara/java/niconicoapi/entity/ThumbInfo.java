package jp.costlierrain464.ogiwara.java.niconicoapi.entity;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by ogiwara on 2017/03/08.
 */
//TODO 本家のXmlシリアライザーに戻す
public class ThumbInfo {
    public String id;
    public String title;
    public String description;
    public String thumbnailUrl;
    public String firstRetrieve;
    public String length;
    public String movieType;
    public long sizeHigh;
    public long sizeLow;
    public long viewCounter;
    public long commentNum;
    public long mylistCounter;
    public String lastResBody;
    public String watchUrl;
    public String thumbType;
    public boolean embeddable;
    public boolean noLivePlay;
    public String[] tags;
    public String userId;
    public String userNickName;
    public String userIconUrl;

    /*public static ThumbInfo parse(String xml) {
        ThumbInfo info = new ThumbInfo();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Root root = JAXB.unmarshal(in, Root.class);//NONClassdef??
            Thumb tb = root.thumb;

            info.id = tb.video_id;
            info.title = tb.title;
            info.description = tb.description;
            info.thumbnailUrl = tb.thumbnail_url;
            info.firstRetrieve = tb.first_retrieve;
            info.length = tb.length;
            info.movieType = tb.movie_type;
            info.sizeHigh = tb.size_high;
            info.sizeLow = tb.size_low;
            info.viewCounter = tb.view_counter;
            info.commentNum = tb.comment_num;
            info.mylistCounter = tb.mylist_counter;
            info.lastResBody = tb.last_res_body;
            info.watchUrl = tb.watch_url;
            info.thumbnailUrl = tb.thumbnail_url;
            info.thumbType = tb.thumb_type;
            info.userNickName = tb.user_nickname;
            info.userIconUrl = tb.user_icon_url;
            if (tb.embeddable.equals("1")) {
                info.embeddable = true;
            } else {
                info.embeddable = false;
            }
            if (tb.no_live_play.equals("1")) {
                info.noLivePlay = true;
            } else {
                info.noLivePlay = false;
            }
            info.userId = tb.user_id;
            info.tags = new String[tb.tags.tag.size()];
            for (int i = 0; i < info.tags.length; i++) {
                info.tags[i] = tb.tags.tag.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }*/

    public static ThumbInfo parse2(String xml2){
        XStream xs = new XStream();
        ThumbInfo info = new ThumbInfo();
        xs.useAttributeFor(NicoVideoThumbResponce.class,"status");
        xs.alias("nicovideo_thumb_response",NicoVideoThumbResponce.class);
        xs.alias("thumb",Thumb.class);
        xs.alias("tag",Tag.class);
        Thumb tb = ((NicoVideoThumbResponce) xs.fromXML(xml2)).thumb;

        info.id = tb.video_id;
        info.title = tb.title;
        info.description = tb.description;
        info.thumbnailUrl = tb.thumbnail_url;
        info.firstRetrieve = tb.first_retrieve;
        info.length = tb.length;
        info.movieType = tb.movie_type;
        info.sizeHigh = tb.size_high;
        info.sizeLow = tb.size_low;
        info.viewCounter = tb.view_counter;
        info.commentNum = tb.comment_num;
        info.mylistCounter = tb.mylist_counter;
        info.lastResBody = tb.last_res_body;
        info.watchUrl = tb.watch_url;
        info.thumbnailUrl = tb.thumbnail_url;
        info.thumbType = tb.thumb_type;
        info.userNickName = tb.user_nickname;
        info.userIconUrl = tb.user_icon_url;
        if (tb.embeddable.equals("1")) {
            info.embeddable = true;
        } else {
            info.embeddable = false;
        }
        if (tb.no_live_play.equals("1")) {
            info.noLivePlay = true;
        } else {
            info.noLivePlay = false;
        }
        info.userId = tb.user_id;
        return  info;
    }

    public class NicoVideoThumbResponce{

        public String status;

        public Thumb thumb;
    }


    public static class Root {
        public Thumb thumb;
    }

    public class Thumb {
        public String video_id;

        public String title;

        public String description;

        public String thumbnail_url;

        public String first_retrieve;

        public String length;

        public String movie_type;

        public long size_high;

        public long size_low;

        public long view_counter;

        public long comment_num;

        public long mylist_counter;

        public String last_res_body;

        public String watch_url;

        public String thumb_type;

        public String embeddable;

        public String no_live_play;

        public List<Tag> tags;

        public String user_id;

        public String user_nickname;

        public String user_icon_url;

    }

    public class Tag {
        public String content;
    }
}
