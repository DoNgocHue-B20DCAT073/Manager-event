package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class ToDoList extends AppCompatActivity {
    private ImageView btnMenu, btnAddWork, btnPickDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        btnMenu = findViewById(R.id.btn_menu);
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

    }

    // tao bottom sheet dialog
    private void createDialog(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.type_work , null);
        dialog.setContentView(view);
        btnPickDate  = view.findViewById(R.id.date);
        btnPickDate.setOnClickListener(v1 -> {
            DatePickerDialog pickdate = new DatePickerDialog(this);
            Calendar calendar = Calendar.getInstance();
            pickdate.updateDate(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_YEAR));
            pickdate.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                }
            });
            pickdate.show();
        });
        dialog.show();
    }
}