package com.caseynguyen.p02b;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    };



    public void toScreen2FromZIP(View view) {

        Intent toScreen2Intent = new Intent(this, Screen2Activity.class);
        final int result = 1;

        EditText Zipview = (EditText) findViewById(R.id.enterZIPCode);
        String Ziptext = String.valueOf(Zipview.getText()) ;

        toScreen2Intent.putExtra("Screen1SelectType", "ZIP");
        toScreen2Intent.putExtra("Ziptext", Ziptext);
        startActivity(toScreen2Intent);
    }

    public void toScreen2FromCurrentLocation(View view) {

        Intent toScreen2Intent = new Intent(this, Screen2Activity.class);
        final int result = 1;

        toScreen2Intent.putExtra("Screen1SelectType", "CurrentLcation");
        startActivity(toScreen2Intent);

    }


}








