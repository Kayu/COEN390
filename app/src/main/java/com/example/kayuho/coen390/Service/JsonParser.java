package com.example.kayuho.coen390.Service;

import android.text.Html;
import android.util.Log;

import com.example.kayuho.coen390.Model.Direction;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kai on 4/9/2016.
 */
public class JsonParser {
    private Direction direction;
    public Direction createObject(BufferedReader bufferedReader){

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String json = bufferedReader.readLine();

            while (json != null) {
                stringBuilder.append(json);
                json = bufferedReader.readLine();
            }

            json = stringBuilder.toString();
            Log.d("JSON: ", json);
            JSONObject result = new JSONObject(json);
            JSONArray routeObj = result.getJSONArray("routes");
            JSONObject routes = routeObj.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            JSONObject legs = routes.getJSONArray("legs").getJSONObject(0);
            ArrayList<LatLng> points = RouteDecoder.decodePolyline(encodedString);
            String distance = legs.getJSONObject("distance").getString("text");
            String duration = legs.getJSONObject("duration").getString("text");
            JSONArray steps = routes.getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
            JSONObject step;
            ArrayList<String> directions = new ArrayList<String>();

            for (int i = 0; i < steps.length(); i++) {
                step = (JSONObject) steps.get(i);
                directions.add(Html.fromHtml(step.getString("html_instructions")).toString());
            }

            direction = new Direction(points, directions, duration, distance);

            Log.i("DISTANCE", distance);
            Log.i("DURATION", duration);

            for (int i = 0; i < directions.size(); i++) {
                Log.i("STEP", directions.get(i));
            }
            return direction;
        }
        catch (IOException e) {
            Log.d("error", "connection failed");
        }
        catch (JSONException e) {
            Log.d("error","something went wrong with json");
        }
        return null;
    }

}
