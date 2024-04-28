package org.sipdroid.sipua;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    public static final String SIP_SP_ROOT_NAME = "SIP_SP_ROOT_NAME";
    public static final String SIP_STATE = "sip_state";


    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(SIP_SP_ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(SIP_SP_ROOT_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
}
