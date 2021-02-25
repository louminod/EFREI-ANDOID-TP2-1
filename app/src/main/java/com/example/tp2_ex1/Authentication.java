package com.example.tp2_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Authentication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the UI elements to this class
        EditText editTextLogin = findViewById(R.id.editTextLogin);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        TextView textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText("My result here");

        // Bind the authentication button and define the action to be performed on click
        Button buttonAuth = findViewById(R.id.buttonAuth);
        buttonAuth.setOnClickListener(view -> {
            URL url = null;
            try {
                // Open a connection to the given url
                url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Get the login and password from the form
                String login = editTextLogin.getText().toString().toLowerCase();
                String password = editTextPassword.getText().toString().toLowerCase();

                // Defining request parameters
                String basicAuth = "Basic " + Base64.encodeToString(String.format("%s:%s", login, password).getBytes(), Base64.NO_WRAP);
                urlConnection.setRequestProperty("Authorization", basicAuth);

                // Create a httpRunnable to perform the request
                HttpRunnable httpRunnable = new HttpRunnable(urlConnection, textViewResult);
                Thread thread = new Thread(httpRunnable);
                thread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}