package com.example.testingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class DebugOptionsActivity extends AppCompatActivity {

    public static boolean isOnlineMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_options);

        EditText newServerIpEditText = findViewById(R.id.newServerIpEditText);
        CheckBox isOnlineMode = findViewById(R.id.isOnlineMode);
        Button saveDebugChangesBtn = findViewById(R.id.saveDebugChangesBtn);

        //default values when entering the activity:
        newServerIpEditText.setText(User.server_default_ip);
        isOnlineMode.setChecked(DebugOptionsActivity.isOnlineMode);

        saveDebugChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.server_default_ip = newServerIpEditText.getText().toString(); //change IP
                DebugOptionsActivity.isOnlineMode = isOnlineMode.isChecked(); //change onlineMode
            }
        });
    }
}
