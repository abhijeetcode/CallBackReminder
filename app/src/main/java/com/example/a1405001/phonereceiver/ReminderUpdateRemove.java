package com.example.a1405001.phonereceiver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReminderUpdateRemove extends AppCompatActivity {
    Intent intent;
    TextView textView1,textView2,textView3;
    String moblieNumber,time,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_update_remove);
        intent = getIntent();
        moblieNumber = intent.getStringExtra("phoneNumber");
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");

    }
}
