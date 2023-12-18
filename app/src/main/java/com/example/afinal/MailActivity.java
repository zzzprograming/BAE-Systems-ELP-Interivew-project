package com.example.afinal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class  MailActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button buttonSend;
    private  static  int CAMERA_PERMISSION_CODE = 100;
    private String savedEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isCameraPresentInPhone()){
            Log.i("VIDEO_RECORD_TAG", "Camera is detected");
            getCameraPermission();
        }else{
            Log.i("VIDEO_RECORD_TAG", "No Camera is detected");
        }
        setContentView(R.layout.activity_2);

        savedEmailAddress = getIntent().getStringExtra("emailAddress");

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                if (savedEmailAddress != null && !savedEmailAddress.isEmpty()) {
                    // Send the message to the saved email address
                    sendEmail(savedEmailAddress, message);
                } else {
                    showNoEmailNotification();
                }
            }
        });
    }

    private void sendEmail(String emailAddress, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNoEmailNotification() {
        Toast.makeText(this, "There is no saved email", Toast.LENGTH_SHORT).show();
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
}
