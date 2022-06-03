package com.example.test.hubActivityFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.test.AdressService.GeoThing;
import com.example.test.R;
import com.example.test.model.Comment;
import com.example.test.tempThings.GetPositionFragment;
import com.google.android.gms.maps.model.LatLng;


import java.util.ArrayList;

public class CategoriesFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    public void save(boolean cr1, boolean cr2, boolean cr3, boolean cr4, boolean cr5, boolean cr6, boolean cr7, boolean cr8) {
        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(userId+" cr1", cr1);
        editor.putBoolean(userId+" cr2", cr2);
        editor.putBoolean(userId+" cr3", cr3);
        editor.putBoolean(userId+" cr4", cr4);
        editor.putBoolean(userId+" cr5", cr5);
        editor.putBoolean(userId+" cr6", cr6);
        editor.putBoolean(userId+" cr7", cr7);
        editor.putBoolean(userId+" cr8", cr8);
        editor.commit();
    }

    public ArrayList<Boolean> load() {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        res.add(sharedPreferences.getBoolean(userId+" cr1", false));
        res.add(sharedPreferences.getBoolean(userId+" cr2", false));
        res.add(sharedPreferences.getBoolean(userId+" cr3", false));
        res.add(sharedPreferences.getBoolean(userId+" cr4", false));
        res.add(sharedPreferences.getBoolean(userId+" cr5", false));
        res.add(sharedPreferences.getBoolean(userId+" cr6", false));
        res.add(sharedPreferences.getBoolean(userId+" cr7", false));
        res.add(sharedPreferences.getBoolean(userId+" cr8", false));
        return res;
    }

    private long userId;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance(/*param*/long id) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putLong("ID", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getLong("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        ArrayList<Boolean> list = load();

        boolean cr1 = list.get(0);
        boolean cr2 = list.get(1);
        boolean cr3 = list.get(2);
        boolean cr4 = list.get(3);
        boolean cr5 = list.get(4);
        boolean cr6 = list.get(5);
        boolean cr7 = list.get(6);
        boolean cr8 = list.get(7);


        CheckBox cat1 = view.findViewById(R.id.Cat1);
        CheckBox cat2 = view.findViewById(R.id.Cat2);
        CheckBox cat3 = view.findViewById(R.id.Cat3);
        CheckBox cat4 = view.findViewById(R.id.Cat4);
        CheckBox cat5 = view.findViewById(R.id.Cat5);
        CheckBox cat6 = view.findViewById(R.id.Cat6);
        CheckBox cat7 = view.findViewById(R.id.Cat7);
        CheckBox cat8 = view.findViewById(R.id.Cat8);

        cat1.setChecked(cr1);
        cat2.setChecked(cr2);
        cat3.setChecked(cr3);
        cat4.setChecked(cr4);
        cat5.setChecked(cr5);
        cat6.setChecked(cr6);
        cat7.setChecked(cr7);
        cat8.setChecked(cr8);

        cat1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(0,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(1,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(2,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(3,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(4,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(5,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(6,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });
        cat8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list.set(7,b);
                save(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            }
        });



        sharedPreferences=getActivity().getPreferences(Context.MODE_PRIVATE);
        LatLng pos=new LatLng(Double.parseDouble(sharedPreferences.getString("start_lat", "57.333")),Double.parseDouble(sharedPreferences.getString("start_lng", "37.333")));
        TextView positionInfo = view.findViewById(R.id.pos);
        positionInfo.setText("Текущее место отсчета расстояния - "+ GeoThing.getAddress(pos.latitude,pos.longitude,getActivity()));
        AppCompatButton setPosition = view.findViewById(R.id.setPos);
        setPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, GetPositionFragment.newInstance(pos,userId)).commit();
            }
        });

        return view;
    }
}