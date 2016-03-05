package com.caseynguyen.p02b;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
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

public class MainActivity extends Activity {
    String TAG = "CS160Wear";
    private List<wearCongress> mywearCongress = new ArrayList<>();


    private TextView mTextView;
    private Button mFeedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "wear>>> casey STARTS here!");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //mFeedBtn = (Button) findViewById(R.id.feed_btn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {
            String stringFromMobile = extras.getString("MSG_FROM_MOBILE");
            //mFeedBtn.setText("Feed " + stringFromMobile);
            Log.i(TAG, "wear:Main: MSG_FROM_MOBILE:" + stringFromMobile);
            if (stringFromMobile.equals("ZIP"))
                temppopulateCongressList(6);
            else
                temppopulateCongressList(3);

            Log.i(TAG, "Got last Item: time to new Activity to display." + mywearCongress.size());
            // Jumo to 2nd screen and navigate
            //Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
            //startService(sendIntent);

            toScreen2forTesting();
        }





/*
        mFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                //startService(sendIntent);
                toScreen2forTesting();

            }
        });

 */


        // Jumo to 2nd screen and navigate
        // toScreen2forTesting();





    }

    private int populatewearCongressList(String s) {

        // list format:
        // NEW or ADD, or ATLAST <Name+Party>
        // separated by \ char

        String[] msgItems= s.split("\\\\");

        Log.i(TAG, "  msgItems[0]:" + msgItems[0] + "1:" + msgItems[1] + "2:" + msgItems[2]);


        if( msgItems[0].equals("ZIP") || msgItems[0].equals("ADD")){
            // put 0 for image for now...cccc
            mywearCongress.add( new wearCongress( msgItems[1], msgItems[2], 0 ) ) ;
            return 0;
        }
        if( msgItems[0].equals( "ATLAST") ) {
            mywearCongress.add( new wearCongress( msgItems[1], msgItems[2], 0 ) ) ;
            return 1 ;
        }
        else {
            Log.i(TAG, "wear:Main: UNrecognized command:" + msgItems[0]);
            return -1;

        }


    }

    //

    private void temppopulateCongressList(int total) {

        mywearCongress.add(
                new wearCongress("Diane Feinstein", "Democrat", R.drawable.democrat_frame));

        mywearCongress.add(
                new wearCongress("Barbara Boxer", "Democrat", R.drawable.democrat_frame));

        mywearCongress.add(
                new wearCongress("Kevin McCarthy", "Republican", R.drawable.republican_frame));

        if( total == 3 ) {

            mywearCongress.add(
                    new wearCongress("Bug Bunny", "Democrat", R.drawable.democrat_frame));

            mywearCongress.add(
                    new wearCongress("Daffy Duck", "Democrat", R.drawable.democrat_frame));

            mywearCongress.add(
                    new wearCongress("Elmer Fudd", "Republican", R.drawable.republican_frame));
        }

        Log.i(TAG, "Main: wearCongress.size():" + mywearCongress.size());

    }

    public void toScreen2forTesting() {

        Intent toScreen2Intent = new Intent(this, wearScreen2Ativity.class);
        final int result = 1;

        toScreen2Intent.putExtra("Screen1SelectType", "Testing");
        startActivity(toScreen2Intent);

    }
}

