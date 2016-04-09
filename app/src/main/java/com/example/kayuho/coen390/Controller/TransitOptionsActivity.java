package com.example.kayuho.coen390.Controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.R;
import com.example.kayuho.coen390.Service.OutgoingCallReceiver;

public class TransitOptionsActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        OutgoingCallReceiver newCall = new OutgoingCallReceiver();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        Bundle mBundle = intent.getBundleExtra("bundle");
        //Bundle wBundle = intent.getBundleExtra("walkBundle");

        final Direction transitDirection = (Direction) mBundle.getParcelable("transit");
        final Direction walkingDirection = (Direction) mBundle.getParcelable("walking");

        StringBuilder str_publictransit =  new StringBuilder();;
        str_publictransit.append("DURATION: ");
        str_publictransit.append(transitDirection.getDuration());
        str_publictransit.append("\n");
        str_publictransit.append(" DISTANCE: ");
        str_publictransit.append(transitDirection.getDistance());

        StringBuilder str_walking = new StringBuilder();
        str_walking.append("DURATION: ");
        str_walking.append(walkingDirection.getDuration());
        str_walking.append("\n");
        str_walking.append(" DISTANCE: ");
        str_walking.append(walkingDirection.getDistance());

        String fontPathButton = "fonts/ostrich-regular.ttf";
        Typeface tf_Button = Typeface.createFromAsset(getAssets(),fontPathButton);

        String fontPathTitles = "fonts/Capture_it.ttf";
        Typeface tf_Titles = Typeface.createFromAsset(getAssets(),fontPathTitles);

        TextView transit_Title = (TextView) findViewById(R.id.PublicTransitText);
        TextView walking_Title = (TextView) findViewById(R.id.WalkingText);

        transit_Title.setTypeface(tf_Titles);
        walking_Title.setTypeface(tf_Titles);

        TextView pt_textView = (TextView)findViewById(R.id.TransitButton);
        pt_textView.setText(str_publictransit.toString());
        pt_textView.setTypeface(tf_Button);
        pt_textView.setTextSize(25);

        TextView walkingTextView = (TextView)findViewById(R.id.WalkingButton);
        walkingTextView.setText(str_walking.toString());
        walkingTextView.setTypeface(tf_Button);
        walkingTextView.setTextSize(25);

        Button transitMapsButton = (Button) findViewById(R.id.TransitButton);
        transitMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitOptionsActivity.this, MapsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("LatLng", transitDirection.getPoints());
                mBundle.putSerializable("Directions", transitDirection.getDirections());
                intent.putExtra("bundle", mBundle);
                startActivity(intent);
            }
        });



        Button walkMapsButton = (Button) findViewById(R.id.WalkingButton);
        walkMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitOptionsActivity.this, MapsActivity.class);
                Bundle wBundle = new Bundle();
                wBundle.putSerializable("LatLng", walkingDirection.getPoints());
                wBundle.putSerializable("Directions", walkingDirection.getDirections());
                intent.putExtra("bundle", wBundle);
                startActivity(intent);
            }
        });
    }

}

