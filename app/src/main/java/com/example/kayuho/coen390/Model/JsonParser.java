package com.example.kayuho.coen390.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
//AO test commit...
/**
 * Created by Kayu on 2016-03-07.
 */
public class JsonParser extends AsyncTask< String, Void, String>{
    ProgressDialog dialog;
    Context current;
    Direction direction;

    public JsonParser(Context context){
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
            if((link.getResponseCode())== HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(link.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String json = bufferedReader.readLine();
                while(json!=null){
                    stringBuilder.append(json);
                    json = bufferedReader.readLine();
                }

                json = stringBuilder.toString();
                Log.d("JSON: ",json);
                JSONObject result = new JSONObject(json);
                JSONArray routeObj = result.getJSONArray("routes");
                JSONObject routes = routeObj.getJSONObject(0);
                JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                String encodedString = overviewPolylines.getString("points");

                ArrayList<LatLng> points = decodePolyline(encodedString);
                String distance = routes.getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
                String duration = routes.getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
                JSONArray steps = routes.getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
                JSONObject step;
                ArrayList<String> directions = new ArrayList<String>();
                for (int i = 0; i < steps.length(); i++) {
                    step = (JSONObject)steps.get(i);
                    directions.add(Html.fromHtml(step.getString("html_instructions")).toString() );
                }

                direction = new Direction(points, directions, duration, distance);

                Log.i("DISTANCE", distance);
                Log.i("DURATION", duration);

                for(int i=0;i<directions.size();i++){
                    Log.i("STEP", directions.get(i));
                }

            }
        }
        catch(MalformedURLException e){
            Log.d("error","url is malformded");
        }
        catch (IOException e) {
            Log.d("error", "connection failed");
        }
        catch (JSONException e) {
            Log.d("error","something went wrong with json");
        }

        return null;
    }

    /** POLYLINE DECODER - http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java **/
    private ArrayList<LatLng> decodePolyline(String encoded) {

        ArrayList<LatLng> poly = new ArrayList<LatLng>();

        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),(((double) lng / 1E5)));
            poly.add(p);
        }
        /*
        for(int i=0;i<poly.size();i++){
            Log.i("Location", "Point sent: Latitude: "+poly.get(i).latitude+" Longitude: "+poly.get(i).longitude);
        }
        */
        return poly;
    }

    public Direction getPoints(){
        return direction;
    }
}