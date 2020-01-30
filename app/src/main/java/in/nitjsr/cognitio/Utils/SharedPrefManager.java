package in.nitjsr.cognitio.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhishek on 26-Jan-18.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF = "SharedPref";
    private static final String IS_REGISTERED = "isRegistered";
    private static final String HAS_PAID = "hasPaid";
    private static SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public static boolean isRegistered() {
        return sharedPref.getBoolean(IS_REGISTERED, false);
    }

    public void setIsRegistered(boolean isRegistered) {
        editor.putBoolean(IS_REGISTERED, isRegistered).apply();
    }

    public boolean hasPaid() {
        return sharedPref.getBoolean(HAS_PAID, false);
    }

    public void setHasPaid(boolean hasPaid) {
        editor.putBoolean(HAS_PAID, hasPaid).apply();
    }


}
