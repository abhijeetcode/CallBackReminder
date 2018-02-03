package com.example.a1405001.phonereceiver;

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
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CallBackDetails movie = callList.get(position);
                //After click go to Another Activity
                Toast.makeText(getApplicationContext(), movie.getMoblieNumber() + " is selected!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        prepareData();
    }
    private void prepareData() {
        String phoneNumber, Date, Time;
        CallBackDetails callBackDetails;
        List<HashMap<String, String>> list;
        String q = "SELECT * FROM Miscall";
        list = shortdb.query(q);

        for (HashMap<String, String> hmap : list) {
            phoneNumber = hmap.get("NUMBER");
            Time = hmap.get("MINUTE") + ":" + hmap.get("HOUR");
            Date = hmap.get("DATE") + "/" + hmap.get("MONTH") + "/" + hmap.get("YEAR");
            //Log.v("Sara Values print ", " " + m + " " + h + " " + d + " " + mo + " " + y + " " + numbercaller);
            callBackDetails = new CallBackDetails(phoneNumber,Time,Date);
            callList.add(callBackDetails);
        }
    }
}
