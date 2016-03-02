package com.caseynguyen.prog02_b;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
