package org.gandroid.motif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Customdialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customdialog);

        Button yes = findViewById(R.id.buttonyes);
        Button no = findViewById(R.id.buttonno);

        final Bundle b =this.getIntent().getExtras();
        Intent prevact = getIntent();


        final String passinggetLPval=b.getString("getLPval");
        Toast.makeText(Customdialog.this,"Data from prev screen:"+passinggetLPval.toString(),Toast.LENGTH_LONG);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle g = new Bundle();

                g.putString("passLPval",passinggetLPval.toString());
                g.putString("frmdialog","3");

                Intent gobacktosecondscreen = new Intent(Customdialog.this,Secondwcscreen.class);
                gobacktosecondscreen.putExtra("FromActivity","fromdialog");
                gobacktosecondscreen.putExtras(g);
                startActivity(gobacktosecondscreen);
            }
        });//closing tag for button yes on click listener


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Customdialog.this.finish();

            }
        });
    }
}
