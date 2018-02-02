package org.gandroid.motif;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Menu extends AppCompatActivity {

        int[] IMAGES = {R.drawable.compress,R.drawable.iconhistory,R.drawable.calendar,R.drawable.iconfamily,R.drawable.iconvideos,R.drawable.iconaboutus};
        String [] NAMES = {"Period Tracker","Period History","Calendar View","Methods","Videos","About us"};
        String [] DESCRIPTIONS = {"Predict your period based on the given information.","You can remember your period cycles easily.",
                                    "Check out your ovulation through your fertile window.","Explore different family planning methods that suits you most.",
                                    "Watch videos to understand more about family planning","All credits goes here."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        // TO CHECK IF THIS IS THE FIRST TIME RUN OF APPLICATION
        Boolean isFirstRun= getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isfirstrun",true);

        if (isFirstRun) {

            Toast.makeText(Menu.this, "First run", Toast.LENGTH_LONG).show();
            Intent stores = new Intent(Menu.this, Welcomescreen.class);
            startActivity(stores);
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();

        }


        ListView listView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter= new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selection= NAMES[i];
                Intent nextactivity;
                switch (selection){
                    case "Period Tracker":

                         nextactivity = new Intent(Menu.this,Periodtracker.class);
                        startActivity(nextactivity);
                        break;


                    case "Period History":

                        nextactivity = new Intent(Menu.this,ViewListContents.class);
                        startActivity(nextactivity);
                        break;

                    case "Calendar View":

                         nextactivity= new Intent(Menu.this,CalendarView.class);
                        startActivity(nextactivity);
                        break;

                    case "Methods":
                         nextactivity= new Intent(Menu.this,MethodsArticle.class);
                        startActivity(nextactivity);
                        break;

                    case "Videos":
                        nextactivity= new Intent(Menu.this,Videos.class);
                        startActivity(nextactivity);
                        break;

                    default:

                        Toast.makeText(Menu.this,"Sorry, something went wrong :(",Toast.LENGTH_LONG).show();
                        break;

                }

            }
        });

    }



    class  CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
            TextView textviewname = (TextView)view.findViewById(R.id.textViewiconame);
            TextView textviewdesc = (TextView)view.findViewById(R.id.textViewdescription);

            imageView.setImageResource(IMAGES[i]);
            textviewname.setText(NAMES[i]);
            textviewdesc.setText(DESCRIPTIONS[i]);
            return view;
        }
    }
}
