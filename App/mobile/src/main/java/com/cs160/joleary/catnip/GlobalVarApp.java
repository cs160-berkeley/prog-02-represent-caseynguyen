package com.cs160.joleary.catnip;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class GlobalVarApp extends Application {
    private static GlobalVarApp m_instance;
    public int shareA;
    public List<String> msharedlist = new ArrayList<String>();
    public List<Congress> global_myCongress = new ArrayList<Congress>();
    public List<String> global_county = new ArrayList<>();
    public static int global_shake = 0 ;

    public static double mLatitude ;
    public static double mLongitude ;



    @Override
    public void onCreate() {
        super.onCreate();
        m_instance = this;

    }

    /**
     * Get instance of this application
     * @return instance of this application
     */
    public static GlobalVarApp getInstance() {
        return m_instance;
    }

}
