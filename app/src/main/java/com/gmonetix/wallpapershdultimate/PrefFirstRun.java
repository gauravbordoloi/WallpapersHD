package com.gmonetix.wallpapershdultimate;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefFirstRun {

    SharedPreferences Pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "welcome_screen";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefFirstRun(Context _context) {
        this._context = _context;
        Pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = Pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return Pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
