package com.sipdroid;

import org.sipdroid.sipua.SipStatus;

/**
 * author Created by 20222 on 2021/1/27.
 * email : 2022
 */
public abstract class OnSipCallBack {


    protected abstract void onCallBack(SipStatus status);

    protected abstract void onCallBackTitle(String title);

    protected abstract void onAfterRegister();

}
