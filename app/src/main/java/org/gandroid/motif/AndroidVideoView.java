package org.gandroid.motif;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

public class AndroidVideoView extends AppCompatActivity {

    /**
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController; **/
    FullscreenVideoLayout videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_android_video_view);

        videoView = (FullscreenVideoLayout) findViewById(R.id.videoview);


        videoView.setActivity(this);
/**
        try {

            int id;
            Intent nextactivity = getIntent();
            String choice = nextactivity.getStringExtra("Key");

            switch (choice) {

                case "fpadvertisement":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);

                    break;

                case "birthcontpills":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "contraceptiveswork":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "barrier":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "hormonal":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "hormonalanim":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "hormonaldiscuss":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "iudcopperanim":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "natural":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "naturalanim":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "permafemalesterilization":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "permatuballigation":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;

                case "permavasectomy":

                    // ID of video file.
                    id = this.getRawResIdByName(choice);
                    videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                    videoView.setShouldAutoplay(true);
                    break;
            }

            } catch (IOException e) {
            e.printStackTrace();
        } **/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


        /**
        videoView = (VideoView) findViewById(R.id.videoView);

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(AndroidVideoView.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);


            try {

                int id;
                Intent nextactivity = getIntent();
                String choice = nextactivity.getStringExtra("Key");

                switch (choice) {

                    case "fpadvertisement":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "birthcontpills":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "contraceptiveswork":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "barrier":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "hormonal":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "hormonalanim":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "hormonaldiscuss":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "iudcopperanim":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "natural":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "naturalanim":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "permafemalesterilization":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "permatuballigation":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;

                    case "permavasectomy":

                        // ID of video file.
                        id = this.getRawResIdByName(choice);
                        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
                        break;


                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

        }
        videoView.requestFocus();


        // When the video file ready for playback.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

    }
**/

    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

/**
    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    } **/

}

