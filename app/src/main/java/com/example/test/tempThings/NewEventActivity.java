package com.example.test.tempThings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.AdressService.GeoThing;
import com.example.test.HubActivity;
import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.google.android.gms.maps.model.LatLng;

import java.util.function.LongFunction;


public class NewEventActivity extends AppCompatActivity {
    private long id;
    final String[] place = {""};
    final double[] x = {0};
    final double[] y = {0};
    final boolean[] isOk = {false};
    private TextView adressText;


    private boolean checkData(String data) {
        if (data.length() != 8) {
            return false;
        } else {
            for (int i = 0; i < 8; i++) {
                int c = data.charAt(i) - '0';
                if (c > 9 || c < 0) {
                    return false;
                }
            }
            int day = (data.charAt(0) - '0') * 10 + (data.charAt(1) - '0');
            int month = (data.charAt(2) - '0') * 10 + (data.charAt(3) - '0');
            int year = (data.charAt(4) - '0') * 1000 + (data.charAt(5) - '0') * 100 + (data.charAt(6) - '0') * 10 + (data.charAt(7) - '0');
            if (month > 12 || year < 2000 || year > 2050 || day > 31) {
                Toast.makeText(NewEventActivity.this, "неверная дата", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        id = getIntent().getExtras().getLong("id");
        adressText = findViewById(R.id.getAdress);

        EditText date = findViewById(R.id.getDate);
        EditText name = findViewById(R.id.getName);
        EditText description = findViewById(R.id.getDescription);

        CheckBox a1 = findViewById(R.id.isStreet);
        CheckBox b1 = findViewById(R.id.isGroup);
        CheckBox c1 = findViewById(R.id.isFamily);
        CheckBox d1 = findViewById(R.id.isFree);
        CheckBox h1 = findViewById(R.id.hasCovid);
        CheckBox e2 = findViewById(R.id.hasRegister);
        CheckBox f1 = findViewById(R.id.isSport);
        CheckBox g1 = findViewById(R.id.hasAgeRestrictions);

        AppCompatButton setPos=findViewById(R.id.startAdressActivity);
        setPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewEventActivity.this,GetPlaceActivity.class);
                startActivityForResult(i,1);
            }
        });
        AppCompatButton back = findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewEventActivity.this, HubActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        AppCompatButton btn = findViewById(R.id.checkNewEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkData(date.getText().toString())) {
                    if (!(name.getText().toString().isEmpty() || description.getText().toString().isEmpty() ||
                            place[0].isEmpty() || date.getText().toString().isEmpty() || !isOk[0])) {
                        boolean a = a1.isChecked();
                        boolean h = h1.isChecked();
                        boolean b = b1.isChecked();
                        boolean c = c1.isChecked();
                        boolean d = d1.isChecked();
                        boolean e1 = e2.isChecked();
                        boolean f = f1.isChecked();
                        boolean g = g1.isChecked();
                        name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        description.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        date.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        Event e = new Event(date.getText().toString(), id, name.getText().toString(), description.getText().toString(),
                                place[0], x[0], y[0], a, b, c, d, h, e1, f, g);
                        Server.insertEvent(e,getApplicationContext());
                        Intent i = new Intent(NewEventActivity.this, HubActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    } else {
                        date.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (name.getText().toString().isEmpty()){
                            name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
                        }
                        if (!isOk[0]){
                            Toast.makeText(getApplicationContext(), "Не задан адрес", Toast.LENGTH_SHORT).show();
                            setPos.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_exlaim_mark,0);
                        }else{
                            setPos.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        }
                        if (description.getText().toString().isEmpty()){
                            description.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
                        }
                    }
                } else {
                    date.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_exlaim_mark, 0);
                    Toast.makeText(NewEventActivity.this, "Неверно введена дата: введте ее в формате ддммгггг", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                double lat1 = data.getDoubleExtra("lat", 1000);
                double lng1 = data.getDoubleExtra("lng", 1000);
                String adress = data.getStringExtra("adress");
                if (lng1 != 1000 && lat1 != 1000 && adress!=null) {
                    x[0]=lat1;
                    y[0]=lng1;
                    place[0]=adress;
                    isOk[0]=true;
                    adressText.setText(place[0]);
                }else {
                    Toast.makeText(getApplicationContext(), "ошибка при передаче, попробуйте снова" , Toast.LENGTH_SHORT).show();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}