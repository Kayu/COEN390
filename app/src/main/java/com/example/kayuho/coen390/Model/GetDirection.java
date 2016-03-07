package com.example.kayuho.coen390.Model;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Kayu on 2016-03-07.
 */
public class GetDirection extends AsyncTask< String, Void, String>{
    @Override
    protected void onPreExecute(){

    }

    @Override
    protected void onPostExecute( String value){

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            URL url = new URL (params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
        }
        catch (MalformedURLException e) {
            Log.d("error", "URL nto crrect");
        } catch (IOException e) {
            Log.d("error", "IO Exception");
        }
        /*
        catch (JSONException e) {

            Log.d("error","JSON Exception");
        }
        */
        return null;
    }
}
