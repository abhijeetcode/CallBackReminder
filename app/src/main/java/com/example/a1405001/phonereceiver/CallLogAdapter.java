package com.example.a1405001.phonereceiver;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 1405001 on 02-02-2018.
 */

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.MyViewHolder> {

    private List<CallBackDetails> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView number, time, year;

        public MyViewHolder(View view) {
            super(view);

            number = (TextView) view.findViewById(R.id.number_txt);
            time = (TextView) view.findViewById(R.id.time_txt);
            year = (TextView) view.findViewById(R.id.date_txt);
        }
    }

    public CallLogAdapter(List<CallBackDetails> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CallBackDetails callBackDetails = moviesList.get(position);
        holder.number.setText(callBackDetails.getMoblieNumber());
        holder.time.setText(callBackDetails.getTime());
        holder.year.setText(callBackDetails.getDate());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
