package org.sipdroid.sipua;

public abstract class RegisterStatusCallBack {
    String key;

    public RegisterStatusCallBack(String key) {
        this.key = key;
    }

    protected abstract void onRegisterStatusUpdate(SipStatus status);
}
