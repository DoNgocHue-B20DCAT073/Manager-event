package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.firstapp.databinding.ActivityMapsBinding;
import com.example.firstapp.model.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public class InformationEvent extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ActivityMapsBinding binding;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private ImageView btnMenu;

    private Event event;

    private TextView name_event, begin_time, end_time, descripton_event, address_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_event);
        Intent intent = getIntent();
        //lấy event vừa click
        String eventString =intent.getStringExtra("event");
        boolean check = intent.getBooleanExtra("check_own", false);
        btnMenu = findViewById(R.id.btn_menu);
        if (!check){
            btnMenu.setVisibility(View.INVISIBLE);
        }




        //chuyển event json thành object
        Gson gson = new Gson();
        event = gson.fromJson(eventString,Event.class);


        name_event = findViewById(R.id.name_event);
        begin_time = findViewById(R.id.begin_time);
        end_time = findViewById(R.id.end_time);
        descripton_event = findViewById(R.id.description_event);
        address_event = findViewById(R.id.address_event);

        name_event.setText(event.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:00 dd/MM/yyyy");
        begin_time.setText(simpleDateFormat.format(event.getBeginTime()));
        end_time.setText(simpleDateFormat.format(event.getEndTime()));
        descripton_event.setText(event.getDescription());
        address_event.setText(event.getAddressOrganization().getName());

        btnMenu.setOnClickListener((view)->{
            PopupMenu popupMenu = new PopupMenu(this, view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_information_event, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    //khi click vào menu chọn Danh sách công việc
                    if(id == R.id.work_list){
                        Intent intent1 = new Intent(InformationEvent.this,ToDoList.class);
                        intent1.putExtra("id_event",event.getId());
                        startActivity(intent1);
                    }
                    return false;
                }
            });
            popupMenu.show();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        Log.d("ox", event.getAddressOrganization().getOx()+"");
        LatLng sydney = new LatLng(event.getAddressOrganization().getOx(), event.getAddressOrganization().getOy());
        mMap.addMarker(new MarkerOptions().position(sydney).title(event.getAddressOrganization().getName()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
    }


}