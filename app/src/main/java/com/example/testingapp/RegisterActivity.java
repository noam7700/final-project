package com.example.testingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        final EditText usernameEditText = (EditText) findViewById(R.id.activity_register_usernameEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.activity_register_passwordEditText);

        final Button registerBtn = (Button) findViewById(R.id.activity_register_registerBtn);
        registerBtn.setText(R.string.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MyAccountActivity.class);
                //TODO: update user's data(maybe an object)

                MainActivity.loggedUser = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                MainActivity.isLoggedIn = true;
                startActivity(startIntent);
            }
        });

    }
}
