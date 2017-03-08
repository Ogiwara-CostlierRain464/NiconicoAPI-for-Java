package jp.costlierrain464.ogiwara.java.niconicoapi.http;

import java.io.*;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class WebClient implements AutoCloseable{

    public static CorrectedCookieManager manager;

    //begin Must close at last
    public HttpURLConnection connection;
    public OutputStream outputStream = null;
    public OutputStreamWriter outputStreamWriter = null;
    public InputStream inputStream = null;
    //end

    public static void init(){
        manager = new CorrectedCookieManager();
        CookieHandler.setDefault(manager);
    }

    private WebClient(URL url) throws IOException{
        connection = (HttpURLConnection) url.openConnection();
    }

    public static WebClient get(String url) throws IOException{
        WebClient webClient = new WebClient(new URL(url));
        webClient.connection.setRequestMethod("GET");
        webClient.connection.setInstanceFollowRedirects(false);
        webClient.connection.connect();
        return webClient;
    }

    public static WebClient post(String url,String postData) throws IOException{
        WebClient webClient = new WebClient(new URL(url));
        webClient.connection.setDoOutput(true);
        webClient.connection.setRequestMethod("POST");
        webClient.outputStream =  webClient.connection.getOutputStream();
        webClient.outputStreamWriter = new OutputStreamWriter(webClient.outputStream);
        webClient.outputStreamWriter.write(postData);
        webClient.outputStreamWriter.close();
        webClient.connection.connect();
        return webClient;
    }

    public String getEntity() throws IOException{
        inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line = "",result = "";
        while((line = rd.readLine()) != null)
            result += line;
        rd.close();
        return result;
    }

    public void close() throws IOException{
        connection.disconnect();

        if(outputStream != null)
            outputStream.close();

        if(inputStream != null)
            inputStream.close();
    }
}
