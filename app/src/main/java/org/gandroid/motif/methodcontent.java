package org.gandroid.motif;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class methodcontent extends AppCompatActivity{
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.methodcontent);

       // TextView contentTitle = findViewById(R.id.FPtitle);
       // TextView contentBody = findViewById(R.id.FPBriefsumm);

        Intent nextactivity = getIntent();
        String choice= nextactivity.getStringExtra("Key");

        switch (choice){

            case "Family Planning":

                webView = findViewById(R.id.contentWEBVIEW);
                webView.loadUrl("file:///android_asset/familyplanning.html");
                webView.setBackgroundColor(Color.TRANSPARENT);

                break;

            case "Barrier":

                webView = findViewById(R.id.contentWEBVIEW);
                webView.loadUrl("file:///android_asset/barrier.html");
                webView.setBackgroundColor(Color.TRANSPARENT);

                break;

            case "Hormonal":

                webView = findViewById(R.id.contentWEBVIEW);
                webView.loadUrl("file:///android_asset/hormonal.html");
                webView.setBackgroundColor(Color.TRANSPARENT);
                break;

            default:
                Toast.makeText(this,"Sorry no available resources.",Toast.LENGTH_LONG);

        }

    }
}
