package com.caseynguyen.p02b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Screen3Activity extends AppCompatActivity {

    private List<Congress> myCongress = new ArrayList<>();
    public String TAG = "CS160";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen3_layout);
        Intent IntentFrom_Screen2 = getIntent();

        Log.i(TAG, "Enter Screen 3 ");

        int position = IntentFrom_Screen2.getExtras().getInt("PositionSelected");
        Log.i(TAG, "PositionSelected " + position);

        //public  myCongress = new ArrayList<>() ;

        if (position >= myCongress.size()) {
            Log.i(TAG, "Error in position " + position);
        }

        popupateCongressList();


        Congress currentCongress = myCongress.get(position);
        Log.i(TAG, "Name: " + currentCongress.getName());
        Log.i(TAG, "Web: " + currentCongress.getWebsite());

        ImageView imageView = (ImageView) findViewById(R.id.detail_icon);
        imageView.setImageResource(currentCongress.getPic());


        TextView makeName = (TextView) findViewById(R.id.detail_Name);
        makeName.setText(currentCongress.getName());

        TextView TypeText = (TextView) findViewById(R.id.detail_CongressType);
        TypeText.setText("" + currentCongress.getCongressType());

        TextView PartyText = (TextView) findViewById(R.id.detail_Party);
        PartyText.setText(currentCongress.getParty());

        TextView emailText = (TextView) findViewById(R.id.detail_email);
        emailText.setText(currentCongress.getEmail());

        TextView websiteText = (TextView) findViewById(R.id.detail_website);
        websiteText.setText(currentCongress.getWebsite());

        TextView tweetText = (TextView) findViewById(R.id.detail_tweet);
        tweetText.setText(currentCongress.getLastTweet());

        TextView EndofTermText = (TextView) findViewById(R.id.detail_EndofTerm);
        EndofTermText.setText(currentCongress.getEndOfTerm());

        TextView CommitteeText = (TextView) findViewById(R.id.detail_Committee);
        CommitteeText.setText(currentCongress.getCommittee());

        TextView BillSponsoredText = (TextView) findViewById(R.id.detail_BillSponsored);
        BillSponsoredText.setText(currentCongress.getBillSponsored());


        //String message = "You clicked position " + position ;

        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();


    }

    public void Screen3Close(View view) {
        Intent IntentBackToScreen2 = new Intent();
        Log.i(TAG, "IntentBackToScreen2");

        setResult(RESULT_OK, IntentBackToScreen2);
        finish();
    }


    private void popupateCongressList() {

        myCongress.add(
                new Congress("Diane Feinstein", R.drawable.feinstein, "Senator", "Democrat",
                        "senator@feinstein.senate.gov", "http://www.feinstein.senate.gov", "(3/3) Apple would not violate its commitment to privacy, rather they would be helping law enforcement do their jobs to pursue justice.",
                        "Vice Chairman, Senate Select Committee on Intelligence", "02/28/2018",
                        "S. 2552: Interstate Threats Clarification Act of 2016"));

        myCongress.add(new Congress("Barbara Boxer", R.drawable.boxer, "Senator", "Democrat",
                "senator@boxer.senate.gov", "http://boxer.senate.gov/",
                "In the 70+ year history of the @UN, there has never been a female Secretary-General. Isn’t it about time?",
                "Select Committee on Ethics, Ranking Member", "January 3, 2017",
                "S. 2552: Interstate Threats Clarification Act of 2016"));

        myCongress.add(new Congress("Kevin McCarthy", R.drawable.mccarthy, "Representative", "Republican",
                "kevinmccarthy@house.gov", "https://kevinmccarthy.house.gov/",
                "Gen. Marshall’s leadership changed the world. H.Con.Res. 123 recognizes his place in history.",
                "Subcommittee on Financial Institutions and Consumer Credit", "1/3/2017",
                "H.R.1735 - National Defense Authorization Act for Fiscal Year 2016"));


        myCongress.add(new Congress("Bug Bunny", R.drawable.bunny, "Senator", "Democrat",
                "senator@bunny.senate.gov", "wwww.bunny.senate.gov", "Last Tweet is go Dubs .",
                "Vice Chairman, Disney Land and Wild Hunter", "02/28/2018",
                "S. 2552: Save your water."));

        myCongress.add(new Congress("Daffy Duck", R.drawable.daffy, "Senator", "Democrat",
                "senator@duck.senate.gov", "www.duck.senate.gov", "Last Tweet  is go Dubs.",
                "Chairman, Disney Land and Wild Hunter", "02/28/2018",
                "S. 2552: S. 2552: Save your water."));

        myCongress.add(new Congress("Elmer Fudd", R.drawable.elmer, "Senator", "Republican",
                "senator@mrfudd.senate.gov", "www.mrfudd.senate.gov", "Last Tweet is go Dubs.",
                "Vice President, Rabbit Hunter Committee", "02/28/2018",
                "S. 2552 -- Ho ho ho.. Save yourself."));
    }


}
