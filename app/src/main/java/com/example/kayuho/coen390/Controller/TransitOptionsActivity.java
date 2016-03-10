package com.example.kayuho.coen390.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kayuho.coen390.Model.Direction;
import com.example.kayuho.coen390.R;

public class TransitOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle mBundle = intent.getBundleExtra("bundle");
        final Direction transitDirection = (Direction) mBundle.getParcelable("transit");

        StringBuilder str_publictransit =  new StringBuilder();;
        str_publictransit.append("PUBLIC TANSIT: ");
        str_publictransit.append("DURATION: ");
        str_publictransit.append(transitDirection.getDuration());
        str_publictransit.append(" DISTANCE: ");
        str_publictransit.append(transitDirection.getDistance());

        TextView pt_textView = (TextView)findViewById(R.id.publictansit_textview);
        pt_textView.setText(str_publictransit.toString());
        Button transitMapsButton = (Button) findViewById(R.id.TransitButton);
        transitMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitOptionsActivity.this, MapsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("LatLng", transitDirection.getPoints());
                intent.putExtra("bundle",mBundle);
                startActivity(intent);
            }
        });

        Button walkMapsButton = (Button) findViewById(R.id.WalkingButton);
        walkMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitOptionsActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}

