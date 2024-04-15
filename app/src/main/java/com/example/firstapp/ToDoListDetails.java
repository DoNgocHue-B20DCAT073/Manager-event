package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.firstapp.model.EventJob;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public class ToDoListDetails extends AppCompatActivity {

    private TextView date_item_event, time_item_event, name_item_event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_details);
        date_item_event = findViewById(R.id.date_item_event);
        time_item_event = findViewById(R.id.time_item_event);
        name_item_event = findViewById(R.id.name_item_event);
        Intent intent = getIntent();
        String eventJob = intent.getStringExtra("details");
        Gson gson = new Gson();
        EventJob eventJob1 = gson.fromJson(eventJob,EventJob.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String a = simpleDateFormat.format(eventJob1.getDeadline());
        String []b = a.split(" ");
        date_item_event.setText(b[0]);
        time_item_event.setText(b[1]);
        name_item_event.setText(eventJob1.getName());
    }
}