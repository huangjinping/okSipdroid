package org.sipdroid.sipua;

public class SipStatus {
    private String text;
    private int mInCallResId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getmInCallResId() {
        return mInCallResId;
    }

    public void setmInCallResId(int mInCallResId) {
        this.mInCallResId = mInCallResId;
    }
}
