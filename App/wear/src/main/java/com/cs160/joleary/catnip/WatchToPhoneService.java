package com.cs160.joleary.catnip;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks,
        OnConnectionFailedListener {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();
    private static final String path2mobile = "/WADE2";
    private static String TAG = "WADEWatchToPhoneService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();
        //and actually connect it
        Log.i(TAG, "mWatchApiClient==null?:"+ (mWatchApiClient==null) );

        mWatchApiClient.connect();
        Log.i(TAG, "mWatchApiClient.isconnected?:" + (mWatchApiClient.isConnected()));
        //while( !(mWatchApiClient.isConnected() ) ) {

        //   Log.i(TAG, "mWatchApiClient.isconnected?:" + (mWatchApiClient.isConnected()));

        //};



    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        final Service _this = this ;
        Log.i(TAG, "in onconnected");
        final String msg1 = MainActivity.wsharedlist.get(0);

        if (MainActivity.wsharedlist.size()>0) {
            Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                    .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                        @Override
                        public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                            nodes = getConnectedNodesResult.getNodes();
                            Log.d(TAG, "found nodes");
                            //when we find a connected node, we populate the list declared above
                            //finally, we can send a message
                            sendMessage("/send_toast", msg1);
                            //sendMessage(path2mobile, msg1);

                            _this.stopSelf();


                        }

                    });
        }
        Log.d(TAG, msg1 + "was sent.");

    }

    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

    private void sendMessage(final String path, final String text ) {
        for (Node node : nodes) {
            Wearable.MessageApi.sendMessage(
                    mWatchApiClient, node.getId(), path, text.getBytes());
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "in onConnectionFailed:"+connectionResult);
    }
}


