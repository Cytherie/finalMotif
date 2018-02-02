package org.gandroid.motif;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcomebackscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomebackscreen);

        Button buttonwbyes = findViewById(R.id.buttonwbyes);
        Button buttonwbno = findViewById(R.id.buttonwbno);

        buttonwbyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextact = new Intent(Welcomebackscreen.this,Periodtracker.class);
                startActivity(nextact);

                Toast.makeText(Welcomebackscreen.this,"Come back when your period ends!",Toast.LENGTH_LONG).show();
            }
        });

        buttonwbno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextact = new Intent(Welcomebackscreen.this,Secondwcscreen.class);
                startActivity(nextact);

            }
        });

    }
}
