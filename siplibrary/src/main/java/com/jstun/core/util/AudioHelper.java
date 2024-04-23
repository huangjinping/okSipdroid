package com.jstun.core.util;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import org.sipdroid.media.RtpStreamReceiver;
import org.sipdroid.sipua.BuildConfig;
import org.sipdroid.sipua.ui.Receiver;

public class AudioHelper {

    public static boolean isHeadsetPlugged(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getMode() == AudioManager.MODE_NORMAL;
    }

    public static boolean isHeadsetPluggedV2(Context context) {
        boolean result = false;
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.isWiredHeadsetOn()) {
                result = true;
            } else if (audioManager.isBluetoothA2dpOn()) {
                result = true;
            } else if (audioManager.isSpeakerphoneOn()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void debugAlert(Context context, String msg) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void updateSpeaker(Context context) {
        if (AudioHelper.isHeadsetPluggedV2(context)) {
            Log.d("isHeadsetPlugged", "---------1");

            Log.d("isHeadsetPlugged", "=======" + (RtpStreamReceiver.speakermode == AudioManager.MODE_NORMAL ?
                    AudioManager.MODE_IN_CALL : AudioManager.MODE_NORMAL));

//            Receiver.engine(context).speaker(RtpStreamReceiver.speakermode == AudioManager.MODE_NORMAL ?
//                    AudioManager.MODE_IN_CALL : AudioManager.MODE_NORMAL);
            Receiver.engine(context).speaker(AudioManager.MODE_NORMAL);
            if (BuildConfig.DEBUG) {
                Toast.makeText(context, "==1", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("isHeadsetPlugged", "---------0");
            if (BuildConfig.DEBUG) {
                Toast.makeText(context, "==0", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
