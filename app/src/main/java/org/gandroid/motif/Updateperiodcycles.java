package org.gandroid.motif;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Updateperiodcycles extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteDatabase Calevents;
    DatabaseHelper myDB;
    ArrayList<Periodcycle> PeriodList;
    ListView listView;
    Periodcycle period;


    private String[] arrayupdatedayslasted = new String[10];
    String nextperiodval="";
    Integer GETID = 0;
    String Lstperiod = "";
    String Cdays = "";
    String Dended = "";
    String endofnextperiod="";
    String startDateNP="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateperiodcycles);

        db = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        Cursor c=db.rawQuery("select [PERIODRANGE] from userinfo", null);
        //move cursor to first position
        c.moveToFirst();
        final Integer periodrange=Integer.parseInt(c.getString(c.getColumnIndex("PERIODRANGE")));

        Calevents = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        Calevents.execSQL("CREATE TABLE IF NOT EXISTS eventlist(ID INTEGER PRIMARY KEY AUTOINCREMENT,NEXTPERIODDAYS VARCHAR (100),FERTILEDAYS VARCHAR(100),OVULATIONDAYS VARCHAR(20))");


        //GET LAST ID FROM DATABASE

        myDB = new DatabaseHelper(this);

        PeriodList= new ArrayList<>();

        Cursor data = myDB.getListContents();

        int numrows =  data.getCount();

        if (numrows==0){
            Toast.makeText(Updateperiodcycles.this,"Database is empty!",Toast.LENGTH_LONG).show();
        }else {

            if (data.moveToLast()) {
                period = new Periodcycle(data.getString(1), data.getString(2), data.getString(3));
                PeriodList.add(period);
                // GET SINGLE COLUMN
                GETID = data.getInt(data.getColumnIndex("ID"));
                Lstperiod = data.getString(data.getColumnIndex("LASTPERIOD"));
                Cdays= data.getString(data.getColumnIndex("CYCLEDAYS"));
                Dended = data.getString(data.getColumnIndex("DATEENDED"));
            }

        }


        Button btnupdate = (Button)findViewById(R.id.buttonupdatesubmit);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Spinner getitem = (Spinner) findViewById(R.id.spinnerupdatecycledays);
                String getinput = getitem.getSelectedItem().toString().trim();
                final Integer getuserinput= Integer.parseInt(getinput);


                Date conv2datefert = null;
                Date conv2dateNperiod = null;
                Date conv2dateOVU = null;
                Date conv2dateendnperiod = null;

                String nextperiodDate = null;
                String ovulationday = null;
                String fertilitydays = null;
                String endofnextperiod = null;

                try{
                    Date date = sdf.parse(Lstperiod);
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    c.add(Calendar.DAY_OF_MONTH,(getuserinput));
                    nextperiodval = sdf.format(c.getTime());

                    ////////////////////////////////////////////////////////////////////////////////
                    ////////////////////// ** GET NEXT PERIOD START DATE ** ////////////////////////
                    ////////////////////////////////////////////////////////////////////////////////
                    Date startnextperiod = sdf.parse(Lstperiod);
                    Calendar startNP = Calendar.getInstance();
                    startNP.setTime(startnextperiod);
                    startNP.add(Calendar.DAY_OF_MONTH,(periodrange));
                    startDateNP = sdf.format(c.getTime());

                    ////////////////////////////////////////////////////////////////////////////////
                    ////////////////////// ** GET NEXT PERIOD END DATE ** //////////////////////////
                    ////////////////////////////////////////////////////////////////////////////////

                    Date nptodate = sdf.parse(startDateNP);
                    Calendar npcal = Calendar.getInstance();
                    npcal.setTime(nptodate);
                    npcal.add(Calendar.DAY_OF_MONTH,getuserinput);
                    endofnextperiod=sdf.format(npcal.getTime());

                    ////////////////////////////////////////////////////////////////////////////////
                    ///////////////////// ** GET OVULATION DATE ** /////////////////////////////////
                    ////////////////////////////////////////////////////////////////////////////////

                    Date OVDATE = sdf.parse(startDateNP);                // OVDATE = NEXTPERIOD TO DATE
                    Calendar calgetovu = Calendar.getInstance();        // INITIALIZED CALENDAR
                    calgetovu.setTime(OVDATE);                          // CONVERTED OVDATE FROM DATE TO CALENDAR
                    calgetovu.add(Calendar.DAY_OF_MONTH, -14);       // OVULATION = NEXTPERIOD - 14DAYS
                    conv2dateOVU = calgetovu.getTime();                 // CONVERT OVULATION TO STRING
                    ovulationday = sdf.format(conv2dateOVU);

                    ////////////////////////////////////////////////////////////////////////////////
                    //////////////////////// ** GET FERTILITY DATES ** /////////////////////////////
                    ////////////////////////////////////////////////////////////////////////////////

                    Date fertdate = sdf.parse(ovulationday);
                    Calendar calgetfert = Calendar.getInstance();
                    calgetfert.setTime(fertdate);
                    calgetfert.add(Calendar.DAY_OF_MONTH, -6);
                    conv2datefert = calgetfert.getTime();
                    fertilitydays = sdf.format(conv2datefert);


                } catch (ParseException e) {
                    e.printStackTrace();
                }


                ////////////////////////////////////////////////////////////////////////////////////////////
                /////////////////////// ** GET INCLUSIVE DATES FOR THE NEXT PERIOD ** //////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////
                /////////////// ** MAKE AN ARRAY LIST TO PUT IN EVENTS FOR PERIOD DAYS ** //////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////

                Date S = null;
                Date E = null;
                try {
                    S = sdf.parse(nextperiodval);
                    E = sdf.parse(endofnextperiod);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DateTime startofNperiod = new DateTime(S);
                DateTime endofNperiod = new DateTime(E);

                DateFormat FullFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                ArrayList<Long>nextperiodlist = new ArrayList<Long>();
                int days = Days.daysBetween(startofNperiod, endofNperiod).getDays();
                List<Date> incdatesforNperiod = new ArrayList<>(days);  // Set initial capacity to `days`.
                for (int i = 0; i < days; i++) {
                    LocalDate d = startofNperiod.toLocalDate().withFieldAdded(DurationFieldType.days(), i);
                    incdatesforNperiod.add(d.toDate());

                    nextperiodlist.add(d.toDate().getTime());


                }
                android.text.TextUtils.join(",", nextperiodlist);
                StringBuilder listString = new StringBuilder();

                for (Long s : nextperiodlist)
                    listString.append(s+",");

                String inclusiveNextperiod = listString.toString();

                ////////////////////////////////////////////////////////////////////////
                /////////////// ** GET INCLUSIVE DATES FOR FERTILE DAYS/////////////////
                ////////////////////////////////////////////////////////////////////////

                Date SF = null;
                Date EF = null;
                try {
                    SF = sdf.parse(fertilitydays);
                    EF = sdf.parse(ovulationday);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DateTime startofFperiod = new DateTime(SF);
                DateTime endofFperiod = new DateTime(EF);

                ArrayList<Long>Fertilitylist = new ArrayList<Long>();
                int daysDIFF = Days.daysBetween(startofFperiod, endofFperiod).getDays();
                List<Date> incdatesforFperiod = new ArrayList<>(daysDIFF);  // Set initial capacity to `days`.
                for (int x = 0; x < daysDIFF; x++) {
                    LocalDate B = startofFperiod.toLocalDate().withFieldAdded(DurationFieldType.days(), x);
                    incdatesforFperiod.add(B.toDate());

                    Fertilitylist.add(B.toDate().getTime());


                }
                android.text.TextUtils.join(",", Fertilitylist);
                StringBuilder listfertily = new StringBuilder();

                for (Long F : Fertilitylist)
                    listfertily.append(F+",");

                String inclusiveFertiledays = listfertily.toString();

                Long longOvulationdate =conv2dateOVU.getTime();
                String OvulationDay = longOvulationdate.toString();

                ////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////// ** END OF CALCULATION ** ////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////



                Calevents.execSQL("INSERT INTO eventlist(NEXTPERIODDAYS,FERTILEDAYS,OVULATIONDAYS) VALUES ('"+inclusiveNextperiod+"','"+inclusiveFertiledays+"','"+OvulationDay+"');");


                Toast.makeText(Updateperiodcycles.this,"Period ended:"+nextperiodval,Toast.LENGTH_LONG).show();
                String convuserinput = getuserinput.toString();

                myDB.updateFields(convuserinput,nextperiodval,GETID);

                Intent nextactivity = new Intent(Updateperiodcycles.this,Periodtracker.class);
                startActivity(nextactivity);

            }
        });


        //The following codes are for the period range
        int counter = 0;
        for (int x = 0; x < 10; x++) {
            counter = x + 1;
            this.arrayupdatedayslasted[x] = "" + counter;
        }

        Spinner spinnerupdatecycledays = (Spinner) findViewById(R.id.spinnerupdatecycledays);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayupdatedayslasted);
        spinnerupdatecycledays.setAdapter(adapter);

    }
}
