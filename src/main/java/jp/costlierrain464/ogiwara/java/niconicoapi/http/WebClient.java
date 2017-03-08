package jp.costlierrain464.ogiwara.java.niconicoapi.http;

import java.io.*;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class WebClient implements AutoCloseable{

    public CorrectedCookieManager manager;

    public HttpURLConnection connection;

    public OutputStreamWriter outputStreamWriter = null;

    public InputStream inputStream = null;

    private WebClient(URL url) throws IOException{
        manager = new CorrectedCookieManager();
        CookieHandler.setDefault(manager);

        connection = (HttpURLConnection) url.openConnection();
    }

    public static WebClient get(String url) throws IOException{
        WebClient webClient = new WebClient(new URL(url));
        webClient.connection.setRequestMethod("GET");
        webClient.connection.setInstanceFollowRedirects(false);
        webClient.connection.connect();
        return webClient;
    }

    public static WebClient post(String url,String postdata) throws IOException{
        WebClient webClient = new WebClient(new URL(url));
        webClient.connection.setDoOutput(true);
        webClient.connection.setRequestMethod("POST");
        webClient.outputStreamWriter = new OutputStreamWriter(webClient.connection.getOutputStream());
        webClient.outputStreamWriter.write(postdata);
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
        if(outputStreamWriter != null)
            outputStreamWriter.close();

        if(inputStream != null)
            inputStream.close();
    }
}
