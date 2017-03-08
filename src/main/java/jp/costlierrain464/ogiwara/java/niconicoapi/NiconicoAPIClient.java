package jp.costlierrain464.ogiwara.java.niconicoapi;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.CommentInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.entity.FlvInfo;
import jp.costlierrain464.ogiwara.java.niconicoapi.entity.MyListItem;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoGetAllMyList;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoGetComment;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoGetFlv;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoLogin;

import java.io.IOException;
import java.util.List;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NiconicoAPIClient {

    public NiconicoAPIClient(){
        WebClient.init();
    }

    /**
     *ログイン
     *
     *@param mail メアド
     *@param pass パスワード
     *@throws NiconicoException,IOException
     */
    public void login(String mail,String pass) throws NiconicoException,IOException{
        new NicoLogin().execute(mail,pass);
    }

    /**
     *マイリストをすべて取得
     *
     * @return マイリストのアイテム
     * @throws IOException
     */
    public List<MyListItem> getAllMyList() throws IOException{
        return new NicoGetAllMyList().getAllMyList();
    }

    /**
     * Flv情報取得
     *
     * @param id 動画id
     * @return Flv情報
     * @throws NiconicoException,IOException
     */
    public FlvInfo getFlv(String id) throws NiconicoException,IOException{
        return new NicoGetFlv().execute(id);
    }

    /**
     * コメント取得
     *
     * @param id 動画id
     * @return コメント
     * @throws NiconicoException,IOException
     */
    public List<CommentInfo> getComment(String id) throws IOException,NiconicoException{
        return new NicoGetComment(getFlv(id)).execute(id);
    }

    /**
     * 動画情報取得
     *
     * @param id 動画id
     * @return 動画情報
     * @throws NiconicoException,IOException
     */

}
