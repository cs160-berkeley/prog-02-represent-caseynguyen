package com.cs160.joleary.catnip;


import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String FRED_FEED = "/Fred";
    private static final String LEXY_FEED = "/Lexy";
    public String TAG = "CS160Wear";
    private static final String path2wear = "/WADE1";



    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)
        final String Path1 = messageEvent.getPath() ;
        Log.d(TAG, "in WatchListenerService, got: " + Path1 );

        if( Path1.equalsIgnoreCase( path2wear ) || Path1.equalsIgnoreCase( FRED_FEED ) ||
                Path1.equalsIgnoreCase( LEXY_FEED ) )

        {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            //
            Log.d(TAG, "in WatchListenerService, got valueof: " + value);

            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.putExtra("MSG_FROM_MOBILE", value);
            Log.i(TAG, "Call Main Activity function with MSG_FROM_MOBILE:" + value);
            MainActivity.sharedlist2w.add(value) ;
            startActivity(intent);
//            MainActivity.onMobileSend();

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}