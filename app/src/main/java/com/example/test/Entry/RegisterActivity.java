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

import com.example.test.R;
import com.example.test.Server.Server;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginRes;
    private TextView passwordRes;
    private TextView userNameRes;


    public void update(int registerResult) {
        if (registerResult == 3) {
            loginRes.setVisibility(View.VISIBLE);
            loginRes.setTextColor(Color.RED);
            loginRes.setText("Логин: уже занят");
            passwordRes.setText("Пароль: ");
            userNameRes.setVisibility(View.GONE);
            passwordRes.setVisibility(View.GONE);
            userNameRes.setText("Имя пользователя: ");
            passwordRes.setTextColor(Color.BLACK);
            userNameRes.setTextColor(Color.BLACK);
        } else {
            if (registerResult == 1) {
                loginRes.setText("Логин: ");
                loginRes.setVisibility(View.GONE);
                passwordRes.setVisibility(View.GONE);
                userNameRes.setVisibility(View.GONE);
                passwordRes.setText("Пароль: ");
                userNameRes.setText("Имя пользователя: ");
                loginRes.setTextColor(Color.BLACK);
                passwordRes.setTextColor(Color.BLACK);
                userNameRes.setTextColor(Color.BLACK);
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            } else if (registerResult == 0) {
                Toast.makeText(getApplicationContext(), "Ошибка при регистрации", Toast.LENGTH_SHORT).show();
            } else if(registerResult==2){
                loginRes.setText("Логин: ");
                loginRes.setVisibility(View.GONE);
                passwordRes.setVisibility(View.GONE);
                userNameRes.setVisibility(View.VISIBLE);
                passwordRes.setText("Пароль: ");
                loginRes.setTextColor(Color.BLACK);
                passwordRes.setTextColor(Color.BLACK);
                userNameRes.setTextColor(Color.RED);
                userNameRes.setText("Имя пользователя: уже занято");
            }else{
                loginRes.setVisibility(View.VISIBLE);
                loginRes.setTextColor(Color.RED);
                loginRes.setText("Логин: уже занят");
                passwordRes.setVisibility(View.GONE);
                userNameRes.setVisibility(View.VISIBLE);
                passwordRes.setText("Пароль: ");
                passwordRes.setTextColor(Color.BLACK);
                userNameRes.setTextColor(Color.RED);
                userNameRes.setText("Имя пользователя: уже занято");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText login = findViewById(R.id.regLogin);
        EditText password = findViewById(R.id.regPassword);
        EditText userName = findViewById(R.id.regName);
        loginRes = findViewById(R.id.register_tv);
        passwordRes = findViewById(R.id.register_tv2);
        userNameRes = findViewById(R.id.register_tv3);
        AppCompatButton btn = findViewById(R.id.register);
        passwordRes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
        loginRes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
        userNameRes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String insertedLogin = login.getText().toString(), insertedPassword = password.getText().toString(), insertedName = userName.getText().toString();
                if (!insertedLogin.isEmpty() && !insertedPassword.isEmpty() && !insertedName.isEmpty()) {
                    loginRes.setText("Логин: ");
                    loginRes.setVisibility(View.GONE);
                    passwordRes.setVisibility(View.GONE);
                    userNameRes.setVisibility(View.GONE);
                    passwordRes.setText("Пароль: ");
                    userNameRes.setText("Имя пользователя: ");
                    loginRes.setTextColor(Color.BLACK);
                    passwordRes.setTextColor(Color.BLACK);
                    userNameRes.setTextColor(Color.BLACK);
                    Server.register(insertedLogin, insertedPassword, insertedName, RegisterActivity.this);
                } else {
                    userNameRes.setVisibility(View.GONE);
                    userNameRes.setVisibility(View.GONE);
                    userNameRes.setVisibility(View.GONE);
                    loginRes.setText("Логин: ");
                    passwordRes.setText("Пароль: ");
                    userNameRes.setText("Имя пользователя: ");
                    loginRes.setTextColor(Color.BLACK);
                    passwordRes.setTextColor(Color.BLACK);
                    userNameRes.setTextColor(Color.BLACK);
                    if (login.getText().toString().isEmpty()) {
                        loginRes.setTextColor(Color.RED);
                        loginRes.setVisibility(View.VISIBLE);
                        loginRes.setText("Логин: не введен логин");
                    }
                    if (password.getText().toString().isEmpty()) {
                        passwordRes.setTextColor(Color.RED);
                        passwordRes.setVisibility(View.VISIBLE);
                        passwordRes.setText("Пароль: пустой пароль");
                    }
                    if (userName.getText().toString().isEmpty()) {
                        userNameRes.setTextColor(Color.RED);
                        userNameRes.setVisibility(View.VISIBLE);
                        userNameRes.setText("Имя пользователя: не введено такое имя");
                    }
                }
            }
        });
    }

}