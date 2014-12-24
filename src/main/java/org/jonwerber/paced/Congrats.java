package org.jonwerber.paced;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jonathanwerber on 12/15/14.
 */

public class Congrats extends ActionBarActivity  {

    private double distance;


    private long startTime;
    private long endTime;
    private long runTime;

    private long timeOne;
    private long timeTwo;
    private long timeTraveled;
    private double distanceUpdate;
    private double distanceTraveled;
    private String distanceUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.congrats);

        Bundle c = getIntent().getExtras();
        distanceUnit = c.getString("distanceUnit");
        runTime = c.getLong("runTime");
        distance = c.getDouble("distance");
        distanceUnit = c.getString("distanceUnit");



        final com.mobsandgeeks.ui.TypefaceTextView youRan = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.youRan);

        int secondsRunTime = (int) (runTime / 1000);
        int minutesRunTime = secondsRunTime / 60;
        secondsRunTime = secondsRunTime % 60;
        youRan.setText("You ran " + distance +" "+ distanceUnit +"\nIn "+(String.format("%d:%02d", minutesRunTime, secondsRunTime))+" Minutes!");



        final com.mobsandgeeks.ui.TypefaceTextView finalPace = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.finalPace);
        double finalPaceNumber = runTime / distance;
        int seconds = (int) (finalPaceNumber / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        finalPace.setText("At a " + (String.format("%d:%02d", minutes, seconds))+" Pace!");

        final com.mobsandgeeks.ui.TypefaceTextView milesToGo = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.milesToGo);




    }
}
