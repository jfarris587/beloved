package com.example.brandonkern.blui;

//https://www.youtube.com/watch?v=hHjFIG0TtA0

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class MonthSchedule extends AppCompatActivity {

    private CalendarView mCalendarView;
    Button home;
    Button today;

    String driverID;
    String todayDate;
    String formattedDate;
    String formattedDate2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_schedule);
        home= findViewById(R.id.home);
        today = findViewById(R.id.today);
        mCalendarView= (CalendarView) findViewById(R.id.calendar);

        driverID = getIntent().getStringExtra("driverID");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM d, yyyy");
        formattedDate = df2.format(c);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month +1) + "/" + dayOfMonth + "/" + year;

                Date c = new Date(year-1900, month, dayOfMonth);

                SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
                formattedDate = df.format(c);

                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
                todayDate = df2.format(c);

                Intent myIntent = new Intent(MonthSchedule.this, Daily_Schedule.class);
                myIntent.putExtra("date", formattedDate);
                myIntent.putExtra("today", todayDate);
                myIntent.putExtra("driverID", driverID);
                MonthSchedule.this.startActivity(myIntent);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MonthSchedule.this, MainMenu.class);
                myIntent.putExtra("driverID", driverID);
                MonthSchedule.this.startActivity(myIntent);

            }
        });

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate2 = df.format(c);

                Intent myIntent = new Intent(MonthSchedule.this, Daily_Schedule.class);
                myIntent.putExtra("date", formattedDate);
                myIntent.putExtra("today", formattedDate2);
                myIntent.putExtra("driverID", driverID);
                MonthSchedule.this.startActivity(myIntent);

            }
        });

    }
}
