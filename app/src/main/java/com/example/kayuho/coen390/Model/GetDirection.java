package com.example.kayuho.coen390.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ProgressDialog dialog;
    Context current;
    Double travelTime;

    public GetDirection(Context context){
        this.current=context;
        dialog = new ProgressDialog(current);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        dialog.setMessage("LOADING PLEASE WAIT.......");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute( String value){
        super.onPostExecute(value);
        if(value!=null){
            dialog.dismiss();
        }
        else
            Toast.makeText(current, "ERROR, something wrong happen, please check inputed values ",Toast.LENGTH_SHORT).show();
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
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while(line!=null){
                    Log.d("LINE",line);
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
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
