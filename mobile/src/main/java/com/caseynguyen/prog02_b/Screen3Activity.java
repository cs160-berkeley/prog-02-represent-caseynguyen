package com.caseynguyen.prog02_b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class Screen3Activity extends AppCompatActivity {

    String TAG = "pr02";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3_layout);
        Intent IntentFrom_Screen1 = getIntent();
        Log.i(TAG, "Enter Screen 3 ");

    }
    public void Screen3Close(View view) {
        Intent IntentBackToScreen2 = new Intent();
        Log.i(TAG, "IntentBackToScreen2");

        setResult(RESULT_OK, IntentBackToScreen2);
        finish();
    }
}