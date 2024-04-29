package org.sipdroid.sipua;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONObject;
import org.sipdroid.sipua.ui.Receiver;
import org.sipdroid.sipua.ui.Sipdroid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SipuaConfig {

    private static List<RegisterStatusCallBack> registerStatusCallBackList;

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

    public static SipStatus registerSipCallBack(Context context, RegisterStatusCallBack callBack) {
        try {
            if (registerStatusCallBackList == null) {
                registerStatusCallBackList = new ArrayList<>();
            }
            for (int i = 0; i < registerStatusCallBackList.size(); i++) {
                RegisterStatusCallBack call = registerStatusCallBackList.get(i);
                if (call.key.equals(callBack.key)) {
                    registerStatusCallBackList.remove(i);
                }
            }
            registerStatusCallBackList.add(callBack);
            System.out.println("==SipStatus===1====" + registerStatusCallBackList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        SipStatus status = new SipStatus();
        status.setText("");
        status.setmInCallResId(0);
        try {
            String result = SpUtils.getString(context, SpUtils.SIP_STATE, "");
            if (!TextUtils.isEmpty(result)) {
                JSONObject root = new JSONObject(result);
                String text = root.optString("text");
                int mInCallResId = root.optInt("mInCallResId");
                status.setText(text);
                status.setmInCallResId(mInCallResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void observer(Context context, SipStatus status) {
        if (registerStatusCallBackList == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mInCallResId", status.getmInCallResId());
            jsonObject.put("text", status.getText());
            SpUtils.putString(context, SpUtils.SIP_STATE, jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (RegisterStatusCallBack callBack : registerStatusCallBackList
            ) {
                status.setKey(callBack.key);
                callBack.onRegisterStatusUpdate(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unRegisterSipCallBack(String key) {
        try {
            if (registerStatusCallBackList == null) {
                registerStatusCallBackList = new ArrayList<>();
            }
            for (int i = 0; i < registerStatusCallBackList.size(); i++) {
                RegisterStatusCallBack callBack = registerStatusCallBackList.get(i);
                if (key.equals(callBack.key)) {
                    registerStatusCallBackList.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
