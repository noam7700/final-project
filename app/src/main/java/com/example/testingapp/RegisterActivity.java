package com.example.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        final EditText usernameEditText = (EditText) findViewById(R.id.activity_register_usernameEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.activity_register_passwordEditText);
        TextView activity_register_titleTextView = (TextView) findViewById(R.id.activity_register_titleTextView);
        TextView activity_register_errorMessageTextView = (TextView) findViewById(R.id.activity_register_errorMessageTextView);

        activity_register_errorMessageTextView.setText(""); //for now empty
        activity_register_titleTextView.setText(R.string.register);

        final Button registerBtn = (Button) findViewById(R.id.activity_register_registerBtn);
        registerBtn.setText(R.string.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Noam: "I don't understand this 'to_do', but I'll leave it for amit to figure it out"
                //TODO: update user's data(maybe an object) -- WHAT1?!@$

                //dont login, just create temp user to register to server, and ask from him to login afterwards
                User temp = new User(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                //TODO: check with amit the server
                /*String errorMessage = temp.register();
                activity_register_errorMessageTextView.setText(errorMessage);
                if(!errorMessage.equals(""))
                    return; //dont go to login. he should try again*/

                //goto loginActivity
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);
            }
        });

    }
}
