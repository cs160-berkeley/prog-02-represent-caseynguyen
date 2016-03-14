package com.cs160.joleary.catnip;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;


public class wearGlobalVarApp extends Application {
    private static wearGlobalVarApp m_instance;
    public int shareA;
    public List<String> msharedlist = new ArrayList<String>();
    public List<wearCongress> global_myCongress = new ArrayList<wearCongress>();
    public List<String> global_county = new ArrayList<String>();




    @Override
    public void onCreate() {
        super.onCreate();
        m_instance = this;

    }

    /**
     * Get instance of this application
     * @return instance of this application
     */
    public static wearGlobalVarApp getInstance() {
        return m_instance;
    }

}
