package com.cs160.joleary.catnip;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        buildGoogleApiClient();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();

        GlobalVarApp.getInstance().shareA = 1;
        GlobalVarApp.getInstance().msharedlist.clear();
    }

    public void toScreen2FromZIP(View view) {
        Intent toScreen2Intent = new Intent(this, Screen2Activity.class);
        final int result = 1;

        EditText Zipview = (EditText) findViewById(R.id.enterZIPCode);
        String Ziptext = String.valueOf(Zipview.getText());

        toScreen2Intent.putExtra("Screen1SelectType", "ZIP");
        toScreen2Intent.putExtra("Ziptext", Ziptext);
        toScreen2Intent.putExtra("latitude", "");
        toScreen2Intent.putExtra("latitude", "");

        startActivity(toScreen2Intent);
    }

    public void toScreen2FromCurrentLocation(View view) {
        Intent toScreen2Intent = new Intent(this, Screen2Activity.class);
        final int result = 1;

        toScreen2Intent.putExtra("Screen1SelectType", "LOC");
        toScreen2Intent.putExtra("Ziptext", "");
//        GlobalVarApp.mLatitude =  37.7749295; // san fran
//        GlobalVarApp.mLongitude =  -122.4194155;
        //GlobalVarApp.mLatitude =  37.0; // san fran
        //GlobalVarApp.mLongitude =  -122.0;

        startActivity(toScreen2Intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVarApp.getInstance().shareA = 1;
    }

    public static void onMobileSend(String s) {
        int size = GlobalVarApp.getInstance().msharedlist.size();
        Log.i("Screen2:", "WADE, sharedsize:" + size + s);
        if (size > 0) {
            Log.i("Screen2:", "get(0):" + GlobalVarApp.getInstance().msharedlist.get(size - 1));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle arg0) {
        Toast.makeText(this, "Google connect...", Toast.LENGTH_SHORT).show();
        Log.i("WADE:", "WADE, Google");
        // Default to SF
        GlobalVarApp.mLatitude =  37.7749295 ;
        GlobalVarApp.mLongitude =  -122.4194155 ;


        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            System.out.println("====> Latitude: " + String.valueOf(mLastLocation.getLatitude()));
            System.out.println("====> Longitude: " + String.valueOf(mLastLocation.getLongitude()));


//            etLat.setText(String.valueOf(mLastLocation.getLatitude()));
//            etLong.setText(String.valueOf(mLastLocation.getLongitude()));
            GlobalVarApp.mLatitude = (mLastLocation.getLatitude()); // 37.7749295
            GlobalVarApp.mLongitude = (mLastLocation.getLongitude()); // -122.4194155

            //GlobalVarApp.mLatitude =  37.7749295; // san fran
            //GlobalVarApp.mLongitude =  -122.4194155;

            Log.d("WADEm" , "Lat: " +String.valueOf(GlobalVarApp.mLatitude) +
            "Lon: " +String.valueOf(GlobalVarApp.mLongitude) );
        } else {
            Log.d("WADEm" , "No mLastLocation");
            GlobalVarApp.mLatitude =  37.7749295 ;
            GlobalVarApp.mLongitude =  -122.4194155 ;
            Toast.makeText(this, "No mLastLocation -- Default to SF...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }
}








