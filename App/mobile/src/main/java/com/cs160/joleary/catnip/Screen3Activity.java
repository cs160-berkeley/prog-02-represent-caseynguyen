package com.cs160.joleary.catnip;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Screen3Activity extends Activity {

    // private List<Congress> myCongress = new ArrayList<>();
    private List<Congress> myCongress = GlobalVarApp.getInstance().global_myCongress;
    public String TAG = "WADEm3";

//    localmyCongress.add(new Congress(name, picUrl, CongressType, Party, email, website, lastTweet, term_end, CommiteeObjs, billObjs));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        List<Congress> localmyCongress = GlobalVarApp.getInstance().global_myCongress;

        //localmyCongress.add(new Congress(name, picUrl, CongressType, Party, email, website, lastTweet, term_end, CommiteeObjs, billObjs));


        setContentView(R.layout.screen3_layout);
        Intent IntentFrom_Screen2 = getIntent();

        Log.i(TAG, "Enter Screen 3 ");

        int position = IntentFrom_Screen2.getExtras().getInt("PositionSelected");
        Log.i(TAG, "PositionSelected " + position);

        //public  myCongress = new ArrayList<>() ;
        //popupateCongressList();



        if (position >= myCongress.size()) {
            Log.i(TAG, "Error in position " + position);
        }



        Congress currentCongress = myCongress.get(position);
        Log.i(TAG, "Name: " + currentCongress.getName());
        Log.i(TAG, "Web: " + currentCongress.getWebsite());

        ImageView imageView = (ImageView) findViewById(R.id.detail_icon);
        String url = String.format("https://theunitedstates.io/images/congress/450x550/%s", currentCongress.getPic());
        Picasso
                .with(this)
                .load(url)
                .into(imageView);

        TextView makeName = (TextView) findViewById(R.id.detail_Name);
        makeName.setText(currentCongress.getName());

        TextView TypeText = (TextView) findViewById(R.id.detail_CongressType);
        TypeText.setText("" + currentCongress.getCongressType());

        TextView PartyText = (TextView) findViewById(R.id.detail_Party);
        PartyText.setText(currentCongress.getParty());

        String party1 = currentCongress.getParty();

        ImageView imageView1 = (ImageView) findViewById(R.id.scr3_item_logo);

        if(party1.equals("Democrat")) {
            imageView1.setImageResource(R.drawable.demlogo11);
        } else         if(party1.equals("Republican")) {
            imageView1.setImageResource(R.drawable.replogo11);

        } else
            imageView1.setImageResource(R.drawable.bunny);

        TextView emailText = (TextView) findViewById(R.id.detail_email);
        emailText.setText(currentCongress.getEmail());

        TextView websiteText = (TextView) findViewById(R.id.detail_website);
        websiteText.setText(currentCongress.getWebsite());

//        TextView tweetText = (TextView) findViewById(R.id.detail_tweet);
//        tweetText.setText(currentCongress.getLastTweet());

        TextView EndofTermText = (TextView) findViewById(R.id.detail_EndofTerm);
        EndofTermText.setText("End of Term: "+currentCongress.getEndOfTerm());


        TextView CommitteeText = (TextView) findViewById(R.id.detail_Committee);
//        CommitteeText.setText(currentCongress.getCommittee());
        Log.i(TAG, "getCommittee size: " + currentCongress.getCommittee().size());


        if(currentCongress.getCommittee().size()>0) {

            String s = currentCongress.getCommittee().get(0).getName();
            CommitteeText.setText(s);

        } else {

            String s = "None";
            CommitteeText.setText(s);
        }

        TextView BillSponsoredText = (TextView) findViewById(R.id.detail_BillSponsored);
//        BillSponsoredText.setText(currentCongress.getBillSponsored());
        Log.i(TAG, "getCommittee size: " + currentCongress.getBillSponsored().size());

        if(currentCongress.getBillSponsored().size()>0) {

            String s = currentCongress.getBillSponsored().get(0).getLast_action_at();
            String s1 = s + ": " + currentCongress.getBillSponsored().get(0).getShort_title();
            BillSponsoredText.setText(s1);
        } else {

            String s1 = "None" ;
            BillSponsoredText.setText(s1);
        }

    }

    public void Screen3Close(View view) {
        Intent IntentBackToScreen2 = new Intent();
        Log.i(TAG, "IntentBackToScreen2");

        setResult(RESULT_OK, IntentBackToScreen2);
        finish();
    }


    private void popupateCongressList() {
/*
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
 */

    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVarApp.getInstance().shareA = 3;
    }
    public static void onMobileSend(String s) {
        int tsize = GlobalVarApp.getInstance().msharedlist.size();
        if (tsize > 0) {
            Log.i("WADEmS3:", s + "onMobileSend: get(0):" + GlobalVarApp.getInstance().msharedlist.get(tsize - 1));
        } else {
            Log.i("WADEmS3:", s + "onMobileSend: Error in size:");
        }

    }


}
