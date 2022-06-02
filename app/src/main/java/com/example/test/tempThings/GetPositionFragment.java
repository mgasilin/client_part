package com.example.test.tempThings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.AdressService.GeoThing;
import com.example.test.R;
import com.example.test.hubActivityFragments.CategoriesFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GetPositionFragment extends Fragment {

    private long id;
    private AppCompatButton back, done;
    private LatLng start_pos;
    private LatLng pos;
    private GoogleMap map;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos=start_pos;
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("start_lat",""+pos.latitude);
                    editor.putString("start_lng",""+pos.longitude);
                    editor.apply();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, CategoriesFragment.newInstance(id)).commit();
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("start_lat",""+pos.latitude);
                    editor.putString("start_lng",""+pos.longitude);
                    editor.apply();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, CategoriesFragment.newInstance(id)).commit();
                }
            });
            map = googleMap;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 9.0f));
            map.addMarker(new MarkerOptions().position(pos).title(GeoThing.getAddress(pos.latitude, pos.longitude, getActivity())));
            map.moveCamera(CameraUpdateFactory.newLatLng(pos));
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    map.clear();
                    pos = latLng;
                    map.addMarker(new MarkerOptions().position(pos).title(GeoThing.getAddress(pos.latitude, pos.longitude, getActivity())));
                    map.moveCamera(CameraUpdateFactory.newLatLng(pos));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 9.0f));
                    SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putString("start_lat",""+pos.latitude);
                    editor.putString("start_lng",""+pos.longitude);
                    editor.apply();
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_position, container, false);
        back=view.findViewById(R.id.backGetPosFr);
        done=view.findViewById(R.id.setGetPosFr);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id=getArguments().getLong("id");
            pos = new LatLng(getArguments().getDouble("lat"), getArguments().getDouble("lng"));
            start_pos = new LatLng(getArguments().getDouble("lat"), getArguments().getDouble("lng"));
        }else {
            id=0;
            pos=new LatLng(57.7,37.7);
            start_pos = new LatLng(57.7,37.7);
        }

    }

    public static GetPositionFragment newInstance(LatLng pos, long id) {
        Bundle args = new Bundle();
        args.putDouble("lat", pos.latitude);
        args.putDouble("lng", pos.longitude);
        args.putLong("id",id);
        GetPositionFragment fragment = new GetPositionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}