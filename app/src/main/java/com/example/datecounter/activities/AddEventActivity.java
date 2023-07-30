package com.example.datecounter.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datecounter.database.DbHandler;
import com.example.datecounter.model.EventModel;
import com.example.datecounter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText dateEditText;
    Button buttonOk;
    Calendar myCalendar;
    Date today;
    String pickedDate;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        titleEditText = findViewById(R.id.title_edit_view);
        dateEditText = findViewById(R.id.date_edit_view);
        buttonOk = findViewById(R.id.ok_button);
        dbHandler = new DbHandler(AddEventActivity.this);

        EventModel eventModel =  new EventModel();

        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.getDefault());
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickedDate = dateFormat.format(myCalendar.getTime());
                eventModel.setEventTitle(titleEditText.getText().toString());
                eventModel.setEventDate(pickedDate);
                if(!eventModel.getEventTitle().isEmpty()){
                    dbHandler.insertData(eventModel);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else{
                    Toast.makeText(AddEventActivity.this,getString(R.string.empty_event),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        myCalendar = Calendar.getInstance();
        today = new Date();
        dateEditText.setText(dateFormat.format(today.getTime()));

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                dateEditText.setText(dateFormat.format(myCalendar.getTime()));

            }
        };
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity
                        .this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(today.getTime());
                datePickerDialog.show();
            }
        });
    }

}