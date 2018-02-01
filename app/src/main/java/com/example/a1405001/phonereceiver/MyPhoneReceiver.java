package com.example.a1405001.phonereceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 1405001 on 01-02-2018.
 */

public class MyPhoneReceiver extends BroadcastReceiver {
    String phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null && extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER) != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.v("Phone Ringing", phoneNumber);
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.v("Phone Missed", phoneNumber + "");
                displayDialog(context);
            }
        }
    }

    private void displayDialog(Context context) {
        Intent i = new Intent(context, Promtbox.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
