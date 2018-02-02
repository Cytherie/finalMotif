package org.gandroid.motif;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Videos extends AppCompatActivity {

    int[] THUMBNAILS = {R.drawable.thumbfpadvertisement,R.drawable.thumbbirthcontpill,R.drawable.thumbcontraceptiveswork,
            R.drawable.thumbbarrier,R.drawable.thumbhormonal,R.drawable.thumbhormonalanim,
            R.drawable.thumbhormonaldiscuss,R.drawable.thumbpermaiudcopperanim,R.drawable.thumbnatural,
            R.drawable.thumbnaturalanim,R.drawable.thumbpermafemalesterilization,R.drawable.thumbpermatuballigation,
            R.drawable.thumbpermavasectomy};
    String [] TITLE = {"Family Planning advertisement from DOH","Birth control pills",
                        "How contraceptives work","Barrier method",
                        "Hormonal method explained","Hormonal method explaination from Durex",
                        "Different kinds of hormonal methods","(IUD)Intrauterine Contraceptive Device",
                        "Natural method of family planning","Natural Family Planning: The best worst thing ever",
                        "Permanent method: Female Sterilization","Permanent method: Tubal Ligation",
                        "Permanent method: Male Vasectomy"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        ListView listView = (ListView)findViewById(R.id.VideoListview);
        Videos.CustomAdapter customAdapter= new Videos.CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selection= TITLE[i];
                Intent nextactivity;
                nextactivity = new Intent(Videos.this,AndroidVideoView.class);

                switch (selection){
                    case "Family Planning advertisement from DOH":
                        nextactivity.putExtra("Key","fpadvertisement");
                        startActivity(nextactivity);
                        break;

                    case "Birth control pills":
                        nextactivity.putExtra("Key","birthcontpills");
                        startActivity(nextactivity);
                        break;

                    case "How contraceptives work":
                        nextactivity.putExtra("Key","contraceptiveswork");
                        startActivity(nextactivity);
                        break;

                    case "Barrier method":
                        nextactivity.putExtra("Key","barrier");
                        startActivity(nextactivity);
                        break;

                    case "Hormonal method explained":
                        nextactivity.putExtra("Key","hormonal");
                        startActivity(nextactivity);
                        break;

                    case "Hormonal method explaination from Durex":
                        nextactivity.putExtra("Key","hormonalanim");
                        startActivity(nextactivity);
                        break;

                    case "Different kinds of hormonal methods":
                        nextactivity.putExtra("Key","hormonaldiscuss");
                        startActivity(nextactivity);
                        break;

                    case "(IUD)Intrauterine Contraceptive Device":
                        nextactivity.putExtra("Key","iudcopperanim");
                        startActivity(nextactivity);
                        break;

                    case "Natural method of family planning":
                        nextactivity.putExtra("Key","natural");
                        startActivity(nextactivity);
                        break;

                    case "Natural Family Planning: The best worst thing ever":
                        nextactivity.putExtra("Key","naturalanim");
                        startActivity(nextactivity);
                        break;

                    case "Permanent method: Female Sterilization":
                        nextactivity.putExtra("Key","permafemalesterilization");
                        startActivity(nextactivity);
                        break;

                    case "Permanent method: Tubal Ligation":
                        nextactivity.putExtra("Key","permatuballigation");
                        startActivity(nextactivity);
                        break;

                    case "Permanent method: Male Vasectomy":
                        nextactivity.putExtra("Key","permavasectomy");
                        startActivity(nextactivity);
                        break;
                }

            }
        });


       /** String filename = "fpadvertisement";

        String filepath = "android.resource://"+getPackageName()+"/raw/" + filename;

        VideoView videoView = (VideoView)findViewById(R.id.videoAds);
        videoView.setVideoURI(Uri.parse(filepath));

       videoView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               VideoView videoView = (VideoView)findViewById(R.id.videoAds);
               videoView.start();
           }
       });
        videoView.setMediaController(new MediaController(this)); **/

    }

    class  CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return THUMBNAILS.length;
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

            view = getLayoutInflater().inflate(R.layout.row,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.VideoThumbnail);
            TextView textviewVtitle = (TextView)view.findViewById(R.id.VideoTitle);


            imageView.setImageResource(THUMBNAILS[i]);
            textviewVtitle.setText(TITLE[i]);
            return view;
        }
    }
}
