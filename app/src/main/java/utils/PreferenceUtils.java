package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xyz on 24/6/17.
 */

public class PreferenceUtils {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PREF_NAME = "SignupCredentials";


    public static void saveSignupCredentials(Context context, String email, String username, String password) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USERNAME, "");
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PASSWORD, "");
    }

}
