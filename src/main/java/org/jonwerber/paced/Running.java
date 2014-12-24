package org.jonwerber.paced;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Timer;

/**
 * Created by jonathanwerber on 12/15/14.
 */

public class Running extends ActionBarActivity implements LocationListener {
    private LocationManager locationManagerOne;

    private double lnOne;
    private double lOne;
    private double lTwo;
    private double lnTwo;
    private double distance;


    private long startTime;
    private long endTime;
    private long runTime;
  private long allTime;
            //for timer
       private long startTimeTimer = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
//end timer

    private Timer timer;
    private long timeOne;
    private long timeTwo;
    private long timeTraveled;
    private double distanceUpdate;
    private double distanceTraveled;
    private String distanceUnit;
    private Handler customHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);

        Bundle b = getIntent().getExtras();
        distanceUnit = b.getString("distanceUnit");
        distance = b.getDouble("distance");
        double pace = b.getDouble("pace");
        final com.mobsandgeeks.ui.TypefaceTextView milesToGo = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.milesToGo);
        milesToGo.setText(String.format(distance + "\n"+ distanceUnit+" to go"));




        startTime = System.currentTimeMillis();

        startTimeTimer = SystemClock.uptimeMillis();

        customHandler.postDelayed(updateTimerThread, 0);




        int seconds = (int) (pace / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        final com.mobsandgeeks.ui.TypefaceTextView paceSoFar = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.paceSoFar);
        //   paceSoFar.setText(String.format("%d:%02d", minutes, seconds));

        paceSoFar.setText(String.format("%d:%02d", minutes, seconds));



        /********** get Gps location service LocationManager object ***********/
        locationManagerOne = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



                /* CAL METHOD requestLocationUpdates */

        // Parameters :
        //   First(provider)    :  the name of the provider with which to register
        //   Second(minTime)    :  the minimum time interval for notifications,
        //                         in milliseconds. This field is only used as a hint
        //                         to conserve power, and actual time between location
        //                         updates may be greater or lesser than this value.
        //   Third(minDistance) :  the minimum distance interval for notifications, in meters
        //   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location)
        //                         method will be called for each location update


     //   locationManagerOne.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 10, this);

        /********* After registration onLocationChanged method  ********/
        /********* called periodically after each 3 sec ***********/
        if (locationManagerOne.isProviderEnabled(LocationManager.GPS_PROVIDER)  == true ){
            locationManagerOne.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 10, this);


        }
       else if((locationManagerOne.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) && (locationManagerOne.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true)){
            locationManagerOne.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 10, this);

        }
        else{
paceSoFar.setText("Your Location Doesn't Seem To Be Working.");
        }

    }

    /**
     * ********** Called after each 3 sec *********
     */

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTimeTimer;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            final com.mobsandgeeks.ui.TypefaceTextView timerValue = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.timer);

            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    @Override
    public void onLocationChanged(Location location) {


        lOne = location.getLatitude();
        lnOne = location.getLongitude();


        timeOne = System.currentTimeMillis();

        while (lOne != lTwo) {
            if (lTwo == 0) {
                lTwo = lOne;
                lnTwo = lnOne;
                timeTwo = timeOne;
            }
            if (distanceUnit == "Miles") {
                distanceTraveled = (((Math.sqrt(((lOne - lTwo) * (lOne - lTwo)) + ((lnOne - lnTwo) * (lnOne - lnTwo)))) * 109.5) * 0.621371);
            } else {
                distanceTraveled = ((Math.sqrt(((lOne - lTwo) * (lOne - lTwo)) + ((lnOne - lnTwo) * (lnOne - lnTwo)))) * 109.5);
            }

            timeTraveled += (timeOne - timeTwo);
            distanceUpdate += distanceTraveled;

            double updatePace = timeTraveled / distanceUpdate;

            int seconds = (int) (updatePace / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            final com.mobsandgeeks.ui.TypefaceTextView milesToGo = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.milesToGo);


            //TODO if distance % 2 speak. (distance "to go"). !!!
            milesToGo.setText((round((distance - distanceUpdate), 3, BigDecimal.ROUND_HALF_DOWN)) + "\n" + distanceUnit + " to go");

            final com.mobsandgeeks.ui.TypefaceTextView paceSoFar = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.paceSoFar);
            paceSoFar.setText(String.format("%d:%02d", minutes, seconds));

            lTwo = lOne;
            lnTwo = lnOne;
            timeTwo = timeOne;
           Double doIend = distance - distanceUpdate;
            if ((distance - distanceUpdate) <= 0) {
         congrats();
                this.finish();
                locationManagerOne.removeUpdates(this);
            }
        }
    }
    public void congrats() {
        endTime = System.currentTimeMillis();
        runTime = (endTime - startTime);

        Intent congratsIntent = new Intent(this, Congrats.class);
        Bundle c = new Bundle();
        c.putString("distanceUnit", distanceUnit);
        c.putLong("runTime", runTime);
        c.putLong("endTime", endTime);
        c.putDouble("distance", distance);
        c.putString("distanceUnit", distanceUnit);

        congratsIntent.putExtras(c);
        startActivity(congratsIntent);
        finish();

        //TODO SHOW FINAL SCREEN WITH PACE ETC


    }


    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/
        final com.mobsandgeeks.ui.TypefaceTextView milesToGo = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.milesToGo);
        milesToGo.setText("Turn on GPS \nBefore you run");

        //  Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
//TODO make field for messages, ie. gps on off et
        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public static double round(double unrounded, int precision, int roundingMode) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();

    }

}
