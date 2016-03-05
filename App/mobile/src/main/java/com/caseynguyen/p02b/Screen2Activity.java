package com.caseynguyen.p02b;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Screen2Activity extends Activity {
    private List<Congress> myCongress = new ArrayList<>();
    public String TAG = "CS160Mobile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2_layout);
        int Zip1 ;

        Intent IntentFrom_Screen1 = getIntent();
        String SelectType = IntentFrom_Screen1.getExtras().getString("Screen1SelectType");
        Log.i(TAG, "Screen1SelectType " + SelectType);
        if (SelectType.equals("ZIP")) {
            String Ziptext = IntentFrom_Screen1.getExtras().getString("Ziptext");
            Log.i(TAG, "Ziptext " + Ziptext);
            Zip1 = 1 ;
        } else Zip1 = 0 ;

        populateCongressList(Zip1);

        populateListView();

        registerClickCallback();

    };


    private void populateCongressList(int Zip1) {

        if( Zip1==1) sendtoWatch("NEW" + "\\Diane Feinstein" + "\\Democrat\\");


        myCongress.add(
                new Congress("Diane Feinstein", R.drawable.feinstein, "Senator", "Democrat",
                        "senator@feinstein.senate.gov", "http://www.feinstein.senate.gov", "(3/3) Apple would not violate its commitment to privacy, rather they would be helping law enforcement do their jobs to pursue justice.",
                        "Vice Chairman, Senate Select Committee on Intelligence", "02/28/2018",
                        "S. 2552: Interstate Threats Clarification Act of 2016"));
        //sendtoWatch("NEW" + "\\Diane Feinstein" + "\\Democrat\\");

        myCongress.add(new Congress("Barbara Boxer", R.drawable.boxer, "Senator", "Democrat",
                "senator@boxer.senate.gov", "http://boxer.senate.gov/",
                "In the 70+ year history of the @UN, there has never been a female Secretary-General. Isn’t it about time?",
                "Select Committee on Ethics, Ranking Member", "January 3, 2017",
                "S. 2552: Interstate Threats Clarification Act of 2016"));
        //sendtoWatch("ADD" + "\\Barbara Boxer" + "\\Democrat\\");

        myCongress.add(new Congress("Kevin McCarthy", R.drawable.mccarthy, "Representative", "Republican",
                "kevinmccarthy@house.gov", "https://kevinmccarthy.house.gov/",
                "Gen. Marshall’s leadership changed the world. H.Con.Res. 123 recognizes his place in history.",
                "Subcommittee on Financial Institutions and Consumer Credit", "1/3/2017",
                "H.R.1735 - National Defense Authorization Act for Fiscal Year 2016"));


        //if(Zip1==1) sendtoWatch("ADD" + "\\Kevin McCarthy" + "\\Democrat\\");
        //else sendtoWatch("ATLAST" + "\\Kevin McCarthy" + "\\Republican\\");

        if( Zip1 == 1) {
            myCongress.add(new Congress("Bug Bunny", R.drawable.bunny, "Senator", "Democrat",
                    "senator@bunny.senate.gov", "wwww.bunny.senate.gov", "Last Tweet is go Dubs .",
                    "Vice Chairman, Disney Land and Wild Hunter", "02/28/2018",
                    "S. 2552: Save your water."));
            //sendtoWatch("ADD" + "\\Bug Bunny" + "\\Democrat\\");

            myCongress.add(new Congress("Daffy Duck", R.drawable.daffy, "Senator", "Democrat",
                    "senator@duck.senate.gov", "www.duck.senate.gov", "Last Tweet  is go Dubs.",
                    "Chairman, Disney Land and Wild Hunter", "02/28/2018",
                    "S. 2552: S. 2552: Save your water."));
            //sendtoWatch("ADD" + "\\Daffy Duck" + "\\Democrat\\");
            myCongress.add(new Congress("Elmer Fudd", R.drawable.elmer, "Senator", "Republican",
                    "senator@mrfudd.senate.gov", "www.mrfudd.senate.gov", "Last Tweet is go Dubs.",
                    "Vice President, Rabbit Hunter Committee", "02/28/2018",
                    "S. 2552 -- Ho ho ho.. Save yourself."));
            //sendtoWatch("ATLAST" + "\\Elmer Fudd" + "\\Republican\\");
        }
        if( Zip1==1) sendtoWatch("ZIP" + "\\0" + "\\0\\");
        else sendtoWatch("LOC" + "\\0" + "\\0\\");

    }

    private void sendtoWatch(String s) {
        // stop here and work on the watch side
        // cccc path is private static final String path2wear = "WADE2WEAR";

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("REP!", s) ;
        startService(sendIntent) ;
    }

    private void populateListView() {
        ArrayAdapter<Congress> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.CongressListView);
        list.setAdapter(adapter);
    }


    private class MyListAdapter extends ArrayAdapter<Congress> {
        public MyListAdapter() {
            super(Screen2Activity.this, R.layout.item_view, myCongress);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            Congress currentCongress = myCongress.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentCongress.getPic());


            TextView makeText = (TextView) itemView.findViewById(R.id.item_Name);
            makeText.setText(currentCongress.getName());

            TextView yearText = (TextView) itemView.findViewById(R.id.item_CongressType);
            yearText.setText("" + currentCongress.getCongressType());

            TextView condionText = (TextView) itemView.findViewById(R.id.item_Party);
            condionText.setText(currentCongress.getParty());

            TextView emailText = (TextView) itemView.findViewById(R.id.item_Email);
            emailText.setText(currentCongress.getEmail());


            TextView WebsiteText = (TextView) itemView.findViewById(R.id.item_Website);
            WebsiteText.setText(currentCongress.getWebsite());

            TextView TweetText = (TextView) itemView.findViewById(R.id.item_Tweet);
            TweetText.setText(currentCongress.getLastTweet());



            return itemView;
        }
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.CongressListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                Congress clicked1 = myCongress.get(position);
                String message = "You clicked position " + position
                        + " and the name is " + clicked1.getName();
                // Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                Intent toScreen3Intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                toScreen3Intent.putExtra("PositionSelected", position);
                Log.i(TAG, "toScreen3Intent");

                startActivity(toScreen3Intent);


            }
        });
    }






}


