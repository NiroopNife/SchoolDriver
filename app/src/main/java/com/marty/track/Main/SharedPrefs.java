package com.marty.track.Main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharedPrefs {

    public static final String LOGGED_IN_PREF = "logged_in_status";

    static SharedPreferences getPreferences(Context mCtx){
        return PreferenceManager.getDefaultSharedPreferences(mCtx);
    }

    public static void setLoggedIn(Context mCtx, boolean loggedIn){
        SharedPreferences.Editor editor = getPreferences(mCtx).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context mCtx){
        return getPreferences(mCtx).getBoolean(LOGGED_IN_PREF, false);
    }
}
