package com.cs160.joleary.catnip;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunlightlabs.api.ApiCall;
import com.sunlightlabs.entities.Bill;
import com.sunlightlabs.entities.Committee;
import com.sunlightlabs.entities.Legislator;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class Screen2Activity extends Activity {
    private static final String SUNLIGHT_API_KEY = "1b1fbda301ad459193be29ae1049a4d7";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Di8DQ9d73UFartyGp41fQuLXM";
    private static final String TWITTER_SECRET = "fp8162LXRCRRZZRJHbBUlpS9N5zF4EnFddBL3di7xsWEh4n98o";

    private List<Congress> myCongress = new ArrayList<>();
    public String TAG = "WADEm2";
    List<Congress> localmyCongress = GlobalVarApp.getInstance().global_myCongress;

    private String lastTweet;
    private Boolean loop = true;
    private Object lock = new Object();

    int zipval = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen2_layout);

        final TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        GlobalVarApp.getInstance().shareA = 1;
        final int Zip1;

        Intent IntentFrom_Screen1 = getIntent();
        String SelectType = IntentFrom_Screen1.getExtras().getString("Screen1SelectType");
        Log.i(TAG, "Screen1SelectType2222 " + SelectType);
        final String zipCode = ((SelectType.equals("ZIP"))?
                IntentFrom_Screen1.getExtras().getString("Ziptext") : ""  );

        if (SelectType.equals("ZIP") ){
            zipval = Integer.valueOf(IntentFrom_Screen1.getExtras().getString("Ziptext"));
        }


        final String latitude = ((SelectType.equals("LOC"))?
                String.valueOf(GlobalVarApp.mLatitude) : ""  );
        final String longitude =  ((SelectType.equals("LOC"))?
                String.valueOf(GlobalVarApp.mLongitude) : ""  );

        Log.d(TAG, "ZIP:" + zipCode + " long:" + longitude + " lat: " + latitude) ;
        GlobalVarApp.getInstance().global_myCongress.clear(); //


        new Thread() {
            @Override
            public void run() {
                ApiCall me = new ApiCall(SUNLIGHT_API_KEY);
                Legislator[] legislators = null;
                System.out.println("Reading All Legislators");
                legislators = Legislator.getLegislatorsForZipCode(me, zipCode, latitude, longitude);
                for (int i = 0; i < Math.min(5, legislators.length); i++) {
                    Legislator l = legislators[i];
                    String bioguide_id = l.getProperty("bioguide_id");

                    ArrayList<CommiteeObj> CommiteeObjs = new ArrayList<CommiteeObj>();
                    System.out.println("===============================================");
                    System.out.println("Reading All House Committees - bioguide_id: " + bioguide_id);
                    Committee[] committees = Committee.allCommittees(me, bioguide_id);
                    for (int i1 = 0; i1 < Math.min(5, committees.length); i1++) {
                        Committee l1 = committees[i1];
                        l1.show(i1 + 1);
                        System.out.println();
                        String name = l1.getProperty("name");
                        CommiteeObjs.add(new CommiteeObj(name));
                        System.out.println("======> name: " + name);
                    }

                    ArrayList<BillSponsoredObj> billObjs = new ArrayList<BillSponsoredObj>();
                    System.out.println("===============================================");
                    System.out.println("Reading All Bill - bioguide_id: " + bioguide_id);
                    Bill[] bills = Bill.allBills(me, bioguide_id);
                    for (int i2 = 0; i2 < Math.min(5, committees.length); i2++) {
                        Bill l2 = bills[i2];
                        l2.show(i2 + 1);
                        System.out.println();
                        String last_action = l2.getProperty("last_action_at");
                        String short_title = l2.getProperty("short_title");
                        billObjs.add(new BillSponsoredObj(last_action, short_title));
                        System.out.println("======> last_action_at: " + last_action);
                        System.out.println("======> short_title: " + short_title);
                    }
                    // if (Zip1 == 1) sendtoWatch("NEW" + "\\Diane Feinstein" + "\\Democrat\\");
                    String name = l.getProperty("first_name") + " " + l.getProperty("last_name");
                    String picUrl = String.format("%s.jpg", bioguide_id); //R.drawable.feinstein;
                    String CongressType = "Rep".equalsIgnoreCase(l.getProperty("title")) ? "Representative" : "Senator";
                    String Party = "";
                    if ("D".equals(l.getProperty("party")))
                        Party = "Democrat";
                    else if ("R".equals(l.getProperty("party")))
                        Party = "Republican";
                    else if ("I".equals(l.getProperty("party")))
                        Party = "Independent";
                    String email = l.getProperty("oc_email");
                    String website = l.getProperty("website");
                    String term_end = l.getProperty("term_end");

                    loop = true;
                    getLastTwitter(l.getProperty("twitter_id"));
                    synchronized (lock) {
                        while (loop) {
                            try {
                                lock.wait();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            loop = false;
                        }
                    }
                    System.out.println("======> lastTweet: " + lastTweet);
                    localmyCongress.add(new Congress(name, picUrl, CongressType, Party, email, website, lastTweet, term_end, CommiteeObjs, billObjs));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // populateCongressList(Zip1);
                        populateListView();
                        registerClickCallback();
                    }
                });
            }
        }.start();

//        System.out.println("=====> percentage: " + getResultElection("Alabama", "romney-percentage"));
//        System.out.println("=====> percentage: " + getResultElection("Alabama", "obama-percentage"));

        String Lat = "37.7749295";
        String Long = "-122.4194155";

    }

    /**
     * read a URL text
     * @param url non-null url
     * @return non-null String
     */
    public JSONObject getJSONfromURL(String url) {
        StringBuilder sb = new StringBuilder();
        JSONObject jObject = null;
        try {
            URL u = new URL(url);
            URLConnection yc = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
                sb.append("\n");
            }
            in.close();
            // try parse the string to a JSON object
            try {
                jObject = new JSONObject(sb.toString());
            } catch (JSONException e) {
                Log.e("log_tag", "Error parsing data " + e.toString());
            }
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jObject;
    }

    public String getCountyName(String Lat, String Long, String Type, String Name) {
        String data = "";
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + Lat + "," + Long + "&sensor=true%22";
        try {
            JSONArray jsonArray = getJSONfromURL(url).getJSONArray("results");
            // JSONArray has x JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Iterator iter = jsonObj.keys();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    if (key.equals("address_components")) {
                        JSONArray arr = jsonObj.getJSONArray(key);
                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject subObj = arr.getJSONObject(j);
                            Iterator iter_comp = subObj.keys();
                            while (iter_comp.hasNext()) {
                                boolean found = false;
                                String subkey = (String) iter_comp.next();
                                if (subkey.equals("types")) {
                                    JSONArray arr1 = subObj.getJSONArray(subkey);
                                    for (int l = 0; l < arr1.length(); l++) {
                                        String tmp = arr1.getString(l);
                                        if (arr1.getString(l).equals(Type)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (found) {
                                    data = subObj.getString(Name);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return data;
    }


    public String getCountyNameZIP(String ZIP, String Type, String Name) {
        String data = "";
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + ZIP + "&sensor=true%22";
        try {
            JSONArray jsonArray = getJSONfromURL(url).getJSONArray("results");
            // JSONArray has x JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                // Creating JSONObject from JSONArray
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Iterator iter = jsonObj.keys();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    if (key.equals("address_components")) {
                        JSONArray arr = jsonObj.getJSONArray(key);
                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject subObj = arr.getJSONObject(j);
                            Iterator iter_comp = subObj.keys();
                            while (iter_comp.hasNext()) {
                                boolean found = false;
                                String subkey = (String) iter_comp.next();
                                if (subkey.equals("types")) {
                                    JSONArray arr1 = subObj.getJSONArray(subkey);
                                    for (int l = 0; l < arr1.length(); l++) {
                                        String tmp = arr1.getString(l);
                                        if (arr1.getString(l).equals(Type)) {
                                            found = true;
                                            break;
                                        }
                                    }
                                }
                                if (found) {
                                    data = subObj.getString(Name);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return data;
    }


    public double getResultElection(String CountyName, String Name) {
        double percentage = 0;
        try {
            String resultJSON = AssetJSONFile("election-county-2012.json", getApplicationContext());
            try {
                JSONArray jsonArray = new JSONArray(resultJSON);
                // JSONArray has x JSONObject
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Creating JSONObject from JSONArray
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Iterator iter = jsonObj.keys();
                    while (iter.hasNext()) {
                        String key = (String) iter.next();
                        String value = jsonObj.getString(key);
                        if (CountyName.equals(value)) {
                            percentage = jsonObj.getDouble(Name);
                            break;
                        }
                        //System.out.println("======> Key Value - key: " + key + " Value: " + value);
                    }
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return percentage;
    }

    public void getLastTwitter(final String screenName) {
        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient(session);
                twitterApiClient.getStatusesService().userTimeline(null,
                        screenName, 1, null, null, false, false, false, true, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> listResult) {
                                for (Tweet tweet : listResult.data) {
                                    lastTweet = tweet.text;
                                    synchronized(lock) {
                                        lock.notify();
                                    }
                                }
                            }

                            @Override
                            public void failure(TwitterException e) {
                                e.printStackTrace();
                                synchronized(lock) {
                                    lock.notify();
                                };
                            }
                        });
            }

            @Override
            public void failure(TwitterException e) {
                e.printStackTrace();
            }
        });
    }

//    private void populateCongressList(int Zip1) {
//        if (Zip1 == 1) sendtoWatch("NEW" + "\\Diane Feinstein" + "\\Democrat\\");
//
//
//    }

    public static String AssetJSONFile(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    private void sendtoWatch(String s) {
        // stop here and work on the watch side
        // cccc path is private static final String path2wear = "WADE2WEAR";

        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
        sendIntent.putExtra("REP!", s);
        startService(sendIntent);
    }

    private void populateListView() {

//        System.out.println("=====> percentage: " + getResultElection("Alabama", "romney-percentage"));
//        System.out.println("=====> percentage: " + getResultElection("Alabama", "obama-percentage"));
//        Log.i(TAG, "getResultElection:");
//        Log.i(TAG, "getResultElection:" + getResultElection("Alabama", "obama-percentage"));



//        System.out.println("=====> countyName: " + getCountyName(Lat, Long, "administrative_area_level_1", "short_name"));
//        System.out.println("=====> countyName: " + getCountyName(Lat, Long, "administrative_area_level_2", "short_name"));
//
//        Log.i(TAG,  "countyName: " + getCountyName(Lat, Long, "administrative_area_level_2", "short_name"));
//
//
        String  countyName ;

        if( zipval == 0) {
            String Long = String.valueOf(GlobalVarApp.mLongitude);
            String Lat = String.valueOf(GlobalVarApp.mLatitude);

             countyName = getCountyName(Lat, Long, "administrative_area_level_2", "short_name");
        } else {
            String ZIP = String.valueOf(zipval);

             countyName = getCountyNameZIP(ZIP, "administrative_area_level_2", "short_name");
        }
        String CountyShort = countyName.replace( " County", "") ;
        String CountyShort1 = CountyShort.replace( "County", "") ;
        Log.i(TAG, "CountyN=" + CountyShort1 ) ;

//
//        Log.i(TAG,  "countyName: " + getCountyName(Lat, Long, "administrative_area_level_2", "short_name"));
//
//        System.out.println("=====> percentage: " + getResultElection(countyName, "romney-percentage"));
//        System.out.println("=====> percentage: " + getResultElection(countyName, "obama-percentage"));
        GlobalVarApp.getInstance().global_county.clear();
        GlobalVarApp.getInstance().global_county.add(CountyShort1 );


        GlobalVarApp.getInstance().global_county.add(String.valueOf(getResultElection(CountyShort1, "obama-percentage")));
        GlobalVarApp.getInstance().global_county.add(String.valueOf(getResultElection(CountyShort1, "romney-percentage")));
        if(GlobalVarApp.getInstance().global_county.get(1).equals("") ||
                GlobalVarApp.getInstance().global_county.get(1).equals("") ) {
            GlobalVarApp.getInstance().global_county.clear();
            GlobalVarApp.getInstance().global_county.add("Not Available");
            GlobalVarApp.getInstance().global_county.add("0" );
            GlobalVarApp.getInstance().global_county.add("0" );
        }



        // format: ZIPLOC
        // ZIPLOC + #person (name+party) + CountrName+Obama%+Romney%
        String msg2Watch1 = "ZIP" + "~" + String.valueOf(localmyCongress.size()) ;
        String msg2Watch2 = BuildNamesString(localmyCongress) ;
        String msg2Watch3 = GlobalVarApp.getInstance().global_county.get(0) + "~" +
                GlobalVarApp.getInstance().global_county.get(1) + "~" +
                GlobalVarApp.getInstance().global_county.get(2) ;
        String msg2WatchAll = msg2Watch1 +
                msg2Watch2 + "~" + msg2Watch3  ;
        // print log
        Log.i(TAG, "msg2WatchAll:"+msg2WatchAll);
        Log.i(TAG, "msg2Watch2:"+msg2Watch2);
        Log.i(TAG, "msg2Watch2:"+msg2Watch3);

        sendtoWatch( msg2WatchAll);

        ArrayAdapter<Congress> adapter = new MyListAdapter(this);
        ListView list = (ListView) findViewById(R.id.CongressListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Congress> {
        private Context mContext;
        public MyListAdapter(Context context) {
            super(Screen2Activity.this, R.layout.item_view, localmyCongress);
            this.mContext = context;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            Congress currentCongress = localmyCongress.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            String url = String.format("https://theunitedstates.io/images/congress/225x275/%s", currentCongress.getPic());
            Picasso
                    .with(mContext)
                    .load(url)
                    .into(imageView);

//            imageView.setImageResource(currentCongress.getPic());

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

            String party1 = currentCongress.getParty();

// cccc
            ImageView imageView1 = (ImageView) itemView.findViewById(R.id.item_logo);

            if(party1.equals("Democrat")) {
                imageView1.setImageResource(R.drawable.demlogo11);
            } else if(party1.equals("Republican")) {
                imageView1.setImageResource(R.drawable.replogo11);

            } else
                imageView1.setImageResource(R.drawable.bunny);

            return itemView;
        }
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.CongressListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {
                Congress clicked1 = localmyCongress.get(position);
                String message = "You clicked position " + position
                        + " and the name is " + clicked1.getName();
                // Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                Intent toScreen3Intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                toScreen3Intent.putExtra("PositionSelected", position);
                Log.i(TAG, "toScreen3Intent");

                final int result = 1;
                startActivityForResult(toScreen3Intent, result);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalVarApp.getInstance().shareA = 2;
    }

    public static void onMobileSend(String s) {
        int tsize = GlobalVarApp.getInstance().msharedlist.size();
        //Log.i("Screen2:", "WADE, sharedsize:" + size);
        //Log.i("Screen2:", "WADE, s=:" + s);
        if (tsize > 0) {
            Log.i("WADEmS2:", s + "onMobileSend: get(0):" + GlobalVarApp.getInstance().msharedlist.get(tsize - 1));
        } else {
            Log.i("WADEmS2:", s + "onMobileSend: Error in size:");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String s = data.getStringExtra("SCR2");
        Log.i("WADEmS2:", "RetMsg SCR3:" + s);
    }

    // format: ZIPLOC
    // ZIPLOC + #person (name+party) + CountrName+Obama%+Romney%

    public String BuildNamesString(List<Congress> myCongress) {
        int i;
        Congress person1;


        Log.i("WADEmS2:", "myCongress.size:" + myCongress.size());
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < myCongress.size(); i++) {
            person1 = myCongress.get(i);
            sb.append("~").append(person1.getName());
            sb.append("~").append(person1.getParty());
            Log.i("WADEmS2sb:", sb.toString());

        }
        return (sb.toString());
    }

    public void Screen2Close(View view) {
        Intent IntentBackToScreen2 = new Intent();
        Log.i(TAG, "IntentBackToScreen1");

        setResult(RESULT_OK, IntentBackToScreen2);
        finish();
    }
}


