package com.example.a1405001.phonereceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

public class Promtbox extends Activity {
    AlertDialog alertDialog;
    private String selectedItem;
    final CharSequence[] items = {" 15 min later ", " 30 min later ", " Coustomized "};
    MyPhoneReceiver myPhoneReceiver = new MyPhoneReceiver();
    String number = myPhoneReceiver.phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog levelDialog;
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select The Time");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int num) {
                switch (num) {
                    case 0:
                        selectedItem = (String) items[num];
                        break;
                    case 1:
                        selectedItem = (String) items[num];
                        break;
                    case 2:
                        selectedItem = (String) items[num];
                        break;
                }
            }
        });
        builder.
                setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), " " + selectedItem, Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        levelDialog = builder.create();
        levelDialog.show();
    }
}