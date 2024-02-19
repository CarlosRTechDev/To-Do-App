package com.example.todoapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SESSION_PREFERENCES = "SessionPreferences";
    private static final String KEY_LOGGED_IN = "loggedIn";
    private static final String KEY_ID_USER = "id_user";

    private final SharedPreferences sharedPreferences;
    //private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCES, Context.MODE_PRIVATE);
        //editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        sharedPreferences.edit().putBoolean(KEY_LOGGED_IN, loggedIn).apply();
    }

    public void saveIdUser(int value) {
        sharedPreferences.edit().putInt(KEY_ID_USER, value).apply();
        /*editor.putInt(KEY_ID_USER, value);
        editor.apply();*/
    }

    public int readIdUser() {
        return sharedPreferences.getInt(KEY_ID_USER, 0);
    }

}
