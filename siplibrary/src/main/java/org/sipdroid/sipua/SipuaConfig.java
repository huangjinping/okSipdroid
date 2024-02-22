package org.sipdroid.sipua;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.sipdroid.sipua.ui.Receiver;
import org.sipdroid.sipua.ui.Sipdroid;

public class SipuaConfig {

    final static String getSharedPrefsFile(Context context) {
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
        edit.commit();
    }

    public static void startInCall(Activity activity, String target) {
        Receiver.engine(activity).registerMore();
        Sipdroid.on(activity,true);
        Receiver.engine(activity).call(target, true);
    }
}
