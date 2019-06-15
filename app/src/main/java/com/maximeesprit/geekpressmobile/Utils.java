package com.maximeesprit.geekpressmobile;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

    public static String readJSONFeed(String url) {
        String jsonArticle = new String();
        try {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(urlConnection.getInputStream())
            );
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                String line;
                while ((line = in.readLine()) != null) {
                    jsonArticle+=line;
                }
            } else {
                Log.d("JSON", "Failed to download file"+statusCode);
            }
            in.close();
            urlConnection.disconnect();
            return jsonArticle;
        }
        catch(MalformedURLException e){
            Log.d("URL",e.getStackTrace().toString());
        }
        catch (Exception e) {
            Log.d("JSON",e.toString());
        }
        return jsonArticle;
    }

}
