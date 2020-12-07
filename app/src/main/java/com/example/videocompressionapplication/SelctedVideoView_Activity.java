package com.example.videocompressionapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.videocompression.video.VideoCompressor;

import java.io.File;

public class SelctedVideoView_Activity extends AppCompatActivity implements SurfaceHolder.Callback {
private  SurfaceView surfaceView;
private MediaPlayer mediaPlayer;

private Uri vUri;
private Button play, pause, compress;

private com.app.videocompression.video.VideoCompressor videoCompressor = new VideoCompressor(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selcted_video_view_);
pause = findViewById(R.id.pause);
play = findViewById(R.id.play);
compress = findViewById(R.id.compressB);
        final Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
        vUri = Uri.parse(bundle.getString("VideoUri"));}

        surfaceView = findViewById(R.id.surfaceView);
        mediaPlayer = MediaPlayer.create(SelctedVideoView_Activity.this,vUri);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();

        surfaceHolder.addCallback(this);
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.setFixedSize(400,400);



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

        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert bundle != null;
                videoCompressor.startCompressing((String) bundle.get("VideoPath"), new VideoCompressor.CompressionListener() {
                    @Override
                    public void compressionFinished(int status, boolean isVideo, String fileOutputPath) {
                        Toast.makeText(SelctedVideoView_Activity.this, "Compression Started", Toast.LENGTH_SHORT).show();
                        if (videoCompressor.isDone()){
                            Uri uri = Uri.fromFile(new File(fileOutputPath));
                            Toast.makeText(SelctedVideoView_Activity.this, "Compressed", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SelctedVideoView_Activity.this, ConvertedVideoView_Activity.class);
                            intent.putExtra("ConvertedVideo", uri.toString());


                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(SelctedVideoView_Activity.this, message, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onProgress(int progress) {

                    }
                });
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