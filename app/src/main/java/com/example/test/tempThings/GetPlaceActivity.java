package com.example.test.tempThings;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.AdressService.GeoThing;
import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.test.databinding.ActivityGetPlaceBinding;

public class GetPlaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private ActivityGetPlaceBinding binding;
    private AppCompatButton finish, back, set;
    private EditText adress;
    private String adr;
    private LatLng coords;
    private boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetPlaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        finish = findViewById(R.id.done);
        back = findViewById(R.id.back);
        set = findViewById(R.id.setAdress);
        adress = findViewById(R.id.adress);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String tmp = adress.getText().toString();
                    LatLng tempC = GeoThing.getLocationFromAddress(getApplicationContext(), tmp);
                    if (tempC != null) {
                        adr = tmp;
                        coords = tempC;
                        done = true;
                    } else {
                        throw new Exception();
                    }
                    if (map!=null){
                        map.clear();
                        map.addMarker(new MarkerOptions().position(coords).title("выбранное место"));
                    }
                } catch (Exception e) {
                    done = false;
                    Toast.makeText(getApplicationContext(), "ошибка с адресом", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (done){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("adress",adr);
                    returnIntent.putExtra("lat",coords.latitude);
                    returnIntent.putExtra("lng",coords.longitude);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                String tmp;
                try {
                    tmp = GeoThing.getAddress(latLng.latitude, latLng.longitude, getApplicationContext());
                    if (!tmp.isEmpty()){
                        map.clear();
                        map.addMarker(new MarkerOptions().position(latLng).title("выбранное место"));
                        done=true;
                        coords = latLng;
                        adress.setText(tmp);
                        adr=tmp;
                    }
                }catch (Exception e){
                    done=false;
                    Toast.makeText(getApplicationContext(), "ошибка при обработке координат", Toast.LENGTH_SHORT).show();
                }
            }
        });
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(55.7, 37.6)));
    }
}