package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstapp.api.ApiService;
import com.example.firstapp.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private AppCompatButton btnLogin;
    private EditText userName;
            private TextInputEditText passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login);
        userName = findViewById(R.id.user_name);
        passWord = findViewById(R.id.pass_word);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUserName(userName.getText().toString());
                user.setPassWord(passWord.getText().toString());
                ApiService.apiService.loginUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User userResponse = response.body();

                        if (userResponse != null){
                            SharedPreferences sharedPreferences = getSharedPreferences("userShared" ,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            editor.putString("user", gson.toJson(userResponse));
                            editor.commit();
                            Log.d("userRe", userResponse.toString());
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Login.this, "Ket noi that bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}