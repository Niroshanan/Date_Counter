package com.example.datecounter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datecounter.R;
import com.example.datecounter.listener.EventListClickListener;
import com.example.datecounter.model.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ListItemHolder> {

    private EventModel[] mDataset;
    private EventListClickListener mListener;
    private Date today;

    public EventListAdapter(EventModel[] dataSet,EventListClickListener mListener){
        this.mDataset = dataSet;
        this.mListener = mListener;
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView dueDateTextView;

        public ListItemHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            this.titleTextView = (TextView)view.findViewById(R.id.title_text_view);
            this.dateTextView = (TextView)view.findViewById(R.id.date_text_view);
            this.dueDateTextView =(TextView)view.findViewById(R.id.due_days_text_view);

        }
    }
    @NonNull
    @Override
    public EventListAdapter.ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout =(LinearLayout) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_item,parent,false);
        ListItemHolder holder = new ListItemHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {

        today = new Date();
        EventModel eventModel = mDataset[position];
        holder.titleTextView.setText(eventModel.getEventTitle());
        holder.dateTextView.setText("End Date " + eventModel.getEventDate());
        String myFormat="MM/dd/yyyy hh:mm:ss";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        Date dueDate = null;
        try {
            dueDate = dateFormat.parse(eventModel.getEventDate() + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = dueDate.getTime() - (today.getTime() - 5000);
        int differenceDates = (int) (difference / (24 * 60 * 60 * 1000));
        String dayDifference = Long.toString(differenceDates);
        String days = " days more";
        switch (dayDifference){
            case "0":
                days = " TODAY";
                dayDifference="";
                break;
            case "1":
                days = " day more";
                break;
            default:
                break;
        }
        holder.dueDateTextView.setText(dayDifference + days );
        if(difference < 0){
            holder.dueDateTextView.setText("Expired");
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventModel selectedEvent = mDataset[position];
                if (mListener != null) {
                    mListener.onCountryClick(selectedEvent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
