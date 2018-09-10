package com.example.brandonkern.blui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class Client_Page extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    TextView name;
    TextView street;
    TextView state;
    TextView dob;
    TextView physical;
    TextView mental;

    Button call;
    Button text;

    Button map;
    Button home;

    String clientID;
    String driverID;
    String rank;

    String phone = "8129872973";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client__page);
        clientID = getIntent().getStringExtra("id");
        rank = getIntent().getStringExtra("rank");
        driverID = getIntent().getStringExtra("driverID");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);



        name = (TextView) findViewById(R.id.name);
        street = (TextView) findViewById(R.id.street);
        state = (TextView) findViewById(R.id.state);
        dob = (TextView) findViewById(R.id.dob);
        physical = (TextView) findViewById(R.id.physical);
        mental = (TextView) findViewById(R.id.mental);

        call = (Button) findViewById(R.id.call);
        text = (Button) findViewById(R.id.text);

        map = (Button) findViewById(R.id.map);
        home = findViewById(R.id.home);

        new Client_Page.AsyncRetrieve().execute(clientID);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Client_Page.this, MainMenu.class);
                myIntent.putExtra("driverID", driverID);
                Client_Page.this.startActivity(myIntent);

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Client_Page.this)
                        .setMessage("Are you sure you want to call the client?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:8127288030"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });





        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Client_Page.this)
                        .setMessage("Are you sure you want send a text alert to the client?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String messageToSend = "Your Beloved Transportation Driver is waiting for you outside...";
                                String number = phone;
                                SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Client_Page.this, InRouteDisplay.class);
                myIntent.putExtra("rank", rank);
                myIntent.putExtra("driverID", driverID);

                Client_Page.this.startActivity(myIntent);
            }
        });
    }

    //-------------------------------------------------------------------------------------------

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Client_Page.this);
        HttpURLConnection conn;
        URL url = null;

        //this method will interact with UI, here display loading message
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        // This method does not interact with UI, You need to pass result to onPostExecute to display
        @Override
        protected String doInBackground(String... params) {
            String id = params[0];

            try {
                // Enter URL address where your php file resides
                url = new URL("https://cgi.soic.indiana.edu/~team29/Beloved%20Transportation/Application/lib/client.php");

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
                String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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

            try {
                JSONObject json = (new JSONArray(result)).getJSONObject(0);
                Client_Page.this.phone = json.getString("phone");
                name.setText(json.getString("name"));
                street.setText(json.getString("street"));
                state.setText(json.getString("state"));
                dob.setText("Date of Birth: " + json.getString("dob"));
                physical.setText("Physical Condition: " + json.getString("physical"));
                mental.setText("Mental Condition: " + json.getString("mental"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

