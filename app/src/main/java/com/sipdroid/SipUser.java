package com.sipdroid;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import org.sipdroid.sipua.RegisterStatusCallBack;
import org.sipdroid.sipua.SipStatus;
import org.sipdroid.sipua.SipuaConfig;

public class SipUser {

    OnSipCallBack callBack;

    String key;
    private Activity context;


    public SipUser(Activity context) {
        this.context = context;
    }

    public void setCallBack(OnSipCallBack callBack, String key) {
        this.callBack = callBack;
        this.key = key;
    }


    public void initSip() {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        try {
            if (callBack != null) {
                callBack.onCallBackTitle("");
            }
            if (callBack != null) {
                callBack.onAfterRegister();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(key)) {
            SipStatus status = SipuaConfig.registerSipCallBack(context, new RegisterStatusCallBack(key) {
                @Override
                protected void onRegisterStatusUpdate(SipStatus status) {
                    if (!key.equals(status.getKey())) {
                        return;
                    }
                    try {
                        if (callBack != null) {
                            if (key.equals(status.getKey())) {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("okhttsw", status.getKey() + "=====" + Thread.currentThread().getName() + "=======" + status.getText());

                                        callBack.onCallBack(status);
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (callBack != null) {
                callBack.onCallBack(status);
            }
        }

    }


    public void unBind() {
        try {
            if (!TextUtils.isEmpty(key)) {
                SipuaConfig.unRegisterSipCallBack(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
