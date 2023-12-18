package com.example.afinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Bitmap;
        import android.graphics.Color;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.MediaController;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.VideoView;

public class AccActivity extends AppCompatActivity {
    private Accelerometer accelerometer;
    private  static  int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORD_CODE = 101;
    private static int IMAGE_RECORD_CODE = 102;
    private static int SELECT_IMAGE_CODE = 1;
    Uri videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        if (isCameraPresentInPhone()){
            Log.i("VIDEO_RECORD_TAG", "Camera is detected");
            getCameraPermission();
        }else{
            Log.i("VIDEO_RECORD_TAG", "No Camera is detected");
        }

        accelerometer = new Accelerometer(this );
        accelerometer.register();
        accelerometer.setListener(new Accelerometer.Listener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                TextView textView = (TextView) findViewById(R.id.Text_To_Display);
                if(tx > 5.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    textView.setText("@string/speed" + tx + "\n" + "Warning");
                    Log.i("inform","slowing down");
                    recordVideoButPressed();
                } else if (tx < 5.0f & tx > 0.01f) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                    textView.setText("@string/speed:" + tx + "\n" + "Keep Constant Speed");
                    Log.i("inform","good speed");
                }else if (tx < 0.01f) {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    textView.setText("@string/speed:" + tx + "\n" + "Speed Up");
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        accelerometer.register();

    }
    @Override
    public void onPause(){
        super.onPause();
        accelerometer.unregister();
        Log.e("Error","on pause");
    }
    public void recordVideoButPressed(){
        recordVideo();
    }
    public void captureImageButPressed(View view){
        captureImage();
    }
    public void chooseDisplay(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Title"),SELECT_IMAGE_CODE);
    }
    private boolean isCameraPresentInPhone(){
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }else {
            return false;
        }
    }
    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }
    }
    private  void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);
    }
    private  void captureImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
        startActivityForResult(intent, IMAGE_RECORD_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_RECORD_CODE){
            if (resultCode == RESULT_OK) {
                videoPath = data.getData();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",  Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",  Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == IMAGE_RECORD_CODE){
            if (resultCode == RESULT_OK){
                Bitmap imgbitmap = (Bitmap)data.getExtras().get("data");
                Log.i("VIDEO_RECORD_TAG", "Video is recorded and avaialbe at path" + videoPath);
            }
            else if(resultCode == RESULT_CANCELED){
                Log.i("VIDEO_RECORD_TAG", "Video is cancelled");
            }
            else{
                Log.i("VIDEO_RECORD_TAG", "Recording video Video has get some error");
            }
        }
        if (requestCode == SELECT_IMAGE_CODE) {
            Uri uri = data.getData();
        }
    }
}