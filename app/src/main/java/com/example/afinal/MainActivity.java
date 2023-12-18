package com.example.afinal;

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
import android.widget.Toast;

import com.example.afinal.MailActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonOpenInput;
    private Button buttonEmergencyCall;
    private Button buttonOpenMail;

    private static final int REQUEST_CODE_INPUT_DETAILS = 1;
    private String savedPhoneNumber;
    private String savedEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOpenInput = findViewById(R.id.button1);
        buttonOpenInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputDetailsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_INPUT_DETAILS);
            }
        });

        buttonEmergencyCall = findViewById(R.id.button5);
        buttonEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedPhoneNumber != null && !savedPhoneNumber.isEmpty()) {
                    makePhoneCall(savedPhoneNumber);
                } else {
                    showNoPhoneNumberNotification();
                }
            }
        });

        buttonOpenMail = findViewById(R.id.button2);
        buttonOpenMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedEmailAddress != null && !savedEmailAddress.isEmpty()) {
                    openMailActivity();
                } else {
                    showNoEmailAddressNotification();
                }
            }
        });

        Button news = findViewById(R.id.button4);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        Button speed = findViewById(R.id.button3);
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makePhoneCall(String phoneNumber) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void showNoPhoneNumberNotification() {
        Toast.makeText(this, "There is no saved phone number", Toast.LENGTH_SHORT).show();
    }

    private void openMailActivity() {
        Intent intent = new Intent(MainActivity.this, MailActivity.class);
        intent.putExtra("emailAddress", savedEmailAddress); // Pass the value of savedEmailAddress
        startActivity(intent);
    }

    private void showNoEmailAddressNotification() {
        Toast.makeText(this, "There is no saved email address", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_INPUT_DETAILS && resultCode == RESULT_OK) {
            if (data != null) {
                savedPhoneNumber = data.getStringExtra("phoneNumber");
                savedEmailAddress = data.getStringExtra("emailAddress");
            }
        }
    }
}
