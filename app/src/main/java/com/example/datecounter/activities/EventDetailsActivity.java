package com.example.datecounter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.datecounter.R;
import com.example.datecounter.database.DbHandler;
import com.example.datecounter.model.EventModel;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView titleTextView,dateTextView;
    private Button deleteButton;
    private EventModel event;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        dbHandler = new DbHandler(EventDetailsActivity.this);

        //Receive passed data and store into an instance variable.
        EventModel eventModel = (EventModel) getIntent().getExtras().get(MainActivity.EVENT_DATA_EXTRA);
        this.event = eventModel;
        setTitle(eventModel.getEventTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get references to existing UI elements and update data.
        titleTextView = findViewById(R.id.details_title_text_view);
        dateTextView = findViewById(R.id.details_date_text_view);
        deleteButton = findViewById(R.id.details_delete_button);

        //Set UI Element Values

        titleTextView.setText(event.getEventTitle());
        dateTextView.setText(event.getEventDate());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = dbHandler.removeData(event);
                if(i > 0){
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}