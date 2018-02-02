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
    Promtbox promtbox = new Promtbox();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER) != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            Log.d("Mobile Number"," "+promtbox.phone_number);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("Phone Ringing", phoneNumber);
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v("Phone Missed", phoneNumber + "");
                displayDialog(context);
            }
        }
    }
    private void displayDialog(Context context) {
        Intent i = new Intent(context, Promtbox.class);
        i.putExtra("phoneNumber",phoneNumber);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
