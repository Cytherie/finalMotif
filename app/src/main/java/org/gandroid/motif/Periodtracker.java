package org.gandroid.motif;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
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

public class Periodtracker extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper myDB;
    ArrayList<Periodcycle> PeriodList;
    ListView listView;
    Periodcycle period;

    //VARIABLES FOR LAST PERIOD DATE ***
    private static final String TAG = "Welcome";
    private TextView tvinputlastperiod;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    String periodstats = "";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodtracker);
        String passlastperiod="";

        webView = findViewById(R.id.animatedgif);
        webView.loadUrl("file:///android_asset/anime.html");
        webView.setBackgroundColor(Color.TRANSPARENT);


        db = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS userinfo(NAME VARCHAR(20),PERIODRANGE INTEGER(20))");

        //SCAN FOR THE LAST DATA FIRST
        myDB = new DatabaseHelper(this);

        PeriodList = new ArrayList<>();

        Cursor data = myDB.getListContents();

        int numrows = data.getCount();

        if (numrows == 0) {
            Toast.makeText(Periodtracker.this, "Database is empty!", Toast.LENGTH_LONG).show();

            String prevdata;
            Integer daysleft = 0;
            Bundle b = this.getIntent().getExtras();

            if (b != null) {
                prevdata = b.getString("daysleft");
                daysleft = Integer.parseInt(prevdata);
            }

            String dayss = " days";

            String msgnegative = "delayed. You should see your doctor or consider taking a pregnancy test.";
            String msgpositive = "left until your period";

            if (daysleft >= 0) {
                ((TextView) findViewById(R.id.tvDaysleft)).setText(daysleft + dayss);
                ((TextView) findViewById(R.id.tvMsg)).setText(msgpositive);

            } else {
                Integer makepositive = daysleft * -1;

                ((TextView) findViewById(R.id.tvDaysleft)).setText(makepositive + dayss);
                ((TextView) findViewById(R.id.tvMsg)).setText(msgnegative);
            }

        } else {

            if (data.moveToLast()) {
               period=new Periodcycle(data.getString(1), data.getString(2), data.getString(3));
                PeriodList.add(period);
                // GET VALUES OF THE LAST ROW
                String FDBID = data.getString(data.getColumnIndex("ID"));
                String FDBlastperiod = data.getString(data.getColumnIndex("LASTPERIOD"));
                String FDBcycledays = data.getString(data.getColumnIndex("CYCLEDAYS"));
                String FDBdateended = data.getString(data.getColumnIndex("DATEENDED"));

                passlastperiod = FDBlastperiod;

                switch (FDBcycledays) {
                    case "N/A": {
                            Toast.makeText(Periodtracker.this,"Hey you're on case N/A",Toast.LENGTH_LONG).show();
                        Calendar calendar = Calendar.getInstance();
                        DateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy ");
                        String strDate = mdformat.format(calendar.getTime());


                        DateFormat conv = new SimpleDateFormat("MM/dd/yyyy");
                        Date date2day = null;
                        Date FDBlperiod = null;


                        try {
                            date2day = conv.parse(strDate);
                            FDBlperiod = conv.parse(FDBlastperiod);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        DateTime dt1 = new DateTime(date2day);
                        DateTime dt2 = new DateTime(FDBlperiod);

                        long diffDays = Days.daysBetween(dt1,dt2).getDays();

                        String dayss = " day/s";
                        String prevdata;

                        String msg = "on period.";
                        String addmsg="Don't forget to wear tampoons!";

                        if (diffDays==0){
                            diffDays=diffDays+1;
                        }else if (diffDays<0){
                            diffDays=diffDays*-1;
                        }

                            ((TextView) findViewById(R.id.tvDaysleft)).setText(diffDays + dayss);
                            ((TextView) findViewById(R.id.tvMsg)).setText(msg);


                            TextView tvnextperiod = (TextView)findViewById(R.id.tvNEXTPERIOD);
                            tvnextperiod.setText(addmsg);

                            Button disableend = (Button)findViewById(R.id.buttonendperiod);
                            disableend.setVisibility(View.VISIBLE);

                             Button disablestart = (Button)findViewById(R.id.buttonstartnow);
                             disablestart.setVisibility(View.GONE);

                        break;
                    }
                    case "":{
                        Toast.makeText(Periodtracker.this,"Can't find data :(",Toast.LENGTH_LONG).show();

                        break;
                    }
                    default: {
                        Button endnow = (Button)findViewById(R.id.buttonendperiod);
                        Button startnow = (Button)findViewById(R.id.buttonstartnow);

                        endnow.setVisibility(View.GONE);
                        startnow.setVisibility(View.VISIBLE);

                        Toast.makeText(Periodtracker.this,"CASE:DEFAULT",Toast.LENGTH_LONG);

                        // GET CURRENT DATE
                        Calendar calendar = Calendar.getInstance(); // GET THE CURRENT DATE USING A CALENDAR OBJECT
                        DateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy "); // SET A CUSTOM DATE FORMAT
                        String strDate = mdformat.format(calendar.getTime()); //CHANGED THE CALENDAR TO CUSTOM FORMAT AND CONVERTED TO STRING


                        DateFormat conv = new SimpleDateFormat("MM/dd/yyyy"); // SET CUSTOM DATE FORMAT

                        //GET PERIODRANGE FROM DATA BASE
                        String FDBperiodrange;
                        //use cursor to keep all data
                        //cursor can keep data of any data type
                        Cursor c = db.rawQuery("select [PERIODRANGE] from userinfo", null); //SELECTED PERIODRANGE FROM DATABASE
                        c.moveToFirst(); //move cursor to first position
                        String periodrange = c.getString(c.getColumnIndex("PERIODRANGE")); // GET THE SELECTED DATA FROM CURSOR AND CONVERTED TO STRING


                        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); // SET CUSTOM DATE FORMAT
                        try {
                            Date date = sdf.parse(FDBlastperiod); //PARSED LASTPERIOD LATEST DATA FROM DATABASE AND CONVERTED TO DATE
                            Calendar newcal = Calendar.getInstance(); // INSTANTIATE A CALENDAR
                            newcal.setTime(date); // CONVERTED THE LASTPERIOD TO CALENDAR
                            newcal.add(Calendar.DAY_OF_MONTH,Integer.parseInt(periodrange));  //CALCULATION ******* NEWCAL = LASTPERIOD + PERIODRANGE -- this must be the nextperiod

                            Date rawnextperiod = newcal.getTime(); // CONVERTED NEWCAL TO DATE
                            String nextperiod = sdf.format(rawnextperiod); // CONVERTED NEWCAL TO STRING

                            String finalnextperiod = nextperiod; // DECLARED NEXTPERIOD
                            String currentdate = strDate; //DECLARED CURRENTDATE

                            DateFormat format = new SimpleDateFormat("MM/dd/yyyy"); // SET CUSTOM FORMAT

                            Date d1 = null;
                            Date d2 = null;

                            Integer daysleft = 0;

                            try {
                                d1 = format.parse(finalnextperiod); // CONVERTED NEXTPERIOD FROM STRING TO DATE
                                d2 = format.parse(currentdate); //CONVERTED CURRENTDATE FROM STRING TO DATE


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            DateTime dt1 = new DateTime(d1); // CONVERTED NEXT PERIOD TO JODA TIME
                            DateTime dt2 = new DateTime(d2); // CONVERTED CURRENTDATE TO JODA TIME

                            daysleft = Days.daysBetween(dt2, dt1).getDays(); //CALCULATION *********** DAYSLEFT = CURRENTDATE - NEXTPERIOD -- this must be the daysleft
                            String dayss = " days";

                            String msgnegative = "delayed. You should see your doctor or consider taking a pregnancy test.";
                            String msgpositive = "left until your period.";
                            String nextperiodmsg = "is your estimated next period date start.";


                            if (daysleft >= 0) { //IF DAYSLEFT IS LESS THAN OR GREATER THAN 0, IT MEANS THE USER IS WAITING FOR HER NEXT PERIOD
                                periodstats="positive";
                                ((TextView) findViewById(R.id.tvDaysleft)).setText(daysleft + dayss);
                                ((TextView) findViewById(R.id.tvMsg)).setText(msgpositive);
                                ((TextView) findViewById(R.id.tvNEXTPERIOD)).setText(finalnextperiod+" "+nextperiodmsg);


                            } else {
                                periodstats="negative";
                                Integer makepositive = daysleft * -1;

                                ((TextView) findViewById(R.id.tvDaysleft)).setText(makepositive + dayss);
                                ((TextView) findViewById(R.id.tvMsg)).setText(msgnegative);
                                ((TextView) findViewById(R.id.tvNEXTPERIOD)).setText(finalnextperiod+" "+nextperiodmsg);

                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                    break;
                }
            }


            Button okay = (Button) findViewById(R.id.buttonOkay);
            Button endnow = (Button) findViewById(R.id.buttonendperiod);
            Button startnow = (Button)findViewById(R.id.buttonstartnow);

            final String finalPasslastperiod = passlastperiod;
            startnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String frmPtrackerscreen= "2";
                    Bundle b = new Bundle();
                    b.putString("fromperiodtracker",frmPtrackerscreen);
                    b.putString("LP", finalPasslastperiod);

                    Intent startnow = new Intent(Periodtracker.this,Secondwcscreen.class);
                    startnow.putExtras(b);
                    startnow.putExtra("FromActivity","periodtracker");
                    startActivity(startnow);
                }
            });


            endnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent updatenext = new Intent(Periodtracker.this, Updateperiodcycles.class);
                    startActivity(updatenext);

                }
            });

            okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent nextactivity = new Intent(Periodtracker.this, Menu.class);
                    startActivity(nextactivity);

                }
            });

        }

    }
}
