package com.example.xyz.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import utils.PreferenceUtils;

public class SignUpActivity extends AppCompatActivity {

    EditText email, username, password;
    Button signup;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.etEmail);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etSPassword);
        signup = (Button) findViewById(R.id.btnSignup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        context = getApplication();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceUtils.saveSignupCredentials(SignUpActivity.this, email.getText().toString(), username.getText().toString(), password.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                Log.e("username", ""+ PreferenceUtils.getUsername(context).toString());
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
