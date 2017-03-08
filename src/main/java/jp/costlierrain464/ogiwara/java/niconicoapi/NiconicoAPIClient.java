package jp.costlierrain464.ogiwara.java.niconicoapi;

import jp.costlierrain464.ogiwara.java.niconicoapi.entity.MyListItem;
import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoGetAllMyList;
import jp.costlierrain464.ogiwara.java.niconicoapi.method.NicoLogin;

import java.io.IOException;
import java.util.List;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NiconicoAPIClient {

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
}
