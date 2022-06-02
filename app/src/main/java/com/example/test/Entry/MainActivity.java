package com.example.test.Entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.HubActivity;
import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.Server.ServerServiceImpl;

public class MainActivity extends AppCompatActivity {
    private TextView loginRes ;
    private TextView passwordRes ;


    public void update(int loginResult,int id){
        if (loginResult == 1) {
            loginRes.setVisibility(View.GONE);
            passwordRes.setVisibility(View.GONE);
            passwordRes.setTextColor(Color.BLACK);
            passwordRes.setText("Пароль:");
            loginRes.setTextColor(Color.BLACK);
            loginRes.setText("Логин:");
            Intent i = new Intent(MainActivity.this, HubActivity.class);
            i.putExtra("id", (long)id);
            startActivity(i);
        } else if (loginResult == 2) {
            loginRes.setVisibility(View.GONE);
            passwordRes.setVisibility(View.VISIBLE);
            passwordRes.setTextColor(Color.RED);
            passwordRes.setText("Пароль: неверный");
            loginRes.setTextColor(Color.BLACK);
            loginRes.setText("Логин:");
        }else if(loginResult==0){
            Toast.makeText(getApplicationContext(), "Ошибка при входе. Попробуйте позже.", Toast.LENGTH_SHORT).show();
        } else {
            loginRes.setVisibility(View.VISIBLE);
            passwordRes.setVisibility(View.GONE);
            passwordRes.setTextColor(Color.BLACK);
            passwordRes.setText("Пароль:");
            loginRes.setText("Логин: отсутствует в списке");
            loginRes.setTextColor(Color.RED);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginRes = findViewById(R.id.entry_tv1);
        passwordRes = findViewById(R.id.entry_tv2);

        EditText loginView = findViewById(R.id.login);
        EditText passwordView = findViewById(R.id.password);
        AppCompatButton login_button = findViewById(R.id.login_button);
        AppCompatButton register_button = findViewById(R.id.registerButton);

        passwordRes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
        loginRes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String login, password;
                login = loginView.getText().toString();
                password = passwordView.getText().toString();
                if (login.isEmpty() || password.isEmpty()) {
                    loginRes.setVisibility(View.GONE);
                    passwordRes.setVisibility(View.GONE);
                    if (password.isEmpty()) {
                        passwordRes.setVisibility(View.VISIBLE);
                        passwordRes.setTextColor(Color.RED);
                        passwordRes.setText("Пароль: пустой");
                    } else {
                        passwordRes.setVisibility(View.GONE);
                        passwordRes.setTextColor(Color.BLACK);
                        passwordRes.setText("Пароль:");
                    }
                    if (login.isEmpty()) {
                        loginRes.setVisibility(View.VISIBLE);
                        loginRes.setTextColor(Color.RED);
                        loginRes.setText("Логин: пустой");
                    } else {
                        loginRes.setVisibility(View.GONE);
                        loginRes.setTextColor(Color.BLACK);
                        loginRes.setText("Логин:");
                    }
                } else {
                    Server.login(login, password, MainActivity.this);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
    }
}