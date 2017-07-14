package com.example.xyz.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import model.UserDetails;
import utils.PreferenceUtils;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public TextView signUp, resultData;
    public ProgressBar progressBar;
    public Button login;
    Context context;
    public String get_username, get_password;

    private final String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        signUp = (TextView) findViewById(R.id.etSignup);
        resultData = (TextView) findViewById(R.id.etResult);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        context = getApplication();

        final UserDetails userDetails = new UserDetails();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString() != PreferenceUtils.getUsername(context).toString() &&
                        password.getText().toString() != PreferenceUtils.getPassword(context).toString()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username or Password is invalid", Toast.LENGTH_LONG).show();
                }

                get_username = username.getText().toString();
                get_password = password.getText().toString();

                /*userDetails.setUsername(get_username);
                userDetails.setPassword(get_password);*/

                //(new AuthenticateTask(context)).execute();

                //new AuthenticateTask().execute(URL, get_username, get_password);
            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HitActivity.class);
                startActivity(intent);
            }
        });
    }

    public class AuthenticateTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            /*String credentials = username + ":" +password;
            String basicAuth = "Basic " + new String(new Base64().encode(credentials.getBytes()));*/

            try {

                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty ("", "");
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Language", "en-US");
                connection.connect();

                Log.e("connection", ""+connection);

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                Log.e("result", ""+buffer.toString());
                //String finalJson = buffer.toString();

                //JSONObject parentObject = new JSONObject(finalJson);
                //JSONArray parentArray = parentObject.getJSONArray("item");

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } /*catch (JSONException e) {
                e.printStackTrace();
            }*/ finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            resultData.setText(result);
        }
    }
}

