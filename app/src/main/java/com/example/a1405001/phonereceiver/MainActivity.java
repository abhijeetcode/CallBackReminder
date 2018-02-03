package com.example.a1405001.phonereceiver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shortroid.com.shortroid.ShortRoidDB.ShortRoidDB;

public class MainActivity extends AppCompatActivity {
    private List<CallBackDetails> callList = new ArrayList<>();
    ShortRoidDB shortdb;
    HashMap<String, String> attributes;
    private RecyclerView recyclerView;
    private CallLogAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        attributes = new HashMap<>();
        attributes.put("NUMBER", "TEXT NOT NULL");
        attributes.put("MINUTE", "INT");
        attributes.put("HOUR", "INT");
        attributes.put("DATE", "INT");
        attributes.put("MONTH", "INT");
        attributes.put("YEAR", "INT");

        shortdb = new ShortRoidDB(MainActivity.this, "CallbackDetails", 1, "Miscall", attributes);

        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new CallLogAdapter(callList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            //RecyclerView OnClick Event
            @Override
            public void onClick(View view, int position) {
                CallBackDetails movie = callList.get(position);
                final String t_time, d_date, m_no;
                m_no = movie.getMoblieNumber();
                t_time = movie.getTime();
                d_date = movie.getDate();
                //DialogBox
                Toast.makeText(getApplicationContext(), movie.getMoblieNumber() + " is selected!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Delete from database
                                String query = "DELETE FROM Miscall WHERE NUMBER = '"+m_no+"';";
                                //String query = "update Miscall set NUMBER = 123 where NUMBER = "+m_no+";";
                                boolean b = shortdb.anyQuery(query);
                                Log.d("querydelete ",b+" "+query);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                finish();
                                //dialog.cancel();
                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle(m_no);
                alert.show();
                setContentView(R.layout.activity_main);

                /*Intent myIntent = new Intent(MainActivity.this, ReminderUpdateRemove.class);
                myIntent.putExtra("phoneNumber", m_no);
                myIntent.putExtra("time", t_time);
                myIntent.putExtra("date", d_date);
                //After click go to Another Activity
                startActivity(myIntent);
                */

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        prepareData();
    }

    private void prepareData() {
        String phoneNumber, DD, TT;
        CallBackDetails callBackDetails;
        List<HashMap<String, String>> list;
        String q = "SELECT * FROM Miscall";
        list = shortdb.query(q);

        for (HashMap<String, String> hmap : list) {
            phoneNumber = hmap.get("NUMBER");
            TT = hmap.get("MINUTE") + ":" + hmap.get("HOUR");
            DD = hmap.get("DATE") + "/" + hmap.get("MONTH") + "/" + hmap.get("YEAR");
            Log.v("NewRetriveDataToDB", phoneNumber + " " + TT + " " + DD);
            callBackDetails = new CallBackDetails(phoneNumber, TT, DD);
            callList.add(callBackDetails);
        }
    }
}
