package com.example.brandonkern.blui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Daily_Schedule extends AppCompatActivity {

    //TIMEOUTS
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    //END TIMEOUTS

    //LAYOUT
    TextView date;
    ListView schedule;
    Button map;
    Button home;
    //END LAYOUT

    //DAILY LIST
    String first = "first name";

    HashMap<String, String> ids = new HashMap<>();
    SimpleAdapter adapter;
    List<HashMap<String, String>> listItem;
    //END DAILY LIST

    //SQL IDs
    String driverID;
    String today;
    //END SQL IDs


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily__schedule);
        date = (TextView) findViewById(R.id.date);
        map = (Button) findViewById(R.id.map);
        home = (Button) findViewById(R.id.home);
        if(getIntent().getStringExtra("date") !=null) {
            date.setText(getIntent().getStringExtra("date"));
        }

        schedule = (ListView) findViewById(R.id.schedule);

        driverID = getIntent().getStringExtra("driverID");
        today = getIntent().getStringExtra("today");


        listItem = new ArrayList<>();
        adapter = new SimpleAdapter(this, listItem, R.layout.list_item,
                new String[]{"First Line", "Last Line"},
                new int[]{R.id.text1, R.id.text2});

        new Daily_Schedule.AsyncRetrieve().execute(driverID, today);

        schedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> obj = (HashMap<String, Object>) adapter.getItem(position);
                String result = (String) obj.get("First Line");

                Intent myIntent = new Intent(Daily_Schedule.this, InRouteDisplay.class);
                myIntent.putExtra("rank", ids.get(result));
                myIntent.putExtra("driverID", driverID);
                myIntent.putExtra("date", today);

                Daily_Schedule.this.startActivity(myIntent);

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultMap();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Daily_Schedule.this, MainMenu.class);
                myIntent.putExtra("driverID", driverID);

                Daily_Schedule.this.startActivity(myIntent);

            }
        });

    }

    public void defaultMap(){
        Intent myIntent = new Intent(Daily_Schedule.this, InRouteDisplay.class);
        myIntent.putExtra("driverID", driverID);
        myIntent.putExtra("date", today);
        Daily_Schedule.this.startActivity(myIntent);

    }

    public void setList(HashMap<String, String> hash){



        Iterator it = hash.entrySet().iterator();
        while(it.hasNext()){
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Last Line", pair.getValue().toString());
            listItem.add(resultsMap);
        }

        schedule.setAdapter(adapter);
    }

    //-------------------------------------------------------------------------------------------

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Daily_Schedule.this);
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
            String today = params[1];

            try {
                // Enter URL address where your php file resides
                url = new URL("https://cgi.soic.indiana.edu/~team29/Beloved%20Transportation/Application/lib/today.php");

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
                        URLEncoder.encode("today", "UTF-8") + "=" + URLEncoder.encode(today, "UTF-8");


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

                JSONArray array = new JSONArray(result);
                JSONObject object;
                LinkedHashMap<String, String> daily = new LinkedHashMap<>();

                for(int i = 0; i < array.length(); i++){
                    object = array.getJSONObject(i);
                    daily.put(object.getString("client"), object.getString("time") + ": " + object.getString("pickupCity")
                    + ", " + object.getString("pickupState"));

                    Daily_Schedule.this.ids.put(object.getString("client"), object.getString("rank"));

                    if(i==0){
                        Daily_Schedule.this.first = object.getString("client");
                    }

                }

                setList(daily);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

