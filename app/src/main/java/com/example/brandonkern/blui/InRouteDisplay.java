package com.example.brandonkern.blui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.List;

public class InRouteDisplay extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    //CODES AND TIMEOUTS
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final int REQUEST_LOCATION_CODE = 99;
    //END CODE AND TIMEOUTS

    //GOOGLE MAPS
    GoogleMap gMap;

    private GoogleApiClient gClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocationMarker;

    double latitude, longitude;
    double end_latitude, end_longitude;
    //END GOOGLE MAPS

    //LAYOUTS
    ImageView picture;

    TextView name;
    TextView address;
    TextView time;
    TextView reason;
    TextView requests;

    Button next;
    Button previous;
    Button home;
    //END LAYOUTS

    //ADDRESSES
    String fromNumber;
    String fromStreet;
    String fromCity;
    String fromState;
    String fromZip;

    String toNumber;
    String toStreet;
    String toCity;
    String toState;
    String toZip;

    String from;
    String to;
    //END ADDRESSES

    //SQL IDs
    String client;
    String driverID = "";
    String date = "";
    String rank = "";
    int rankINT = 1;
    boolean last = false;
    //END SQL IDs



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_route_display);

        picture = (ImageView) findViewById(R.id.picture);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        address = (TextView) findViewById(R.id.address);
        reason = (TextView) findViewById(R.id.reason);
        requests = (TextView) findViewById(R.id.requests);

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        home = (Button) findViewById(R.id.home);

        if(getIntent().getStringExtra("rank") != null){
            rank = getIntent().getStringExtra("rank");
            rankINT = Integer.valueOf(rank);
        }
        else {
            rankINT = 1;
        }
        driverID = getIntent().getStringExtra("driverID");
        date = getIntent().getStringExtra("date");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(InRouteDisplay.this, Client_Page.class);
                myIntent.putExtra("id", client);
                myIntent.putExtra("driverID", driverID);
                myIntent.putExtra("rank", rank);
                InRouteDisplay.this.startActivity(myIntent);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(InRouteDisplay.this, Client_Page.class);
                myIntent.putExtra("id", client);
                myIntent.putExtra("driverID", driverID);
                myIntent.putExtra("rank", rank);
                InRouteDisplay.this.startActivity(myIntent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.clear();
                rankINT += 1;
                last = true;
                rank = Integer.toString(rankINT);
                new AsyncRetrieve().execute(driverID, rank, date);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.clear();
                rankINT -= 1;
                last = false;
                rank = Integer.toString(rankINT);
                new AsyncRetrieve().execute(driverID, rank, date);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(InRouteDisplay.this, MainMenu.class);
                myIntent.putExtra("driverID", driverID);
                InRouteDisplay.this.startActivity(myIntent);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkLocationPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_LOCATION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(gClient == null){
                            buildGoogleApiClient();
                        }
                        gMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        rank = Integer.toString(rankINT);
        new AsyncRetrieve().execute(driverID, rank, date);
    }

    public void loadMap(){
        LatLng client = getLocationFromAddress(this, from);
        latitude = client.latitude;
        longitude = client.longitude;

        gMap.addMarker(new MarkerOptions().position(client)
                .title("Pickup").icon(BitmapDescriptorFactory.defaultMarker(207)));

        LatLng dropoff = getLocationFromAddress(this, to);
        end_latitude = dropoff.latitude;
        end_longitude = dropoff.longitude;

        gMap.addMarker(new MarkerOptions().position(dropoff)
                .title("Dropoff").icon(BitmapDescriptorFactory.defaultMarker(312)));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(client));
        // Enable my location if we have permission
        if (haveLocationPermission(this))
        {
            try
            {
                buildGoogleApiClient();
                gMap.setMyLocationEnabled(true);
            }
            catch (SecurityException e) {}
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(client);
        builder.include(dropoff);

        LatLngBounds bounds = builder.build();

        int padding = 200; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        gMap.moveCamera(cu);

        Object dataTransfer[] = new Object[3];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();

        String url = getDirectionsUrl();
        GetDirectionsData getDirectionsData = new GetDirectionsData();
        dataTransfer[0] = gMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(end_latitude, end_longitude);
        getDirectionsData.execute(dataTransfer);

    }

    private String getDirectionsUrl(){
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyDF2VriLp5h75LtjH58JEav-WUCd6JzbCQ");

        return googleDirectionsUrl.toString();
    }

    public static boolean haveLocationPermission(Context context){
        return ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    protected synchronized void buildGoogleApiClient(){
        gClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        gClient.connect();
    }


    public LatLng getLocationFromAddress(Context context, String strAddress){
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        /*
        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentLocationMarker = gMap.addMarker(markerOptions);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomBy(10));
        */
        if(gClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(gClient, this);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();

        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(gClient, locationRequest, this);
        }
    }

    public boolean checkLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else{
            return false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //-------------------------------------------------------------------------------------------

    private class AsyncRetrieve extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(InRouteDisplay.this);
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
            String rank = params[1];
            String date = params[2];

            try {
                // Enter URL address where your php file resides
                url = new URL("https://cgi.soic.indiana.edu/~team29/Beloved%20Transportation/Application/lib/mapRequest.php");

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
                        URLEncoder.encode("rank", "UTF-8") + "=" + URLEncoder.encode(rank, "UTF-8") + "&" +
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
            if(result.equals("no more results")){
                if(last){
                    rankINT -= 1;
                    rank = Integer.toString(rankINT);
                }
                else{
                    rankINT += 1;
                    rank = Integer.toString(rankINT);
                }

                new AlertDialog.Builder(InRouteDisplay.this)
                        .setMessage("No more routes left to load")
                        .setCancelable(false)
                        .setNegativeButton("Okay", null)
                        .show();

                //new AsyncRetrieve().execute(driverID, rank, date);
            }
            else {

                try {
                    JSONObject json = (new JSONArray(result)).getJSONObject(0);

                    fromNumber = json.getString("pickupNumber");
                    fromStreet = json.getString("pickupStreet");
                    fromCity = json.getString("pickupCity");
                    fromState = json.getString("pickupState");
                    fromZip = json.getString("pickupZip");

                    InRouteDisplay.this.address.setText(fromCity);
                    InRouteDisplay.this.name.setText(json.getString("fname") + " " + json.getString("lname"));
                    InRouteDisplay.this.time.setText(json.getString("time"));
                    InRouteDisplay.this.from = fromNumber + ", " + fromStreet + ", " + fromCity + ", " + fromState + ", " + fromZip;

                    InRouteDisplay.this.reason.setText(json.getString("reason"));
                    InRouteDisplay.this.requests.setText(json.getString("requests"));

                    toNumber = json.getString("destinationNumber");
                    toStreet = json.getString("destinationStreet");
                    toCity = json.getString("destinationCity");
                    toState = json.getString("destinationState");
                    toZip = json.getString("destinationZip");

                    InRouteDisplay.this.client = json.getString("clientID");

                    InRouteDisplay.this.to = toNumber + ", " + toStreet + ", " + toCity + ", " + toState + ", " + toZip;


                    InRouteDisplay.this.loadMap();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

