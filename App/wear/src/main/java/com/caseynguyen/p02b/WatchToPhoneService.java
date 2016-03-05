package com.caseynguyen.p02b;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
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

public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();
    static String TAG ="CS160wearServices:";


    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        Log.d(TAG, "onCreate:" );

        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();
        //and actually connect it
        //mWatchApiClient.connect();
        if( ! mWatchApiClient.isConnected()) mWatchApiClient.connect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }


    @Override
    public IBinder onBind(Intent intent) {

        Bundle extras = intent.getExtras() ;
        final String s1 = extras.getString("NametoPhone");
        Log.d(TAG, "onBInd:=" + s1);
        return null;
    }

    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        final Service _this = this;
        Log.d(TAG, "in onConnected");
        final String s1 = bundle.getString("NametoPhone");
        Log.d(TAG, "onconnect:value=" + s1);


        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        Log.d("CS160watch2phone:", "found nodes");
                        //when we find a connected node, we populate the list declared above
                        //finally, we can send a message
                        sendMessage("/send_toast", "Good job!");
                        _this.stopSelf();
                        Log.d("T", "sent");
                    }
                });


    }

    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

    private void sendMessage(final String path, final String text ) {
        for (Node node : nodes) {
            Wearable.MessageApi.sendMessage(
                    mWatchApiClient, node.getId(), path, text.getBytes());
        }
    }


}
