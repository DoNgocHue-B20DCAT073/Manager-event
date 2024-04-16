package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TimePicker;

import com.example.firstapp.adapter.EventJobAdapter;
import com.example.firstapp.api.ApiService;
import com.example.firstapp.model.Event;
import com.example.firstapp.model.EventJob;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoList extends AppCompatActivity {
    private ImageView btnMenu, btnAddWork, btnPickDate, btnPickTime, btnSend;
    private AppCompatButton btnAll, btnSoon, btnFinish;

    private RecyclerView recyclerView;

    private EventJobAdapter eventJobAdapter;

    private String date, time;

    private EditText typeWork;

    private int idEvent;

    private List<EventJob> listEventJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        btnMenu = findViewById(R.id.btn_menu);
        btnAll = findViewById(R.id.all);
        btnSoon = findViewById(R.id.soon);
        btnFinish = findViewById(R.id.finish);

        eventJobAdapter = new EventJobAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.show_list_work);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(eventJobAdapter);

        //lấy id event
        Intent intent = getIntent();
        idEvent = intent.getIntExtra("id_event", 0);

        ApiService.apiService.getAllListEventJob(idEvent).enqueue(new Callback<List<EventJob>>() {
            @Override
            public void onResponse(Call<List<EventJob>> call, Response<List<EventJob>> response) {
                listEventJob =  response.body();
                eventJobAdapter.setListEventJob(listEventJob);

            }

            @Override
            public void onFailure(Call<List<EventJob>> call, Throwable t) {

            }
        });

        btnMenu.setOnClickListener((view)->{
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_to_do_list,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
            popupMenu.show();
        });
        btnAddWork = findViewById(R.id.add_work);

        btnAddWork.setOnClickListener(v ->{
            createDialog(v); // tao bottom sheet dialog
        });

        btnAll.setBackgroundDrawable(getDrawable(R.drawable.custom_fucntion));


        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColor();
                eventJobAdapter.setListEventJob(listEventJob);
                btnAll.setBackgroundDrawable(getDrawable(R.drawable.custom_fucntion));
            }
        });

        btnSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColor();
                eventJobAdapter.setListEventJob(listEventJob.stream().filter(item -> item.getStatus() == 0).collect(Collectors.toList()));
                btnSoon.setBackgroundDrawable(getDrawable(R.drawable.custom_fucntion));
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColor();
                eventJobAdapter.setListEventJob(listEventJob.stream().filter(item -> item.getStatus() == 1).collect(Collectors.toList()));
                btnFinish.setBackgroundDrawable(getDrawable(R.drawable.custom_fucntion));
            }
        });

    }

    void resetColor(){
        btnAll.setBackgroundDrawable(getDrawable(R.drawable.custom_to_do_list_1_button));
        btnSoon.setBackgroundDrawable(getDrawable(R.drawable.custom_to_do_list_1_button));
        btnFinish.setBackgroundDrawable(getDrawable(R.drawable.custom_to_do_list_1_button));

    }

    // tao bottom sheet dialog
    private void createDialog(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.type_work , null);
        dialog.setContentView(view);

        typeWork = view.findViewById(R.id.editText);

        btnPickDate  = view.findViewById(R.id.date);
        btnPickDate.setOnClickListener(v1 -> {
            DatePickerDialog pickdate = new DatePickerDialog(this);
            Calendar calendar = Calendar.getInstance();
            pickdate.updateDate(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH)   , calendar.get(Calendar.DAY_OF_MONTH));
            pickdate.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = dayOfMonth +"/"+ month +"/"+ year ;

                }
            });
            pickdate.show();
        });

        btnPickTime = view.findViewById(R.id.time);
        btnPickTime.setOnClickListener(v2 -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int mn = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            time = hourOfDay + ":" + minute;
                        }
                    },14,55,true);
            timePickerDialog.show();
        });
        btnSend = view.findViewById(R.id.send);
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventJob eventJob = new EventJob();
                eventJob.setStatus(0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String connect = date + " " +time;
                try {
                    Date date1 = simpleDateFormat.parse(connect);
                    eventJob.setDeadline(date1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                eventJob.setName(typeWork.getText().toString());
                Event event = new Event();
                event.setId(idEvent);
                eventJob.setEvent(event);
                ApiService.apiService.saveEventJob(eventJob).enqueue(new Callback<EventJob>() {
                    @Override
                    public void onResponse(Call<EventJob> call, Response<EventJob> response) {
                        EventJob eventJob1 = response.body();
                        listEventJob.add(eventJob1);
                        eventJobAdapter.setListEventJob(listEventJob);
                    }

                    @Override
                    public void onFailure(Call<EventJob> call, Throwable t) {
                        Log.d("loi",t.toString());
                    }
                });

                dialog.dismiss();
            }
        });
        dialog.show();
    }

}