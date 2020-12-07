package com.example.videocompressionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class ConvertedVideoView_Activity extends AppCompatActivity implements SurfaceHolder.Callback {
private Button play, pause;

private SurfaceView surfaceView;
private MediaPlayer mediaPlayer;

private Uri uri;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converted_video_view_);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            uri = Uri.parse(bundle.getString("ConvertedVideo"));
        }

        pause = findViewById(R.id.pause2);
        play = findViewById(R.id.play2);


        mediaPlayer = MediaPlayer.create(ConvertedVideoView_Activity.this, uri);

        surfaceView = findViewById(R.id.surfaceView2);
        SurfaceHolder holder = surfaceView.getHolder();

        holder.setFixedSize(400,400);
        holder.addCallback(this);
        holder.setKeepScreenOn(true);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();}
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }

            }
        });

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}