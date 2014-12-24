package org.jonwerber.paced;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {


    public final static String distanceSend = "com.mycompany.myfirstapp.distanceDouble";

    double distanceDouble;
    long milliPace = 420000;
    private String distanceUnit = "Miles";

    public void plusTime() {
        milliPace += 30000;

        final com.mobsandgeeks.ui.TypefaceTextView pace = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.pace);
        int seconds = (int) (milliPace / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        pace.setText(String.format("%d:%02d", minutes, seconds));

    }


    public void minusTime() {
        if (distanceUnit == "Miles") {
            if (milliPace > 210000) {
                milliPace -= 30000;
            } else {
            }
        } else if (distanceUnit == "Km") {
            if (milliPace > 120000) {
                milliPace -= 30000;
            } else {
            }
        }
        final com.mobsandgeeks.ui.TypefaceTextView pace = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.pace);
        int seconds = (int) (milliPace / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        pace.setText(String.format("%d:%02d", minutes, seconds));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final com.mobsandgeeks.ui.TypefaceTextView unitOne = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.unitOne);
        final com.mobsandgeeks.ui.TypefaceTextView unitTwo = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.unitTwo);


        // MAKE KM & MILE

        unitTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (distanceUnit == "Miles") {
                    distanceUnit = "Km";

                    unitOne.setTextColor(Color.parseColor("#FFFFFF"));
                    unitOne.setText(" Km");

                    unitTwo.setTextColor(Color.parseColor("#c6466c1b"));
                    unitTwo.setText(" Miles");
                } else if (distanceUnit == "Km") {
                    distanceUnit = "Miles";

                    unitOne.setTextColor(Color.parseColor("#FFFFFF"));
                    unitOne.setText(" Miles");

                    unitTwo.setTextColor(Color.parseColor("#c6466c1b"));
                    unitTwo.setText(" Km");
                }
            }

        });

        final com.mobsandgeeks.ui.TypefaceTextView more = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.more);
        final com.mobsandgeeks.ui.TypefaceTextView distance = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.distance);
        distanceDouble = 2.5;
        more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                distanceDouble += .5;
                distance.setText(distanceDouble + "");

            }
        });
        final com.mobsandgeeks.ui.TypefaceTextView less = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (distanceDouble > .5) {
                    distanceDouble -= .5;
                    distance.setText(distanceDouble + "");
                }
            }
        });


        final com.mobsandgeeks.ui.TypefaceTextView slower = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.slower);
        slower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                minusTime();
            }
        });
        final com.mobsandgeeks.ui.TypefaceTextView faster = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.faster);
        faster.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                plusTime();
            }
        });


        final com.mobsandgeeks.ui.TypefaceTextView run = (com.mobsandgeeks.ui.TypefaceTextView) findViewById(R.id.goText);
        run.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, Running.class);
                Bundle b = new Bundle();
                b.putString("distanceUnit", distanceUnit);
                b.putDouble("distance", distanceDouble);
                b.putDouble("pace", milliPace);

                myIntent.putExtras(b);

                startActivity(myIntent);
            }
        });
    }

    public void runNow(View view) {
        Intent myIntent = new Intent(this, Running.class);
        Bundle b = new Bundle();
        b.putString("distanceUnit", distanceUnit);
        b.putDouble("distance", distanceDouble);
        b.putDouble("pace", milliPace);

        myIntent.putExtras(b);

        startActivity(myIntent);


    }

}









