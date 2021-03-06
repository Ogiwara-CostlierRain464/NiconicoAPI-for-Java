package jp.costlierrain464.ogiwara.java.niconicoapi.method;

import jp.costlierrain464.ogiwara.java.niconicoapi.exception.NiconicoException;
import jp.costlierrain464.ogiwara.java.niconicoapi.http.WebClient;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NicoLogin {
    static final String URI = "https://secure.nicovideo.jp/secure/login?site=niconico";

    static final int HTTP_OK = 200;
    static final int HTTP_FOUND = 302;

    Logger logger = Logger.getLogger(NicoLogin.class.getName());

    public void execute(String mail,String pass) throws NiconicoException,IOException{
        if(StringUtils.isBlank(mail) || StringUtils.isBlank(pass))
            throw new NiconicoException("Login parameters was empty");

        try(WebClient client = WebClient.post(URI, "mail="+mail+"&password="+pass)){
            int rescode = client.connection.getResponseCode();
            logger.info("Responce code:" + String.valueOf(rescode));
            if(rescode !=  HTTP_FOUND)
                throw new NiconicoException("Login request error");

            for(String e : client.connection.getHeaderFields().get("Location"))
                if(e.contains("https://secure.nicovideo.jp/secure/login"))
                    throw new NiconicoException("Invailed mail or password");
        }
    }
}
