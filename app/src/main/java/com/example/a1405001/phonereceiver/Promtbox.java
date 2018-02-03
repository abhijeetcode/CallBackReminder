package com.example.a1405001.phonereceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
    final CharSequence[] items = {"15 min later", "30 min later", "Coustomized"};
    private Context context;
    private static final String TIME_PATTERN = "HH:mm";
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    int hours, mins, month, year, day;
    String phone_number;
    Date dateTime;
    HashMap<String, String> attributes;
    HashMap<String, Object> data;
    ShortRoidDB shortdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog levelDialog;
        intent = getIntent();
        attributes = new HashMap<>();
        calendar = Calendar.getInstance();

        //DataBase Shemea
        phone_number = intent.getStringExtra("phoneNumber");
        attributes.put("NUMBER", "TEXT NOT NULL");
        attributes.put("HOUR", "INT");
        attributes.put("MINUTE", "INT");
        attributes.put("DATE", "INT");
        attributes.put("MONTH", "INT");
        attributes.put("YEAR", "INT");

        //AlarmManager for Notification
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        final PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final Calendar cal = Calendar.getInstance();

        //Define DB Nama and TableName
        shortdb = new ShortRoidDB(Promtbox.this, "CallbackDetails", 1, "Miscall", attributes);

        //Alert Dialog After RecyclerView
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
                            s = df.format(now.getTime());
                            String[] arrOfStr = s.split(":");
                            hours = Integer.parseInt((arrOfStr[0]));
                            mins = Integer.parseInt((arrOfStr[1]));
                            Toast.makeText(getApplicationContext(), "" + phone_number + " " + mins + " " + hours + " " + year + " " + month + " " + day, Toast.LENGTH_LONG).show();
                            //Insert into dataDB
                            InsertData(phone_number, hours, mins, year, month, day);

                            //Notify by Notifiction
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                            cal.set(Calendar.SECOND, 30);
                        } else {
                            year = now.get(Calendar.YEAR);
                            month = (now.get(Calendar.MONTH) + 1);
                            day = now.get(Calendar.DATE);
                            s = df.format(now.getTime());
                            String[] arrOfStr = s.split(":");
                            hours = Integer.parseInt((arrOfStr[0]));
                            mins = Integer.parseInt((arrOfStr[1]));

                            //Insert into DataDB
                            InsertData(phone_number, hours, mins, year, month, day);

                            //Notify me by Notification
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                            cal.set(Calendar.SECOND, 30);
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

        levelDialog = builder.create();
        levelDialog.show();
    }

    public void DisplayTimeDate() {
        final Calendar cal = Calendar.getInstance();
        final View dialogView = View.inflate(this, R.layout.time_date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        final PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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
                Log.v("DateTimePicker Debugg", "" + day + " " + month + " " + year + " " + hours + " " + mins);

                //Insert into DataDB
                InsertData(phone_number, hours, mins, year, month, day);

                //Notify me by Notifiacation
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
                calendar.set(Calendar.HOUR_OF_DAY, hours);
                cal.set(Calendar.MINUTE, mins);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void InsertData(String phone_number, int hours, int mins, int year, int month, int day) {

        Log.v("InsertDataIntoDB", phone_number + " " + hours + " " + mins + " " + year + " " + month + " " + day);
        data = new HashMap<>();
        data.put("NUMBER", phone_number);
        data.put("HOUR", hours);
        data.put("MINUTE", mins);

        data.put("DATE", year);
        data.put("MONTH", month);
        data.put("YEAR", day);
        shortdb.insert(data);
    }
}