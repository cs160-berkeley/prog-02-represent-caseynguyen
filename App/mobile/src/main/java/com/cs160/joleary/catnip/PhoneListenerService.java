package com.cs160.joleary.catnip;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;


public class PhoneListenerService extends WearableListenerService{
    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_toast";
    private static final String path2mobile = "/WADE2";


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("WADE2", "in PhoneListenerService" + messageEvent.getPath() );
        if( messageEvent.getPath().equalsIgnoreCase(TOAST) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            //MainActivity.msharedlist.add(value);
            //MainActivity.onMobileSend();
            //Screen2Activity.onMobileSend();

            // Make a toast with the String
            //Context context = getApplicationContext();
            //int duration = Toast.LENGTH_SHORT;

            //Toast toast = Toast.makeText(context, value, duration);
            //toast.show();


            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions
            GlobalVarApp.getInstance().msharedlist.add(value);
            final int size = GlobalVarApp.getInstance().msharedlist.size();


            Log.i("WADE2r", "NEW PhoneListener:rcvd: "+ value );
            Log.i("WADE2r", "MainActivity.msharedlist.get():)" +
                    GlobalVarApp.getInstance().msharedlist.get(size - 1));

            //int ivalue = Integer.getInteger(value) ;
            //if(  true ) {
//                Intent toScreen3Intent = new Intent(this, MainActivity.class);
//                toScreen3Intent.putExtra("PositionSelected", "1" );
//
//                startActivity(toScreen3Intent);
            //}

            switch ( GlobalVarApp.getInstance().shareA) {

                case 1:
                    Log.i("WADE2r", "at main:"  );

                    MainActivity.onMobileSend(value);
                    break ;
                case 2:
                    Log.i("WADE2r", "at screen 2"  );


                    Screen2Activity.onMobileSend(value);
                    break;
                case 3:
                    Log.i("WADE2r", "at screen 3:"  );

                    Screen3Activity.onMobileSend(value);
                    break;
                    default:
                        Log.i("WADE2", "onMobileSend error:"  );


            }

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}