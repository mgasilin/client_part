package com.example.test.hubActivityFragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.AdressService.GeoThing;
import com.example.test.R;
import com.example.test.Server.Server;
import com.example.test.model.Event;
import com.example.test.tempThings.ShowCaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment {


    private int state = 0;
    private List<Event> events = new ArrayList<>();
    private List<Event> eventsLen = new ArrayList<>();
    private List<Event> eventsCat = new ArrayList<>();
    private int len = 0;
    double main_lat;
    double main_lng;
    private SeekBar length;
    private CheckBox apply_categories, search;
    private TextView len_text;
    private ShowCaseFragment showCaseFragment;
    private Map<Marker, Long> m = new HashMap<Marker, Long>();
    private GoogleMap map;
    private long id;

    public void update() {
        switch (state) {
            case 1:
                events = eventsLen;
                break;
            case 2:
                events = eventsCat;
                break;
            case 3:
                events = intersectLists(eventsCat, eventsLen);
                break;
            default:
                Toast.makeText(getActivity(), "0 state", Toast.LENGTH_LONG).show();
        }
        Log.d("MAP_EVENTS", events.toString());
        m.clear();
        if (map != null) {
            map.clear();
            for (Event event : events) {
                m.put(map.addMarker(new MarkerOptions().position(new LatLng(event.getX(), event.getY()))), event.getId());
            }
        }
    }

    public void updateLen(List<Event> events, double x, double y, int leng) {
        ArrayList<Event> eventLen = new ArrayList<>();
        for (Event e : events) {
            if (GeoThing.getDistance(new LatLng(x, y), new LatLng(e.getX(), e.getY())) <= leng) {
                eventLen.add(e);
            }
        }
        Log.d("MAP_LEN", eventLen.toString());
        Log.d("MAP_EVL", events.toString());
        this.eventsLen = eventLen;
        update();
    }

    public void updateCat(List<Event> eventsCat) {
        Log.d("MAP_CAT", eventsCat.toString());
        this.eventsCat = eventsCat;
        update();
    }

    private static List<Event> intersectLists(List<Event> a, List<Event> b) {
        List<Event> res = new ArrayList<>();
        for (Event event : b) {
            for (Event event1 : a) {
                if (event.getId() == event1.getId()) {
                    res.add(event);
                    break;
                }
            }
        }
        return res;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            for (Event event : events) {
                m.put(map.addMarker(new MarkerOptions().position(new LatLng(event.getX(), event.getY()))), event.getId());
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(getActivity()
                    .getPreferences(Context.MODE_PRIVATE).getString("start_lat", "57.333")), Double.parseDouble(getActivity().
                    getPreferences(Context.MODE_PRIVATE).getString("start_lng", "37.333")))));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(getActivity()
                    .getPreferences(Context.MODE_PRIVATE).getString("start_lat", "57.333")), Double.parseDouble(getActivity().
                    getPreferences(Context.MODE_PRIVATE).getString("start_lng", "37.333"))), 9.0f));

            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    showCaseFragment = ShowCaseFragment.newInstance(m.get(marker), id);
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.event_showcase_maps, showCaseFragment)
                            .commit();
                    return false;
                }
            });
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
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        apply_categories = view.findViewById(R.id.applyCategories);
        search = view.findViewById(R.id.lengh_search);
        length = view.findViewById(R.id.map_length);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        main_lat = Double.parseDouble(sharedPreferences.getString("start_lat", "57.333"));
        main_lng = Double.parseDouble(sharedPreferences.getString("start_lng", "37.333"));
        length.setMax(50000);
        length.setProgress(sharedPreferences.getInt("length", 0));
        len = sharedPreferences.getInt("length", 0);
        apply_categories.setChecked(sharedPreferences.getBoolean("apply_categories", false));
        search.setChecked(sharedPreferences.getBoolean("length_search", false));
        boolean length_search = search.isChecked();
        boolean apply_cats = apply_categories.isChecked();
        Server.getByCategories(getActivity(), getActivity().getPreferences(0), id, MapFragment.this);
        Server.findByLength(len, getActivity().getPreferences(0), getActivity(), MapFragment.this);
        if (length_search && apply_cats) {
            state = 3;
        } else if (length_search) {
            state = 1;
        } else {
            state = 2;
        }

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                boolean length_search = search.isChecked();
                boolean apply_cats = apply_categories.isChecked();
                List<Event> events1 = null;
                Server.getByCategories(getActivity(), getActivity().getPreferences(0), id, MapFragment.this);
                Server.findByLength(len, getActivity().getPreferences(0), getActivity(), MapFragment.this);
                if (length_search && apply_cats) {
                    state = 3;
                } else if (length_search) {
                    state = 1;
                } else {
                    state = 2;
                }
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("length_search", length_search);
                editor.putBoolean("apply_categories", apply_cats);
                editor.putInt("length", length.getProgress());
                editor.apply();

            }
        };


        search.setOnCheckedChangeListener(listener);
        apply_categories.setOnCheckedChangeListener(listener);

        length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                len = i;
                len_text.setText("Расстояние для поиска: " + len+" м");
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("length", len);
                editor.apply();
                boolean length_search = search.isChecked();
                boolean apply_cats = apply_categories.isChecked();
                Server.getByCategories(getActivity(), getActivity().getPreferences(0), id, MapFragment.this);
                Server.findByLength(len, getActivity().getPreferences(0), getActivity(), MapFragment.this);
                if (length_search && apply_cats) {
                    state = 3;
                } else if (length_search) {
                    state = 1;
                } else {
                    state = 2;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        len_text = view.findViewById(R.id.len);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        len_text.setText("Расстояние для поиска: " + sp.getInt("length", 0)+" м");
        return view;
    }

    public static MapFragment newInstance(long id) {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        args.putLong("id", id);
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