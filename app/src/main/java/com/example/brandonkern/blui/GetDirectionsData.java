package com.example.brandonkern.blui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by Jordan Desktop on 4/4/2018.
 */

public class GetDirectionsData extends AsyncTask<Object, String, String> {


    String googlePlacesData;
    GoogleMap mMap;
    String url;
    LatLng latlng;
    int type;


    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latlng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s){
        String[] directionsList;

        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        displayDirections(directionsList);
    }

    public void displayDirections(String[] directionsList) {
        PolylineOptions options = new PolylineOptions();
        options.jointType(JointType.ROUND);
        options.color(Color.rgb(0, 179, 253));
        options.width(25);
        int count = directionsList.length;
        for (int i = 0; i < count; i++) {

            options.addAll(PolyUtil.decode(directionsList[i]));
        }

        mMap.addPolyline(options);


    }

}
