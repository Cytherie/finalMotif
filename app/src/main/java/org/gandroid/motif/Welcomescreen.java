package org.gandroid.motif;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Welcomescreen extends AppCompatActivity {
    SQLiteDatabase db;


    //DECLARATION OF VARIABLES***********
    // VARIABLE FOR THE PERIOD RANGE
   // private String[] arrayspinnerinputperiodrange = new String[31];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomescreen);

        Toast.makeText(getApplicationContext(),"first run!",Toast.LENGTH_LONG).show();
        // CREATE DATABASE FOR THE USER DATA
        db = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(20),PERIODRANGE VARCHAR(20))");

        ArrayList<String>DAYS = new ArrayList<String>();
        for (int i = 15; i <= 35; i++) {
            DAYS.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DAYS);

        Spinner spinperiodrange = (Spinner)findViewById(R.id.spinnerwcinputperiodrange);
        spinperiodrange.setAdapter(adapter);

        //The following codes are for the cycle days
      /**  int countercycle = 0;
        for (int c = 0; c < 31; c++) {
            countercycle = c + 1;

            this.arrayspinnerinputperiodrange[c] = "" + countercycle;
        }

        Spinner spinnerinputCycledays = (Spinner) findViewById(R.id.spinnerwcinputperiodrange);
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayspinnerinputperiodrange);
        spinnerinputCycledays.setAdapter(itemsAdapter);**/


        //CALL BUTTON NEXT FOR THE EVENT
        final Button wcbuttonnext = (Button)findViewById(R.id.buttonwcnext);
        wcbuttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DATA FOR INSERTING INTO DATABASE
                EditText name = (EditText)findViewById(R.id.editTextname);
                Spinner  periodrange= (Spinner)findViewById(R.id.spinnerwcinputperiodrange);

                String DBname,DBperiodrange;
                DBname= name.getText().toString().trim();
                DBperiodrange= periodrange.getSelectedItem().toString().trim();

                db.execSQL("INSERT INTO userinfo(NAME,PERIODRANGE)VALUES ('"+DBname+"','"+DBperiodrange+"');");
                String screenval = "1";
                Bundle b = new Bundle();
                b.putString("frmwcscreen",screenval);

                Intent nextactivity = new Intent(Welcomescreen.this,Secondwcscreen.class);
                nextactivity.putExtra("FromActivity","wcscreen");
                nextactivity.putExtras(b);
                startActivity(nextactivity);
            }
        });

    }
}
