package com.example.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InputDetailsActivity extends AppCompatActivity {

    private EditText editTextPhoneNumber;
    private EditText editTextEmailAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String emailAddress = editTextEmailAddress.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("emailAddress", emailAddress);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
