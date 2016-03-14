package com.cs160.joleary.catnip;


import android.app.Service;
import android.os.Bundle;
import android.os.IBinder;
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
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity {
    String TAG = "WADEw";
    public static List<String> wsharedlist = new ArrayList<String>();


    List<wearCongress> localmyCongress = wearGlobalVarApp.getInstance().global_myCongress;
    private List<String> mywearCounty = wearGlobalVarApp.getInstance().global_county;

    private List<wearCongress> mywearCongress = new ArrayList<wearCongress>();
    public static Activity tempA;
    public static Intent tempI ;
    public static String tempString ;
    public static Bundle tempB ;
    public static List<String> sharedlist2w = new ArrayList<String>();



    private TextView mTextView;
    private Button mFeedBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "wear>>> casey STARTS here!");


        super.onCreate(savedInstanceState);
        wsharedlist.clear();
        sharedlist2w.clear();

        setContentView(R.layout.activity_main);


//        mFeedBtn = (Button) findViewById(R.id.feed_btn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if (extras != null) {
            String stringFromMobile = extras.getString("MSG_FROM_MOBILE");
            //mFeedBtn.setText("Feed " + stringFromMobile);
            Log.i(TAG, "wear:Main: MSG_FROM_MOBILE:" + stringFromMobile);
            if (stringFromMobile.equals("ZIP")) {
                temppopulateCongressList(6);

                populatewearCongressList(stringFromMobile);

                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);

                stopService(sendIntent);
                Log.i(TAG, "stopService(sendIntent)");

                int ww = 3;
                while (ww > 0) {
                    ww--;
                    Log.i(TAG, "stopService(sendIntent)");
                }

                toScreen2forTesting();

            }
            else {
                temppopulateCongressList(3);
                populatewearCongressList(stringFromMobile);

                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);

                stopService(sendIntent);
                Log.i(TAG, "stopService(sendIntent)");

                int ww = 3;
                while (ww > 0) {
                    ww--;
                    Log.i(TAG, "stopService(sendIntent)");
                }

                toScreen2forTesting();

            }


        }





//
//
//        mFeedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//
//                stopService(sendIntent);
//                Log.i(TAG, "stopService(sendIntent)");
//
//                int ww = 3;
//                while (ww > 0) {
//                    ww--;
//                    Log.i(TAG, "stopService(sendIntent)");
//                }
//
//                toScreen2forTesting();
//
//            }
//        });
//


    }

    private int populatewearCongressList(String s) {
        // list format:
        // NEW or ADD, or ATLAST <Name+Party>
        // separated by \ char

        String[] msgItems= s.split("~");

        int i = msgItems.length ;
        if( i < 1 ) return -1;

        // format: ZIPLOC
        // ZIPLOC + #person (name+party) + CountrName+Obama%+Romney%
        if( msgItems[0].equals("ZIP") || msgItems[0].equals("LOC")){
            Log.i(TAG, "wearCongressadd input:"+s ) ;
            // next is the # of person
            //
            int person = Integer.parseInt(msgItems[1]);
            Log.i(TAG, "wearCongressadd input:"+ s + "<person>:" + person ) ;

            localmyCongress.clear();
            mywearCongress.clear();
            int j = 2 ;
            while( person > 0) {
                localmyCongress.add(new wearCongress(msgItems[j], msgItems[j + 1], 0)) ;
                Log.i(TAG, "wearCongressadd:"+(msgItems[j] + msgItems[j + 1] )) ;

                j=j+2;
                person--;
            }
            mywearCounty.clear();
            mywearCounty.add(msgItems[j++]) ;
            mywearCounty.add(msgItems[j++]) ;
            mywearCounty.add(msgItems[j]) ;

            Log.i(TAG, "wearCongressadd:" +
                    mywearCounty.add(msgItems[0]) +
                    mywearCounty.add(msgItems[1]) +
                    mywearCounty.add(msgItems[2]) + "size:sb3" + mywearCounty.size()) ;

            return 0;
        }
        // we will check for election result code here
        // data format is:
        // 012~CountyName~Obama~51%~Romney~49%
        // cccc
        return 0;
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

        List<Node> nodes = new ArrayList<>();
        String TAG1 = "WADEWatchToPhoneService";

        Intent toScreen2Intent = new Intent(this, wearScreen2Ativity.class);
        final int result = 1;


        toScreen2Intent.putExtra("Screen1SelectType", "Testing");
        startActivity(toScreen2Intent);

    }

//    public static void onMobileSend() {
//
//        Log.i("WADEwMain:", "WADE, Mobile sends you data");
//        if( sharedlist2w.size()>0 )
//        Log.i("WADEm", (String) (sharedlist2w.get(0)) );
//        sharedlist2w.clear();
//
//    }
}

