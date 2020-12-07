package com.example.videocompressionapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_CONFIRMATION =1 ;
    private static final int VIDEO_PERMISSION = 2;
    private Button uploadB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadB = findViewById(R.id.uploadButton);

        uploadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                  getVideo();
              }else {
                  ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, VIDEO_PERMISSION);
              }
            }
        });
    }

    public void getVideo(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");

        startActivityForResult(intent, VIDEO_CONFIRMATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VIDEO_CONFIRMATION && resultCode == RESULT_OK){
            if (data!=null){
            String videoUri = Objects.requireNonNull(data.getData()).toString();

            Intent i = new Intent(MainActivity.this, SelctedVideoView_Activity.class);
            i.putExtra("VideoUri", videoUri);
            i.putExtra("VideoPath", data.getData().getPath());

            startActivity(i);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == VIDEO_PERMISSION){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getVideo();
            }
        }
    }
}