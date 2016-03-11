package com.example.kayuho.coen390.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.Model.JsonParser;
import com.example.kayuho.coen390.Model.UrlString;
import com.example.kayuho.coen390.R;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn_test = (Button)findViewById(R.id.GetHomeButton);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String depart = "2211EmileNelligan";
                String arrival = "1087Duguay";
                UrlString url = new UrlString(MainActivity.this, depart, arrival);
                JsonParser getTransitDirection = new JsonParser(MainActivity.this);
                Direction transitDirection;
                try {
                    getTransitDirection.execute(url.makeDirectionsURL("transit")).get();

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                transitDirection = getTransitDirection.getPoints();
                Intent intent = new Intent(MainActivity.this, TransitOptionsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putParcelable("transit", transitDirection);
                intent.putExtra("bundle", mBundle);

                JsonParser getWalkingDirection = new JsonParser((MainActivity.this));

                Direction walkingDirection;
                try {
                    getWalkingDirection.execute(url.makeDirectionsURL("walking")).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                walkingDirection = getWalkingDirection.getPoints();

                Bundle wBundle = new Bundle();
                wBundle.putParcelable("walking", walkingDirection);
                intent.putExtra("walkBundle", wBundle);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
