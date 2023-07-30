package com.example.datecounter.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.datecounter.R;
import com.example.datecounter.adapter.EventListAdapter;
import com.example.datecounter.database.DbHandler;
import com.example.datecounter.listener.EventListClickListener;
import com.example.datecounter.model.EventModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_EVENT = 1;
    public static final String EVENT_DATA_EXTRA = "EVENT_DATA_EXTRA";
    public static final int EVENT_DETAILS = 2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addButton;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton =(Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(MainActivity.this, AddEventActivity.class);
                startActivityForResult(intent,ADD_EVENT);
            }
        });

        //refer to recycler view instance
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        // Fetch data from database
        dbHandler =  new DbHandler(MainActivity.this);
        loadData();

    }

    private void loadData() {
        EventModel[] dataSet = dbHandler.retrieveData();
        mAdapter = new EventListAdapter(dataSet, new EventListClickListener() {
            @Override
            public void onCountryClick(EventModel eventModel) {
                Intent intent = new Intent(MainActivity.this,
                        EventDetailsActivity.class);
                intent.putExtra(EVENT_DATA_EXTRA, eventModel);
                startActivityForResult(intent, EVENT_DETAILS);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_EVENT && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this,getString(R.string.event_added),Toast.LENGTH_SHORT).show();
            loadData();
        }
        else if(requestCode == EVENT_DETAILS && resultCode == RESULT_OK){
            Toast.makeText(MainActivity.this,getString(R.string.event_deleted),Toast.LENGTH_SHORT).show();
            loadData();
        }
    }
}