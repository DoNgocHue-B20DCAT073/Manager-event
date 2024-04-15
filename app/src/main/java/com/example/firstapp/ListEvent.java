package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.firstapp.adapter.EventAdapter;
import com.example.firstapp.api.ApiService;
import com.example.firstapp.model.Event;
import com.example.firstapp.model.User;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEvent extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private User user;

    private List<Event> listEvent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);

        recyclerView = findViewById(R.id.recyclerview);
        eventAdapter = new EventAdapter();
        SharedPreferences sharedPreferences = getSharedPreferences("userShared",MODE_PRIVATE);
        String userString = sharedPreferences.getString("user", "");
        Gson gson = new Gson();
        user = gson.fromJson(userString,User.class);
        Log.d("user", userString);
        eventAdapter.setUser(user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(eventAdapter);



        ApiService.apiService.getAllListEvent().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call< List<Event> > call, Response<List<Event>> response) {
                listEvent = response.body();

                eventAdapter.setListEvent(listEvent);

                Toast.makeText(ListEvent.this, listEvent.size()+ "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("loi", t.toString());
                Toast.makeText(ListEvent.this, "ket noi khong on dinh", Toast.LENGTH_SHORT).show();
            }
        });



    }
}