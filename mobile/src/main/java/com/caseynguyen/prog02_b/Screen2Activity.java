package com.caseynguyen.prog02_b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Screen2Activity extends Activity {
    String TAG = "pr02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2_layout);

        Intent IntentFrom_Screen1 = getIntent();
        String SelectType = IntentFrom_Screen1.getExtras().getString("Screen1SelectType");
        Log.i(TAG, "Screen1SelectType " + SelectType);
        if (SelectType.equals("ZIP")) {
            String Ziptext = IntentFrom_Screen1.getExtras().getString("Ziptext");
            Log.i(TAG, "Ziptext " + Ziptext);
        }
    }


    public void toScreen3_button(View view) {

        Intent toScreen3Intent = new Intent(this, Screen3Activity.class);
        final int result = 1;

        toScreen3Intent.putExtra("Screen1SelectType", "CurrentLcation");
        Log.i(TAG, "toScreen3Intent");

        startActivity(toScreen3Intent);

    }

    public void Screen2Close(View view) {
        Intent IntentBackToScreen1 = new Intent();
        Log.i(TAG, "IntentBackToScreen1");


                setResult(RESULT_OK, IntentBackToScreen1);
        finish();
    }

}
