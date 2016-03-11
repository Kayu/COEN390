package com.example.kayuho.coen390.Controller;



import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kayuho.coen390.R;
import com.example.kayuho.coen390.Service.DbHelper;


/**
 * Created by Alexei on 2016-03-11.
 */
public class DatabaseTest extends AppCompatActivity {

    DbHelper MyDB;
    Button Add, Display;
    EditText editText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        MyDB = new DbHelper(this);
        editText = (EditText) findViewById(R.id.editText);

        Add = (Button)findViewById(R.id.add_address);
        Display = (Button)findViewById(R.id.display_button);

        add_Address();
        viewAll();
    }




    public void add_Address(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String address = editText.getText().toString();

                        boolean isInserted = MyDB.insert_address(address);

                        if (isInserted = true)
                            Toast.makeText(DatabaseTest.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(DatabaseTest.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){
        Display.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor data = MyDB.getAddress();
                        if(data.getCount() == 0){
                            showMessage("Error","No Data Found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(data.moveToNext()){                                   //getting data from data base and using buffer (input output)
                            buffer.append("Address: "+ data.getString(0)+"\n\n");  //data.getString(1) is getting data from the second column of the current row from the table

                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }


    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }










}
