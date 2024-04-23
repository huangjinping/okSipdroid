package org.sipdroid.sipua;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.sipdroid.sipua.ui.Receiver;
import org.sipdroid.sipua.ui.Sipdroid;

import java.io.File;

public class SipuaConfig {

    public final static String getSharedPrefsFile(Context context) {
        String packageName = context.getPackageName();
        String result = packageName + "_preferences";
        return result;
    }

    public static void init(Context context, ConfigSip configSip) {
        SharedPreferences setting_info = context.getSharedPreferences(getSharedPrefsFile(context), MODE_PRIVATE);
        SharedPreferences.Editor edit = setting_info.edit();
        edit.putString("server", configSip.getServer());
        edit.putString("dns0", configSip.getDns0());
        edit.putString("port", configSip.getPort());
        edit.putString("username", configSip.getUsername());
        edit.putString("protocol", configSip.getProtocol());
        edit.putString("protocol1", configSip.getProtocol());
        edit.putString("password", configSip.getPassword());
        //   ------------------支持3g和wifi------------------------>>>>>>
        edit.putBoolean("3g", true);
        edit.putBoolean("wlan", true);
//        edit.putBoolean("edge", true);
        edit.putString("eargain", "1.0");
        edit.putString("micgain", "1.0");
        edit.putString("heargain", "1.0");

        edit.commit();
    }

    public static void startInCall(Activity activity, String target) {
        Receiver.engine(activity).registerMore();
        Sipdroid.on(activity, true);
        Receiver.engine(activity).call(target, true);
    }


    public static void deleteUser(Context context) {

        try {
            String packageName = context.getPackageName();

            File pref_xml = new File("data/data/" + packageName + "/shared_prefs/" + getSharedPrefsFile(context) + ".xml");
            if (pref_xml.exists()) {
                pref_xml.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        SharedPreferences setting_info = context.getSharedPreferences(getSharedPrefsFile(context), MODE_PRIVATE);
//        SharedPreferences.Editor edit = setting_info.edit();
//        edit.clear().commit();

//        SharedPreferences setting_info = context.getSharedPreferences(getSharedPrefsFile(context), MODE_PRIVATE);
//        SharedPreferences.Editor edit = setting_info.edit();
//        edit.putString("server","");
//        edit.putString("dns0", "");
//        edit.putString("port", "");
//        edit.putString("username", "");
//        edit.putString("protocol", "");
//        edit.putString("protocol1", "");
//        edit.putString("password", "");
//        edit.commit();
    }
}
