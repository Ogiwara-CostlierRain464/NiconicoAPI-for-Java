package jp.costlierrain464.ogiwara.java.niconicoapi.http;

import java.io.IOException;
import java.net.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class CorrectedCookieManager extends CookieManager {
    protected static Locale defaultLocale = Locale.getDefault();
    protected static String testHeader = "Set-Cookie: session=session_543210_1234567890; expires=Fri, 18-Sep-2009 11:44:26 GMT; path=/; domain=.testvideo.jp";
    //	protected static String testHeader = "Set-Cookie: user_session=deleted; expires=Fri, 18-Sep-2009 11:44:26 GMT; path=/; domain=.testvideo.jp";
//	protected static String testHeader = "Set-Cookie: session=session_543210_1234567890; expires=Mon, 18-Oct-2010 11:44:27 GMT; path=/; domain=.testvideo.jp";
    protected static String expirePattern = "EEE, dd-MMM-yyyy HH:mm:ss 'GMT'";
    protected static SimpleDateFormat expireFormat = new SimpleDateFormat(expirePattern, Locale.US);
    protected static Comparator<String> expireComparator = new Comparator<String>(){
        @Override
        public int compare(String o1, String o2) {
            Date date1 = expireFormat.parse(o1, new ParsePosition(o1.indexOf("expires=")+8));
            Date date2 = expireFormat.parse(o2, new ParsePosition(o2.indexOf("expires=")+8));
            long time1 = date1==null ? new Date().getTime() : date1.getTime();
            long time2 = date2==null ? new Date().getTime() : date2.getTime();
            return (int)(time2 - time1);
        }
    };

    public static boolean isParsable(){
        List<HttpCookie> list = HttpCookie.parse(testHeader);
        return  list!=null && list.size()>0 && list.get(0).getMaxAge()!=0;
    }


    protected boolean parsable = false;

    public CorrectedCookieManager() {
        super();
        this.parsable = isParsable();
    }
    public CorrectedCookieManager(CookieStore cookieStore, CookiePolicy cookiePolicy) {
        super(cookieStore, cookiePolicy);
        this.parsable = isParsable();
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders)
            throws IOException {
		/* responseHeaderは変更不可なMapなので,新規にコピーを作成 */
        Map<String, List<String>> headerCopy = new HashMap<String, List<String>>(responseHeaders);

		/* Set-Cookieヘッダ */
        List<String> list = headerCopy.get("Set-Cookie");
        if(list!=null){
			/* 取得したListは変更不可なので,新規コピーを作成してソートし,Mapへ格納 */
            ArrayList<String> newList = new ArrayList<String>(list);
            Collections.sort(newList, expireComparator);
            headerCopy.put("Set-Cookie", newList);
        }

		/* Set-Cookie2ヘッダ */
        List<String> list2 = headerCopy.get("Set-Cookie2");
        if(list2!=null){
			/* 取得したListは変更不可なので,新規コピーを作成してソートし,Mapへ格納 */
            ArrayList<String> newList = new ArrayList<String>(list2);
            Collections.sort(newList, expireComparator);
            headerCopy.put("Set-Cookie2", newList);
        }

        if(!this.parsable){
            Locale.setDefault(Locale.US);
            super.put(uri, headerCopy);
            Locale.setDefault(defaultLocale);
        }else{
            super.put(uri, headerCopy);
        }
    }
}
