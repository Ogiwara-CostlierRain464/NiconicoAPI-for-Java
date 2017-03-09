# NiconicoAPI-for-Java
NiconicoAPI for Java

- Androidの64K問題回避の為に作成
- AndroidでApache HttpClientが非推奨になったので自作のWebClientを作成

##samples

ログイン
ほぼすべてのAPIの実行にはログインが必要
```
NiconicoAPIClient client = new NIconicoAPIClient();
client.login("mail address","password");
```

コメント取得
```
List<CommentInfo> list = client.getComment("sm30737982");
```

アカウントのすべてのマイリストを取得
```
List<MyListItem> items = client.getAllMyList();
```

動画のダウンロード
```
client.downloadVideo("sm30737982","パス");
```


その他の機能は現在作成中

>参考: https://github.com/fujiriko59/nico-api-client
