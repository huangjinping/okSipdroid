package org.sipdroid.sipua;

public abstract class RegisterStatusCallBack {
    int key;

    public RegisterStatusCallBack(int key) {
        this.key = key;
    }

    protected abstract void onRegisterStatusUpdate(SipStatus status);
}
