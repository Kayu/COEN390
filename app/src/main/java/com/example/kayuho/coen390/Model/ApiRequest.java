package com.example.kayuho.coen390.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.example.kayuho.coen390.Service.JsonParser;
import com.example.kayuho.coen390.Service.RouteDecoder;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//AO test commit...
/**
 * Created by Kayu on 2016-03-07.
 */
public class ApiRequest extends AsyncTask< String, Void, String>{
    ProgressDialog dialog;
    Context current;
    private Direction direction;
    public ApiRequest(Context context){
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

        dialog.dismiss();
        //Toast.makeText(current, "ERROR, something wrong happen, please check inputed values ",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection link = (HttpURLConnection)url.openConnection();
            link.setRequestMethod("GET");
            link.connect();

            if((link.getResponseCode())== HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(link.getInputStream()));
                direction = new JsonParser().createObject(bufferedReader);

            }
        }
        catch(MalformedURLException e){
            Log.d("error","url is malformded");
        }
        catch(IOException e){
            Log.d("error","connection error");
        }

        return null;
    }

    public Direction getDirection(){
        return direction;
    }
}
