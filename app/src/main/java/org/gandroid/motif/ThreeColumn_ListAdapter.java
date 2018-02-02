package org.gandroid.motif;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<Periodcycle> {

    private ArrayList<Periodcycle> periods;
    private LayoutInflater mInflater;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context,int textViewResourceId,ArrayList<Periodcycle>periods){

        super(context,textViewResourceId,periods);
        this.periods = periods;
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;


    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView=mInflater.inflate(mViewResourceId,null);
        Periodcycle period = periods.get(position);

        if (period!=null){
            TextView lastperiod = (TextView)convertView.findViewById(R.id.Lastperiod);
            TextView cycledays = (TextView)convertView.findViewById(R.id.Dayslasted);
            TextView dateended = (TextView)convertView.findViewById(R.id.Dateended);


            if (lastperiod!=null){
                lastperiod.setText((period.getLPeriod()));
            }

            if (cycledays!=null){
                cycledays.setText((period.getCdays()));
            }

            if (dateended!=null){
                dateended.setText((period.getDEnded()));
            }

        }
        return convertView;
    }

}
