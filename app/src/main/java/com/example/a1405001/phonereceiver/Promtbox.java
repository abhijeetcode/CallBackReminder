package com.example.a1405001.phonereceiver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import shortroid.com.shortroid.ShortRoidDB.ShortRoidDB;

public class Promtbox extends Activity {
    AlertDialog alertDialog;
    private String selectedItem;
    Intent intent;
    final CharSequence[] items = {"15 min later","30 min later", "Coustomized"};
    //MyPhoneReceiver myPhoneReceiver = new MyPhoneReceiver();
    private Context context;
    private static final String TIME_PATTERN = "HH:mm";
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    int hours;
    int mins;
    int month;
    int year;
    int day;
    String phone_number;
    Date dateTime;
    HashMap<String, String> attributes;
    HashMap<String, Object> data;
    ShortRoidDB shortdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog levelDialog;
        //Database
        intent = getIntent();
        attributes = new HashMap<>();
        phone_number = intent.getStringExtra("phoneNumber");
        attributes.put("NUMBER", "TEXT NOT NULL");
        attributes.put("MINUTE", "INT");
        attributes.put("HOUR", "INT");
        attributes.put("DATE", "INT");
        attributes.put("MONTH", "INT");
        attributes.put("YEAR", "INT");
        shortdb = new ShortRoidDB(Promtbox.this, "CallbackDetails", 1, "Miscall", attributes);

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(phone_number + " Callback After");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int num) {
                AlertDialog alertDialog = new AlertDialog.Builder(Promtbox.this).create(); //Read Update
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
                        String s;
                        Calendar now = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                        if (selectedItem == "Coustomized") {
                            DisplayTimeDate();
                        } else if (selectedItem == "15 min later") {
                            year = now.get(Calendar.YEAR);
                            month = (now.get(Calendar.MONTH) + 1);
                            day = now.get(Calendar.DATE);
                            now.add(Calendar.MINUTE, 15);
                            s = df.format(now.getTime());
                            String[] arrOfStr = s.split(":");
                            mins = Integer.parseInt((arrOfStr[0]));
                            hours = Integer.parseInt((arrOfStr[1]));
                            //Log.d("Nowtime ", " " + phone_number);
                            Toast.makeText(getApplicationContext(), "" + phone_number + " " + mins + " " + hours + " " + year + " " + month + " " + day, Toast.LENGTH_LONG).show();
                            //Insert
                            InsertData(phone_number, mins, hours, year, month, day);
                        } else {
                            now.add(Calendar.MINUTE, 30);
                            year = now.get(Calendar.YEAR);
                            month = (now.get(Calendar.MONTH) + 1);
                            day = now.get(Calendar.DATE);
                            s = df.format(now.getTime());
                            String[] arrOfStr = s.split(":");
                            mins = Integer.parseInt((arrOfStr[0]));
                            hours = Integer.parseInt((arrOfStr[1]));
                            Log.d("Nowtime ", " " + phone_number);
                            //Insert
                            InsertData(phone_number, mins, hours, year, month, day);
                            //Log.v("All attributes", phone_number + " " + mins + " " + hours + " " + year + " " + month + " " + day);
                            Toast.makeText(getApplicationContext(), "" + phone_number + " " + mins + " " + hours + " " + year + " " + month + " " + day,
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        retriveAllData();
        levelDialog = builder.create();
        levelDialog.show();
    }

    public void DisplayTimeDate() {

        final View dialogView = View.inflate(this, R.layout.time_date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
                Date dateTime = calendar.getTime();
                year = calendar.get(Calendar.YEAR);
                month = (calendar.get(Calendar.MONTH) + 1);
                day = calendar.get(Calendar.DATE);
                mins = dateTime.getMinutes();
                hours = dateTime.getHours();
                //Log.v("DateTimePicker Debugg", "" + dateTime);
                //Log.v("DateTimePicker Debugg", "" + day + " " + month + " " + year + " " + hours + " " + mins);
                InsertData(phone_number, year, month, day, hours, mins);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void InsertData(String phone_number, int mins, int hours, int year, int month, int day) {

        Log.v("InsertDataIntoDB", phone_number + " " + mins + " " + hours + " " + year + " " + month + " " + day);
        data = new HashMap<>();
        data.put("NUMBER", phone_number);
        data.put("MINUTE", mins);
        data.put("HOUR", hours);
        data.put("DATE", year);
        data.put("MONTH", month);
        data.put("YEAR", day);
        shortdb.insert(data);
    }


    public void retriveAllData() {
        String PhoneNumber, Time, date;
        List<HashMap<String, String>> list;
        String q = "SELECT * FROM Miscall";
        list = shortdb.query(q);

        for (HashMap<String, String> hmap : list) {
            PhoneNumber = hmap.get("NUMBER");
            Time = hmap.get("MINUTE") + ":" + hmap.get("HOUR");
            date = hmap.get("DATE") + "/" + hmap.get("MONTH") + "/" + hmap.get("YEAR");
            //Log.v("RetriveDataToDB", PhoneNumber + " " + Time + " " + date);
        }

    }
}