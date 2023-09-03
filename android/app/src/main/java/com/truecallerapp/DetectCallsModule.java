package com.truecallerapp;

import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class DetectCallsModule extends ReactContextBaseJavaModule {
    private TelephonyManager telephonyManager;
    private CustomTelephonyCallback telephonyCallback;

    public DetectCallsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        telephonyManager = (TelephonyManager) reactContext.getSystemService(reactContext.TELEPHONY_SERVICE);
        telephonyCallback = new CustomTelephonyCallback();
    }

    private class CustomTelephonyCallback extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        @Override
        public void onCallStateChanged(int state) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    showToast("Incoming call");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    showToast("Call started");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    showToast("Call ended");
                    break;
            }
        }

    }

    private void showToast(String message) {
        Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getName() {
        return "DetectCallsModule";
    }

    @ReactMethod
    public void startListening() {
        telephonyManager.registerTelephonyCallback(getReactApplicationContext().getMainExecutor(), telephonyCallback);
    }

    @ReactMethod
    public void stopListening() {
        telephonyManager.unregisterTelephonyCallback(telephonyCallback);
    }


    @ReactMethod
    public void testModule(String name, Callback cb) {
        try {
            cb.invoke(null, name);
        } catch (Exception e) {
            cb.invoke(e, null);
        }
    }
}
