package org.gandroid.motif;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<Periodcycle>PeriodList;
    ListView listView;
    Periodcycle period;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);


        myDB = new DatabaseHelper(this);

        PeriodList= new ArrayList<>();

        Cursor data = myDB.getListContents();

        int numrows =  data.getCount();

        if (numrows==0){
            Toast.makeText(ViewListContents.this,"Database is empty!",Toast.LENGTH_LONG).show();

            String screenval = "1";
            Bundle b = new Bundle();
            b.putString("frmwcscreen",screenval);

            Intent nextactivity = new Intent(ViewListContents.this,Secondwcscreen.class);
            nextactivity.putExtra("FromActivity","wcscreen");
            nextactivity.putExtras(b);
            startActivity(nextactivity);

        }else {

            while (data.moveToNext()){
                period = new Periodcycle (data.getString(1),data.getString     (2),data.getString(3));
                PeriodList.add(period);
            }

            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view,PeriodList);
            listView= (ListView)findViewById(R.id.listviewperiodhistory);
            listView.setAdapter(adapter);
        }
    }
}
