package com.example.brandonkern.blui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Contact_Page extends AppCompatActivity {

    Button office;
    Button chris;
    Button home;

    String driverID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__page);

        office = findViewById(R.id.office);
        chris = findViewById(R.id.chris);
        home = findViewById(R.id.home);

        driverID = getIntent().getStringExtra("driverID");


        office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Contact_Page.this)
                        .setMessage("Are you sure you want to call Office?")
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



        chris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Contact_Page.this)
                        .setMessage("Are you sure you want to call Dwanye?")
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

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Contact_Page.this, MainMenu.class);
                myIntent.putExtra("driverID", driverID);
                Contact_Page.this.startActivity(myIntent);

            }
        });



    }
}