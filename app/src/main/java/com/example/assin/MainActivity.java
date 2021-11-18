package com.example.assin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//Import classes needed in order for code to run

public class MainActivity extends AppCompatActivity {
    EditText longitude, latitude;
    Button add, view, remove;
    Database DB;
    String place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitude = findViewById(R.id.editLongitude);//Asgin the longitude variable
        latitude = findViewById(R.id.editLatitude);//Assign the latitude variable
        add = findViewById(R.id.add);// Assign the Add button
        view = findViewById(R.id.view);//Assign the View button
        remove = findViewById(R.id.remove);//Assign the remove button
        DB = new Database(this);//Assigns the Database to DB
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ;
                try {
                    String Longitude = longitude.getText().toString();
                    String Latitude = latitude.getText().toString();
                    //Convert both values to Double values that way we can use geocode to obtain the location
                    Double a = Double.parseDouble(Latitude);
                    Double b = Double.parseDouble(Longitude);
                    String space = "-";
                    List<Address> adress = geocoder.getFromLocation(a, b, 1);
                    StringBuilder sb = new StringBuilder();

                    int i = 0;
                    while (i < adress.size() - 1)
                    {
                        sb.append(adress.get(i));
                        sb.append(space);
                        i++;
                    }
                    sb.append(adress.get(i));
                    place = sb.toString();
                    //Since Adress is stored as a List than we needed to append each coordinate that convert that into a string
                    Boolean checkinsertdata = DB.insertuserdata(Longitude, Latitude, place);
                    if(checkinsertdata==true)
                        Toast.makeText(MainActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
                    //If database ha sucesfully entered its data it will display sucessfull otherwise it would say Unsucessful.


                } catch(IOException e) {
                    e.printStackTrace();
                }
            }});



        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //THe longitude is the primary key so if the user wil like to delete a node it will look at the primarykey(Longitude) and delete the most recent call
                String Longitude = longitude.getText().toString();
                Boolean checkudeletedata = DB.removedata(Longitude);
                if(checkudeletedata==true)
                    Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    //To help the user identify which coordinate is
                    buffer.append("Latitude :"+res.getString(0)+"\n");
                    buffer.append("Location :"+res.getString(1)+"\n");
                    buffer.append("Longitude :"+res.getString(2)+"\n");;

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Database");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });


    }


}

