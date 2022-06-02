package com.example.test.tempThings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowCaseMap extends Fragment {
    public static ShowCaseMap newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong("id", id);
        ShowCaseMap fragment = new ShowCaseMap();
        fragment.setArguments(args);
        return fragment;
    }

    public void update(Event event){
        showEvent=event;
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.show_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private long id;
    private Event showEvent;
    private GoogleMap map;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(showEvent.getX(), showEvent.getY())));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(showEvent.getX(), showEvent.getY()), 9.0f));
            map.addMarker(new MarkerOptions().position(new LatLng(showEvent.getX(), showEvent.getY())).title(showEvent.getName()).snippet(showEvent.getDescription()));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
            map.setMyLocationEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_case_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getLong("id");
        showEvent = null;
        Server.getEventById((int) id, getActivity(),ShowCaseMap.this);
    }


}