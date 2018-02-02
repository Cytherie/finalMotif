package org.gandroid.motif;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.FontsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class CalendarView extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<CycleEvents>CycleEventsList;
    ListView listView;
    CycleEvents cycleEvents;

    private static final long strL = L;
    SQLiteDatabase db;

    CompactCalendarView compactCalendar;
    //private DateFormat dateFormatMonth = DateFormat.getDateInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        final TextView monthindicator = findViewById(R.id.CurrentMonth);

        Date setMonthoncreate= new Date();
        Format formatter = new SimpleDateFormat("MMMM yyyy");
        monthindicator.setText(formatter.format(setMonthoncreate));


        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////** CALCULATE USER'S PERIOD CYCLE **/////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
        db = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS eventlist(ID INTEGER PRIMARY KEY AUTOINCREMENT,NEXTPERIODDAYS VARCHAR (100),FERTILEDAYS VARCHAR(100),OVULATIONDAYS VARCHAR(20))");


        Cursor c=db.rawQuery("select * from eventlist", null);
        //move cursor to first position


        myDB = new DatabaseHelper(this);

        CycleEventsList= new ArrayList<>();

        Cursor data = myDB.getCycleEvents();

        int numrows =  data.getCount();


         if (numrows==0){
             Toast.makeText(CalendarView.this,"Database is empty!",Toast.LENGTH_LONG).show();
         }
            else {
            while (data.moveToNext()) {

                cycleEvents = new CycleEvents (data.getString(1),data.getString (2),data.getString(3));
                CycleEventsList.add(cycleEvents);


                String NextPeriodDates = data.getString(1);
                String FertileDates = data.getString(2);
                String OvulationDay = data.getString(3);

                String[] NextperiodDatesArray = NextPeriodDates.trim().split(",");
                String[] FertileDatesArray = FertileDates.trim().split(",");
                Long millisND;

                for (String ND : NextperiodDatesArray) {
                    millisND = Long.parseLong(ND);

                    Event periodDates = new Event(Color.rgb(255, 0, 0), millisND + strL, "Some extra data that I want to store.");
                    compactCalendar.addEvent(periodDates);
                }


                Long millisFD;
                for (String FD : FertileDatesArray) {
                    millisFD = Long.parseLong(FD);

                    Event fertilityDates = new Event(Color.rgb(89, 181, 86), millisFD + strL, "Some extra data that I want to store.");
                    compactCalendar.addEvent(fertilityDates);
                }

                Long millisOD = Long.parseLong(OvulationDay);
                Event OvulationDate = new Event(Color.rgb(220, 154, 248), millisOD + strL, "Some extra data that I want to store.");
                compactCalendar.addEvent(OvulationDate);

                List<Event> events = compactCalendar.getEvents(1515076271379L);

            }

         }



            ////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////// ** CREATE EVENT TO HIGHLIGHT IN CALENDAR ** //////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////


            ////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////// ** DECLARE CALCULATIONS AS FINAL ** //////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////

            compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    Context context = getApplicationContext();

                    /** if (dateClicked.toString().compareTo(newdate)==0){
                     Toast.makeText(context,"I got an event here!", Toast.LENGTH_SHORT).show();
                     Toast.makeText(context,"The statement is equal to clicked:" +Strmilliseconds, Toast.LENGTH_SHORT).show();

                     }else {
                     //  Toast.makeText(context, "Sorry no event for this day :(", Toast.LENGTH_SHORT).show();
                     //Toast.makeText(context,"Nextperiod:" +firstevent, Toast.LENGTH_LONG).show();
                     // Toast.makeText(context,"Ovulation:" + d, Toast.LENGTH_LONG).show();
                     // Toast.makeText(context, "Fertility:"+ y, Toast.LENGTH_LONG).show();
                     }*/
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    Format formatter = new SimpleDateFormat("MMMM yyyy");
                    monthindicator.setText(formatter.format(firstDayOfNewMonth));
                }
            });
        } // CLOSING TAG FOR ON CREATE

    }

