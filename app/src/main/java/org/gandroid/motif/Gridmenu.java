package org.gandroid.motif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class Gridmenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridmenu);

        // TO CHECK IF THIS IS THE FIRST TIME RUN OF APPLICATION
        Boolean isFirstRun= getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isfirstrun",true);

        if (isFirstRun) {

            Toast.makeText(Gridmenu.this, "First run", Toast.LENGTH_LONG).show();
            Intent stores = new Intent(Gridmenu.this, Welcomescreen.class);
            startActivity(stores);
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
        }

        CardView Ptracker = findViewById(R.id.MPtracker);
        CardView MCalendar = findViewById(R.id.MCalendar);
        CardView PHistory = findViewById(R.id.MPhistory);
        CardView Methods = findViewById(R.id.MMethods);
        CardView Videos = findViewById(R.id.MVideos);
        CardView Aboutus = findViewById(R.id.MAboutus);

        Ptracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(Gridmenu.this,Periodtracker.class);
                startActivity(redirect);
            }
        });

        MCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(Gridmenu.this,CalendarView.class);
                startActivity(redirect);
            }
        });

        PHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent redirect = new Intent(Gridmenu.this,ViewListContents.class);
                startActivity(redirect);
            }
        });

        Methods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(Gridmenu.this,MethodsArticle.class);
                startActivity(redirect);
            }
        });

        Videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(Gridmenu.this,Videos.class);
                startActivity(redirect);
            }
        });

        Aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Gridmenu.this, "This feature is unavailable right now.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
