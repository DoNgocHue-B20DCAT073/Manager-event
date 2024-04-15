package com.example.firstapp.api;


import com.example.firstapp.ListEvent;
import com.example.firstapp.model.Event;
import com.example.firstapp.model.EventJob;
import com.example.firstapp.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8080/")
            .addConverterFactory( GsonConverterFactory.create(gson) )
            .build()
            .create(ApiService.class);

    @POST("user/login")
    Call<User> loginUser(@Body User user);

    @GET("event/get-all-events")
    Call<List<Event>> getAllListEvent();

    @GET("event-job/{id}")
    Call<List<EventJob>> getAllListEventJob(@Path("id") int id);

    @POST("event-job/save")
    Call<EventJob> saveEventJob(@Body EventJob eventJob);

    @GET("event-job/delete-{id}")
    Call<Void> deleteEventJob(@Path("id") int id);

    @POST("event-job/update")
    Call<EventJob> updateEventJob(@Body EventJob eventJob);
}
