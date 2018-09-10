package com.example.brandonkern.blui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainMenu extends AppCompatActivity {

    //LAYOUT
    Button resume;
    Button today;
    Button schedule;
    Button contact;
    Button logout;
    //END LAYOUT

    String date;
    String driverID;
    String firstRoute = "1";
    String formattedDate;
    String formattedDate2;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        resume = findViewById(R.id.resume);
        today = findViewById(R.id.today);
        schedule = findViewById(R.id.schedule);
        contact = findViewById(R.id.contact);
        logout = findViewById(R.id.logout);

        driverID = getIntent().getStringExtra("driverID");
        date = "2018-04-18";

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM d, yyyy");
        formattedDate2 = df2.format(c);


        new MainMenu.AsyncRetrieve().execute(driverID, date);


        //ON CLICK LISTENERS FOR BUTTONS
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenu.this, InRouteDisplay.class);
                myIntent.putExtra("driverID", driverID);
                myIntent.putExtra("date", formattedDate);
                MainMenu.this.startActivity(myIntent);
            }
        });

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenu.this, Daily_Schedule.class);
                myIntent.putExtra("date", formattedDate2);
                myIntent.putExtra("today", formattedDate);
                myIntent.putExtra("driverID", driverID);
                MainMenu.this.startActivity(myIntent);

            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenu.this, MonthSchedule.class);
                myIntent.putExtra("driverID", driverID);
                MainMenu.this.startActivity(myIntent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenu.this, Contact_Page.class);
                myIntent.putExtra("driverID", driverID);
                MainMenu.this.startActivity(myIntent);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenu.this, MainLogin.class);
                MainMenu.this.startActivity(myIntent);

            }
        });


    }

    //-------------------------------------------------------------------------------------------

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainMenu.this);
        HttpURLConnection conn;
        URL url = null;

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        // This method does not interact with UI, You need to pass result to onPostExecute to display
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String date = params[1];

            try {
                // Enter URL address where your php file resides
                url = new URL("https://cgi.soic.indiana.edu/~team29/Beloved%20Transportation/Application/lib/resume.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

                //MY STUFF HERE
                OutputStream OS = conn.getOutputStream();
                BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");

                bf.write(data);
                bf.flush();
                bf.close();
                OS.close();



            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        // this method will interact with UI, display result sent from doInBackground method
        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();
            MainMenu.this.firstRoute = result;


        }

    }
}

