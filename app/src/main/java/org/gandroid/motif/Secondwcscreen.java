package org.gandroid.motif;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Secondwcscreen extends AppCompatActivity {
    SQLiteDatabase FORLatestLP;
    SQLiteDatabase db;
    SQLiteDatabase Calevents;
    DatabaseHelper hardupdateDB;
    SQLiteDatabase ADDevents;

    ArrayList<Userinfo> UserList;

    //VARIABLES FOR LAST PERIOD DATE ***
    private static final String TAG = "Welcome";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    Integer cycledaystat=0;
    //VARIABLES FOR THE PERIOD RANGE
    private String[] arrayspinnercycledays = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondwcscreen);
            TextView retreiveLP = findViewById(R.id.tvinputlastperiod);
            Spinner disablespinner = findViewById(R.id.spinnerinputcycledays);

        // DETERMINE WHAT WAS THE PREVIOUS SCREEN
        Bundle b = this.getIntent().getExtras();
        Intent nextactivity = getIntent();
        String screencheck= nextactivity.getStringExtra("FromActivity");

        if (screencheck.equals("wcscreen")){
            cycledaystat = Integer.parseInt(b.getString("frmwcscreen"));

        }else if (screencheck.equals("periodtracker")) {
            TextView changedisplay = (TextView)findViewById(R.id.tvWelcometext) ;

            changedisplay.setText("Starting a new period.");
            cycledaystat = Integer.parseInt(b.getString("fromperiodtracker"));

        }else if (screencheck.equals("fromdialog")){
            disablespinner.setEnabled(false);

            retreiveLP.setText(b.getString("passLPval"));
            cycledaystat = Integer.parseInt(b.getString("frmdialog"));

        }




        // FOR DATABASE **********************************
        db = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS periodcycles(ID INTEGER PRIMARY KEY AUTOINCREMENT,LASTPERIOD VARCHAR(20),CYCLEDAYS VARCHAR(20),DATEENDED VARCHAR(20))");
        Calevents = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
        Calevents.execSQL("CREATE TABLE IF NOT EXISTS eventlist(ID INTEGER PRIMARY KEY AUTOINCREMENT,NEXTPERIODDAYS VARCHAR (100),FERTILEDAYS VARCHAR(100),OVULATIONDAYS VARCHAR(20))");


        //The following codes are used to navigate to other screen within the app
        //Button CLICK event
        final Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                //    DECLARATION OF VARIABLES
                //----------------------GETTING CURRENT DATE
                Calendar calendar = Calendar.getInstance();
                DateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy ");
                String strDate = mdformat.format(calendar.getTime());

                //GETTING INPUT VALUES FROM USER
                TextView tvlastperiod = (TextView) findViewById(R.id.tvinputlastperiod);
                Spinner spinnercycledays = (Spinner) findViewById(R.id.spinnerinputcycledays);


                String inputlastperiod = ((TextView) findViewById(R.id.tvinputlastperiod)).getText().toString();
                Integer cycledays = Integer.parseInt(spinnercycledays.getSelectedItem().toString());


                //DATA FOR INSERTING INTO DATABASE
                String DBlastperiod,DBcycledays;
                DBlastperiod= inputlastperiod;
                DBcycledays = cycledays.toString();



                //GET PERIODRANGE FROM DATA BASE
                Cursor c=db.rawQuery("select [PERIODRANGE] from userinfo", null);
                //move cursor to first position
                c.moveToFirst();
                String periodrange=c.getString(c.getColumnIndex("PERIODRANGE"));
                c.close();

                Integer calcdays;
                calcdays = cycledays + Integer.parseInt(periodrange);

                DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date = sdf.parse(inputlastperiod);
                    Calendar newcal = Calendar.getInstance();
                    newcal.setTime(date);
                    newcal.add(Calendar.DAY_OF_MONTH, calcdays);

                    Date rawnextperiod = newcal.getTime();
                    String nextperiod = sdf.format(rawnextperiod);

                    String calcnextperiod = nextperiod;
                    String currentdate = strDate;

                    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                    Date d1 = null;
                    Date d2 = null;
                    Integer daysleft = 0;
                    try {
                        d1 = format.parse(nextperiod);
                        d2 = format.parse(currentdate);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    DateTime dt1 = new DateTime(d1);
                    DateTime dt2 = new DateTime(d2);

                    daysleft = Days.daysBetween(dt2, dt1).getDays();

                    String NA = "N/A";
                    switch (cycledaystat) {
                        case 1: {
                            Toast.makeText(getApplicationContext(), "FROM WELCOME SCREEN:CASE 1", Toast.LENGTH_LONG).show();

                            DateFormat converter = new SimpleDateFormat("MM/dd/yyyy"); // CUSTOM FORMATTING
                            Date extract2date= sdf.parse(DBlastperiod); // PARSING DATE FROM USER INPUT TO BE INSERTED IN THE DATABASE ---- LAST PERIOD
                            Calendar newcalval = Calendar.getInstance(); // INITIALIZED A CALENDAR
                            newcalval.setTime(extract2date); //THE INPUT FROM USER (LASTPERIOD FIELD) HAS BEEN CONVERTED TO CALENDAR OBJECT ---- CONVERTED TO CALENDAR
                            newcalval.add(Calendar.DAY_OF_MONTH, Integer.parseInt(DBcycledays)); //CALCULATION ***** NEWCALVAL= LASTPERIOD + CYCLEDAYS ----- this is the end of period

                            Date endDate = newcalval.getTime(); // VALUE FROM NEWCALVAL CONVERTED TO DATE OBJECT
                            String finalEndDate = converter.format(endDate); // CONVERTED THE NEWCALVAL TO STRING


                            ////////////////////////////////////////////////////////////////////////////////////////////
                            ////////////////** CALCULATE USER'S PERIOD CYCLE FOR CALENDAR**/////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////////////////


                            String CalLperiod = inputlastperiod;
                            String CalPLength = periodrange;

                            Date conv2datefert = null;
                            Date conv2dateNperiod = null;
                            Date conv2dateOVU = null;
                            Date conv2dateendnperiod = null;

                            String nextperiodDate = null;
                            String ovulationday = null;
                            String fertilitydays = null;
                            String endofnextperiod = null;
                            try {
                                Date dateToday = sdf.parse(CalLperiod);
                                // FOR GETTING NEXT PERIOD//////////////////////////////////////////////////////////////////
                                Calendar newCalendar = Calendar.getInstance(); // INSTANTIATE A CALENDAR OBJECT
                                newCalendar.setTime(dateToday);                     // STORED LASTPERIOD AS DATE TO CALENDAR
                                newCalendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(CalPLength)); // NEWCAL=LASTPERIOD+PERIODLENGTH
                                conv2dateNperiod = newCalendar.getTime(); // CONVERTED CALENDAR VALUE TO DATE
                                nextperiodDate = sdf.format(conv2dateNperiod);

                                //FOR GETTING OVULATIONDAY /////////////////////////////////////////////////////////////////
                                Date OVDATE = sdf.parse(nextperiodDate);                // OVDATE = NEXTPERIOD TO DATE
                                Calendar calgetovu = Calendar.getInstance();        // INITIALIZED CALENDAR
                                calgetovu.setTime(OVDATE);                          // CONVERTED OVDATE FROM DATE TO CALENDAR
                                calgetovu.add(Calendar.DAY_OF_MONTH, -14);       // OVULATION = NEXTPERIOD - 14DAYS
                                conv2dateOVU = calgetovu.getTime();                 // CONVERT OVULATION TO STRING
                                ovulationday = sdf.format(conv2dateOVU);             // APPLIED CUSTOM FORMAT TO OVULATION AS STRING

                                // GET FERTILITY ///////////////////////////////////////////////////////////////////////////
                                Date fertdate = sdf.parse(ovulationday);
                                Calendar calgetfert = Calendar.getInstance();
                                calgetfert.setTime(fertdate);
                                calgetfert.add(Calendar.DAY_OF_MONTH, -6);
                                conv2datefert = calgetfert.getTime();
                                fertilitydays = sdf.format(conv2datefert);

                                // FOR GETTING END OF INCLUSIVE DATES FOR THE NEXT PERIOD DAYS /////////////////////////
                                Date incnperioddates = sdf.parse(nextperiodDate);
                                Calendar calgetincperioddates = Calendar.getInstance();
                                calgetincperioddates.setTime(incnperioddates);
                                calgetincperioddates.add(Calendar.DAY_OF_MONTH,(cycledays));
                                conv2dateendnperiod = calgetincperioddates.getTime();
                                endofnextperiod = sdf.format(conv2dateendnperiod);

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
                                S = sdf.parse(nextperiodDate);
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

                            db.execSQL("INSERT INTO periodcycles(LASTPERIOD,CYCLEDAYS,DATEENDED)VALUES ('" + DBlastperiod + "','" + DBcycledays + "','" + finalEndDate + "');");
                            //INSERTED TO DATABASE (PERIODCYCLES) FROM VALUES DBLASTPERIOD,DBCYCLEDAYS, FINALENDDATE


                            Calevents.execSQL("INSERT INTO eventlist(NEXTPERIODDAYS,FERTILEDAYS,OVULATIONDAYS) VALUES ('"+inclusiveNextperiod+"','"+inclusiveFertiledays+"','"+OvulationDay+"');");

                            Intent stores = new Intent(Secondwcscreen.this, Periodtracker.class); // INITIALIZING INTENT TO PROCEED TO NEXT SCREEN
                            startActivity(stores);
                                break;
                        }
                        case 2:{
                            FORLatestLP = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
                            FORLatestLP.execSQL("CREATE TABLE IF NOT EXISTS periodcycles(ID INTEGER PRIMARY KEY AUTOINCREMENT,LASTPERIOD VARCHAR(20),CYCLEDAYS VARCHAR(20),DATEENDED VARCHAR(20))");

                            ADDevents = openOrCreateDatabase("userdata.db", Context.MODE_PRIVATE,null);
                            ADDevents.execSQL("CREATE TABLE IF NOT EXISTS eventlist(ID INTEGER PRIMARY KEY AUTOINCREMENT,NEXTPERIODDAYS VARCHAR (100),FERTILEDAYS VARCHAR(100),OVULATIONDAYS VARCHAR(20))");

                            Cursor Selector=FORLatestLP.rawQuery("select [LASTPERIOD] from periodcycles", null);
                            //move cursor to first position
                            Selector.moveToLast();
                            String GetfromDBLatestLP=Selector.getString(Selector.getColumnIndex("LASTPERIOD"));
                            Selector.close();

                            Toast.makeText(getApplicationContext(), "FROM PERIODTRACKER SCREEN:CASE 2", Toast.LENGTH_LONG).show();

                            Date inputdate = sdf.parse(inputlastperiod);

                            Date getprevdate = sdf.parse(GetfromDBLatestLP);
                            Calendar LatestLPcal = Calendar.getInstance();
                            LatestLPcal.setTime(getprevdate);

                            String prevperiod = sdf.format(LatestLPcal.getTime()); // formatted previous last period to MM/dd/yyyy
                            String newperiod = sdf.format(inputdate);              // formatted new period to MM/dd/yyyy


                            Date PP = null;
                            Date NP = null;
                            Integer daysbetween = 0;
                            try {
                                PP = format.parse(prevperiod);
                                NP = format.parse(newperiod);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            DateTime valPP = new DateTime(PP);
                            DateTime valNP = new DateTime(NP);

                            daysbetween = Days.daysBetween(valPP, valNP).getDays();
                            String newPeriodrange=daysbetween.toString();

                            DateFormat customFormat = new SimpleDateFormat("MM/dd/yyyy"); // CUSTOM FORMATTING
                            Calendar Calmanipulator = Calendar.getInstance(); // INITIALIZED A CALENDAR
                            Calmanipulator.setTime(inputdate); //THE INPUT FROM USER (LASTPERIOD FIELD) HAS BEEN CONVERTED TO CALENDAR OBJECT ---- CONVERTED TO CALENDAR
                            Calmanipulator.add(Calendar.DAY_OF_MONTH, cycledays); //CALCULATION ***** NEWCALVAL= LASTPERIOD + CYCLEDAYS ----- this is the end of period

                            Date endDate = Calmanipulator.getTime(); // VALUE FROM NEWCALVAL CONVERTED TO DATE OBJECT
                            String AddtoDBendDate = customFormat.format(endDate); // CONVERTED THE NEWCALVAL TO STRING

                            hardupdateDB = new DatabaseHelper(getBaseContext());

                            UserList= new ArrayList<>();

                            Integer GETID =1;

                                hardupdateDB.updatePeriodrange(newPeriodrange,GETID);

                                db.execSQL("INSERT INTO periodcycles(LASTPERIOD,CYCLEDAYS,DATEENDED)VALUES ('" + DBlastperiod + "','" + cycledays + "','" + AddtoDBendDate + "');");

                            Date datefertTodate = null;
                            Date NperiodTodate = null;
                            Date OVUTodate = null;
                            Date endnperiodTodate = null;

                            String nextperiodDateTostring = null;
                            String ovulationdayTostring = null;
                            String fertilitydaysTostring = null;
                            String endofnextperiodTostring = null;

                        try{
                            ////////////////////////////////////////////////////////////////////////////////
                            ////////////////////// ** GET NEXT PERIOD START DATE ** ////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////
                            Date startnextperiod = sdf.parse(inputlastperiod);
                            Calendar NPstartDate = Calendar.getInstance();
                            NPstartDate.setTime(startnextperiod);
                            NPstartDate.add(Calendar.DAY_OF_MONTH,(Integer.parseInt(newPeriodrange)));
                            nextperiodDateTostring = sdf.format(NPstartDate.getTime());

                            ////////////////////////////////////////////////////////////////////////////////
                            ////////////////////// ** GET NEXT PERIOD END DATE ** //////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////

                            Date endofNP = sdf.parse(nextperiodDateTostring);
                            Calendar endofNPTocal = Calendar.getInstance();
                            endofNPTocal.setTime(endofNP);
                            endofNPTocal.add(Calendar.DAY_OF_MONTH,cycledays);
                            endofnextperiodTostring=sdf.format(endofNPTocal.getTime());

                            ////////////////////////////////////////////////////////////////////////////////
                            ///////////////////// ** GET OVULATION DATE ** /////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////

                            Date ovulationdate = sdf.parse(nextperiodDateTostring);                // OVDATE = NEXTPERIOD TO DATE
                            Calendar OvudaltionTocal = Calendar.getInstance();        // INITIALIZED CALENDAR
                            OvudaltionTocal.setTime(ovulationdate);                          // CONVERTED OVDATE FROM DATE TO CALENDAR
                            OvudaltionTocal.add(Calendar.DAY_OF_MONTH, -14);       // OVULATION = NEXTPERIOD - 14DAYS
                            OVUTodate = OvudaltionTocal.getTime();                 // CONVERT OVULATION TO STRING
                            ovulationdayTostring = sdf.format(OVUTodate);

                            ////////////////////////////////////////////////////////////////////////////////
                            //////////////////////// ** GET FERTILITY DATES ** /////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////////

                            Date fertilitydate = sdf.parse(ovulationdayTostring);
                            Calendar fertilityTocal = Calendar.getInstance();
                            fertilityTocal.setTime(fertilitydate);
                            fertilityTocal.add(Calendar.DAY_OF_MONTH, -6);
                            datefertTodate = fertilityTocal.getTime();
                            fertilitydaysTostring = sdf.format(datefertTodate);


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
                            S = sdf.parse(nextperiodDateTostring);
                            E = sdf.parse(endofnextperiodTostring);
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
                            SF = sdf.parse(fertilitydaysTostring);
                            EF = sdf.parse(ovulationdayTostring);
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

                        Long longOvulationdate =OVUTodate.getTime();
                        String OvulationDay = longOvulationdate.toString();

                        ////////////////////////////////////////////////////////////////////////////////////////////
                        ////////////////////////// ** END OF CALCULATION ** ////////////////////////////////////////
                        ////////////////////////////////////////////////////////////////////////////////////////////

                            ADDevents.execSQL("INSERT INTO eventlist(NEXTPERIODDAYS,FERTILEDAYS,OVULATIONDAYS) VALUES ('"+inclusiveNextperiod+"','"+inclusiveFertiledays+"','"+OvulationDay+"');");


                            Toast.makeText(getApplicationContext(), "All done!", Toast.LENGTH_LONG).show();

                                Intent startnewact = new Intent(Secondwcscreen.this,Periodtracker.class);
                                startActivity(startnewact);


                            break;
                        }
                        case 3: {


                           db.execSQL("INSERT INTO periodcycles(LASTPERIOD,CYCLEDAYS,DATEENDED)VALUES ('" + DBlastperiod + "','" +NA + "','" + NA + "');");
                            Toast.makeText(getApplicationContext(), "All done!", Toast.LENGTH_LONG).show();

                            Intent stores = new Intent(Secondwcscreen.this, Periodtracker.class);
                            Bundle b = new Bundle();

                            b.putString("daysleft", daysleft.toString());
                            // b.putString("nextperiod", nextperiod.toString());


                            stores.putExtras(b);
                            startActivity(stores);
                        }
                        default:{
                            Toast.makeText(Secondwcscreen.this,"Ughh, i dont know what to do.",Toast.LENGTH_LONG);
                        }

                    }
                }catch (ParseException e) {
                    e.printStackTrace();
                }// catch closing tag

            } //closing tag for onclick view
        });//closing tag for button next click listener


        //The following codes are for the period range
        int counter = 0;
        for (int x = 0; x < 10; x++) {
            counter = x + 1;
            this.arrayspinnercycledays[x] = "" + counter;
        }

        Spinner spinnerinputcycledays = (Spinner) findViewById(R.id.spinnerinputcycledays);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayspinnercycledays);
        spinnerinputcycledays.setAdapter(adapter);

        //The following codes are for the last period date
        final TextView tvinputlastperiod = (TextView) findViewById(R.id.tvinputlastperiod);

        tvinputlastperiod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Secondwcscreen.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });// closing tag for custom date picker

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy:" + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                tvinputlastperiod.setText(date);


                //the current date
                Calendar calendar = Calendar.getInstance();
                DateFormat mdformat = new SimpleDateFormat("MM/dd/yyyy ");
                String currentdate = mdformat.format(calendar.getTime());


                //user input date
                String userinput = date;


                //CONVERT STRINGS TO DATE
                DateFormat conv = new SimpleDateFormat("MM/dd/yyyy");
                Date datetoday = null;
                Date usersdate = null;

                try {
                    datetoday = conv.parse(currentdate);
                    usersdate = conv.parse(userinput);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //CONVERT TO CALENDAR
                Calendar Fdatetoday = Calendar.getInstance();
                Fdatetoday.setTime(datetoday);

                Calendar Fusersdate = Calendar.getInstance();
                Fusersdate.setTime(usersdate);

                long millis1 = Fdatetoday.getTimeInMillis();
                long millis2 = Fusersdate.getTimeInMillis();

                // Calculate difference in milliseconds
                long diff = millis1 - millis2;

                // Calculate difference in seconds
                long diffSeconds = diff / 1000;

                // Calculate difference in minutes
                long diffMinutes = diff / (60 * 1000);

                // Calculate difference in hours
                long diffHours = diff / (60 * 60 * 1000);

                // Calculate difference in days
                long diffDays = diff / (24 * 60 * 60 * 1000);


                if (diffDays<7){

                   // final AlertDialog.Builder mbuilder = new AlertDialog.Builder(Secondwcscreen.this);
                    // custom dialog for the date prompt
                    //final View mview = getLayoutInflater().inflate(R.layout.activity_customdialog,null);

                    //custom dialog for the note dialog
                    //final View notedia = getLayoutInflater().inflate(R.layout.activity_dialognote,null);

                    //widgets for note dialog
                    //TextView note = (TextView) notedia.findViewById(R.id.tvnotehere);
                    //final Button gotit = (Button)notedia.findViewById(R.id.buttongotit);

                    //widgets for date prompt
                    //TextView msg = (TextView) mview.findViewById(R.id.tvconfirmationmsg);
                    //Button yes = (Button)mview.findViewById(R.id.buttonyes);
                    //Button no = (Button)mview.findViewById(R.id.buttonno);

                    Bundle lasperiodfield = new Bundle();
                    lasperiodfield.putString("getLPval",tvinputlastperiod.getText().toString());
                    Intent showdialog = new Intent(Secondwcscreen.this,Customdialog.class);
                    showdialog.putExtras(lasperiodfield);
                    startActivity(showdialog);


                }

            }// closing for onDateset
        }; // closing tag for datelistener
    } //closing tag for onCreate
}//closing tag for the class
