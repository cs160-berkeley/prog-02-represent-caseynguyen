package com.cs160.joleary.catnip;


import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.support.v4.view.GestureDetectorCompat;
import android.widget.Toast;


public class wearScreen3Ativity extends Activity {
    String TAG = "CS160Wear sc3:";

    int count = 0 ;
    private GestureDetector mGestureDetector;
    private GestureDetectorCompat gDetect;

    private List<String> mywearCounty = wearGlobalVarApp.getInstance().global_county;



    private TextView mTextView;
    private Button mFeedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_screen3_layout);



        // display the first item in list
        // lets find out if person is dem/rep


        TextView p1View = (TextView) findViewById(R.id.sc3_location);
        p1View.setText(mywearCounty.get(0));

        p1View = (TextView) findViewById(R.id.P1_percent);
        p1View.setText("Obama" + " "+ mywearCounty.get(1)+"%");

        TextView p2View = (TextView) findViewById(R.id.P2_percent);
        p2View.setText("Romney" + " "+ mywearCounty.get(2)+"%");

        //
        Log.i(TAG, "Romney Obama: " );

        //
        gDetect = new GestureDetectorCompat(this, new GestureListener());


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent");
        this.gDetect.onTouchEvent(ev);
        return  super.dispatchTouchEvent(ev);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent ev) {
            Log.i(TAG, "CATTonSingleTapUp" + ev.toString());
            return true;
        }
        @Override
        public void onShowPress(MotionEvent ev) {
            Log.i(TAG, "CATTonShowPress" + ev.toString());
        }
        @Override
        public void onLongPress(MotionEvent ev) {
            Log.i(TAG, "CATTonLongPress: send data back to the phone" + ev.toString());
            //


        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float x, y ;

            Log.i(TAG,"CATTonScroll E1:"+ e1.toString());
            Log.i(TAG, "CATTonScroll E2" + e2.toString());
            Log.i(TAG, "CATT scroll distance X=" + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));

            if( distanceX < 0 ) x = -distanceX ; else x = distanceX ;
            if( distanceY < 0 ) y = -distanceY ; else y = distanceY;



            if( x > y ) {
                // left right direction
                if( x > 10) {
                    if (distanceX > 0) {
                        Log.i(TAG, "CATT Swipe LEFT  " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        //OnClickNextPerson1();

                    } else {
                        Log.i(TAG, "CATT Swipe RIGHT  " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        //OnClickPrevPerson1();
                    }
                }


            } else {
                // up down  direction
                if( y >  10 ) {
                    if (distanceY < 0) {
                        Log.i(TAG, "CATT Scroll DOWN  " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        // OnClickPrevPerson1();


                    } else {
                        Log.i(TAG, "CATT Scroll UP    " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        // add return code here
                        // OnClickNextPerson1();
                        Intent IntentBackToScreen2 = new Intent();
                        Log.i(TAG, "IntentBackToScreen2");

                        setResult(RESULT_OK, IntentBackToScreen2);
                        finish();
                    }
                }

            }

            return true;
        }
        @Override
        public boolean onDown(MotionEvent ev) {
            Log.i(TAG,"CATTonDownd"+ev.toString());
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "CATTd" + e1.toString());
            Log.i(TAG,"CATTe2"+e2.toString());
            return true;
        }
    }

}
