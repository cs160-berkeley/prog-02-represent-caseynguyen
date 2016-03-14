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


public class wearScreen2Ativity extends Activity implements AccelerometerListener{

    String TAG = "WADEwS2";
    String SHAK2 = "SHAK";

    //private List<wearCongress> mywearCongress = new ArrayList<>();
    List<wearCongress> localmyCongress = wearGlobalVarApp.getInstance().global_myCongress;
    private List<String> mywearCounty = wearGlobalVarApp.getInstance().global_county;


    int count = 0 ;
    private GestureDetector mGestureDetector;
    private GestureDetectorCompat gDetect;



    private TextView mTextView;
    private Button mFeedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "scr2 starts here!");



        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_screen2_layout);

        // temp loading list for testing
        scr2_temppopulateCongressList();

        DisplayUpdatePerson( 0);

        //
        Log.i(TAG, "display pos 0: " + localmyCongress.get(0).getName());

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
            Log.i(TAG, "CATTonLongPress" + ev.toString());

            TextView iView = (TextView) findViewById(R.id.item_Name) ;
            String iName = iView.getText().toString() ;
            // now we want to send iName to phone for detailed view
            Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
            Log.i(TAG, "Jump to WatchToPhoneService NametoPhone" );

            sendIntent.putExtra( "NametoPhone", iName ) ;
            startService(sendIntent);




            /*
                    <TextView
            android:id="@+id/item_Name"
            */

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
                        OnClickNextPerson1();

                    } else {
                        Log.i(TAG, "CATT Swipe RIGHT  " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        OnClickPrevPerson1();
                    }
                }


            } else {
                // up down  direction
                if( y >  10 ) {
                    if (distanceY < 0) {
                        Log.i(TAG, "CATT Scroll DOWN  " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        // OnClickPrevPerson1();
                        // Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        Intent toScreen3Intent = new Intent(wearScreen2Ativity.this, wearScreen3Ativity.class);
                        // toScreen3Intent.putExtra("PositionSelected", 1);
                        Log.i(TAG, "toScreen3Intent");

                        startActivity(toScreen3Intent);


                    } else {
                        Log.i(TAG, "CATT Scroll UP    " + String.valueOf(distanceX) + " Y= " + String.valueOf(distanceY));
                        //OnClickNextPerson1();
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

    public void OnClickPrevPerson(View view) {

        // We want to display previous person fom current display
        TextView nameView = (TextView) findViewById(R.id.item_Name);
        String curName = nameView.getText().toString();
        Log.i(TAG, "Click Prev Person of" + curName);


        // find where he is in the array
        int Pos1 = ArraySearch( curName) ;
        if( Pos1 < 0) {
            // error
            Pos1=0;
        } else Pos1-- ;

        if (Pos1<0) Pos1= 0;
        DisplayUpdatePerson( Pos1) ;

    }


    public void OnClickPrevPerson1() {

        // We want to display previous person fom current display
        TextView nameView = (TextView) findViewById(R.id.item_Name);
        String curName = nameView.getText().toString();
        Log.i(TAG, "SWIPE Prev Person of" + curName);


        // find where he is in the array
        int Pos1 = ArraySearch( curName) ;
        if( Pos1 < 0) {
            // error
            Pos1=0;
        } else Pos1-- ;

        if (Pos1<0) Pos1= 0;
        DisplayUpdatePerson( Pos1) ;

    }

    public void OnClickNextPerson(View view) {

        // We want to display previous person fom current display
        TextView nameView = (TextView) findViewById(R.id.item_Name);
        String curName = nameView.getText().toString();
        Log.i(TAG, "Click Next Person of" + curName);

        // find where he is in the array
        int Pos1 = ArraySearch( curName) ;
        if( Pos1 < 0) {
            // error
            Pos1=0;
        } else Pos1++ ;

        if (Pos1 >=localmyCongress.size() ) Pos1= localmyCongress.size()-1;
        DisplayUpdatePerson(  Pos1 ) ;

    }

    public void OnClickNextPerson1( ) {

        // We want to display previous person fom current display
        TextView nameView = (TextView) findViewById(R.id.item_Name);
        String curName = nameView.getText().toString();
        Log.i(TAG, "SWIPE Next Person of" + curName);

        // find where he is in the array
        int Pos1 = ArraySearch( curName) ;
        if( Pos1 < 0) {
            // error
            Pos1=0;
        } else Pos1++ ;

        if (Pos1 >=localmyCongress.size() ) Pos1= localmyCongress.size()-1;
        DisplayUpdatePerson(  Pos1 ) ;

    }

    public void DisplayUpdatePerson( int pos1) {



        wearCongress person1 = localmyCongress.get(pos1);
        String party1 = person1.getParty();

        ImageView imageView = (ImageView) findViewById(R.id.item_icon);

        TextView partyView = (TextView) findViewById(R.id.item_Party);

        partyView.setText(person1.getParty());
        if(person1.getParty().equals("Democrat")) {
            imageView.setImageResource(R.drawable.democrat_frame);
        } else         if(person1.getParty().equals("Republican")) {
            imageView.setImageResource(R.drawable.republican_frame);

        } else
            imageView.setImageResource(R.drawable.start_frame);

        partyView.setText(person1.getParty());
        TextView nameView = (TextView) findViewById(R.id.item_Name);
        nameView.setText(person1.getName());


        Log.i(TAG, "display pos: " + pos1 + person1.getName());

        Log.i(TAG, "startService(sendIntent)");

        // send it to the phone ... diagnostic

        MainActivity.wsharedlist.clear();
        MainActivity.wsharedlist.add(person1.getName()) ;
        String msg1 = MainActivity.wsharedlist.get(0);
        Log.d(TAG, msg1 + "sent2phone");
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
        startService(sendIntent);

    }


    public int  ArraySearch( String s) {

        int i = 0;
        while (i < localmyCongress.size() && !(localmyCongress.get(i).getName().equals(s)))
            i++;

        if (i >= localmyCongress.size()) {
            return -1;

        } else {
            return i;
        }
    }
    // temporary, we hardcode the array and move on

    private void scr2_temppopulateCongressList() {
//
//        mywearCongress.add(
//                new wearCongress("Diane Feinstein", "Democrat", R.drawable.democrat_frame));
//
//        mywearCongress.add(
//                new wearCongress("Barbara Boxer", "Democrat", R.drawable.democrat_frame));
//
//        mywearCongress.add(
//                new wearCongress("Kevin McCarthy", "Republican", R.drawable.republican_frame));
//
//        mywearCongress.add(
//                new wearCongress("Bug Bunny", "Democrat", R.drawable.democrat_frame));
//
//        mywearCongress.add(
//                new wearCongress("Daffy Duck",  "Democrat", R.drawable.democrat_frame));
//
//        mywearCongress.add(
//                new wearCongress("Elmer Fudd", "Republican", R.drawable.republican_frame));
//
//        Log.i(TAG, "scr2: wearCongress.size():" + mywearCongress.size());
//

    }


    public void onAccelerationChanged(float x, float y, float z) {
        // TODO Auto-generated method stub

    }

    public void onShake(float force) {

        // Do your stuff here

        // Called when Motion Detected
        //Toast.makeText(getBaseContext(), "Motion detected",
        //        Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Shake: onShake"   );


        MainActivity.wsharedlist.clear();
        MainActivity.wsharedlist.add("99/SHAKE") ;
        String msg1 = MainActivity.wsharedlist.get(0);
        Log.d(TAG, msg1 + "sentSHAKE");
        Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
        startService(sendIntent);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
        //        Toast.LENGTH_SHORT).show();

        //Check device supported Accelerometer senssor or not
        Log.i(SHAK2, "Shake: onResume");

        if (AccelerometerManager.isSupported(this)) {

            //Start Accelerometer Listening
            AccelerometerManager.startListening(this);
            Log.i(SHAK2, "Shake: startListening");

        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            //Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
            //       Toast.LENGTH_SHORT).show();
            Log.i(SHAK2, "Shake: onStop");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(SHAK2, "onDestroy");

        //Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            //Start Accelerometer Listening
            AccelerometerManager.stopListening();

            //    Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
            //           Toast.LENGTH_SHORT).show();
            Log.i(SHAK2, "Shake: onDestroy");

        }

    }

}
