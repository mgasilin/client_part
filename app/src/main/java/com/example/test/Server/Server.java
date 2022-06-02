package com.example.test.Server;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.test.hubActivityFragments.HomeFragment;
import com.example.test.hubActivityFragments.MapFragment;
import com.example.test.hubActivityFragments.SearchFragment;
import com.example.test.model.Comment;
import com.example.test.model.Event;
import com.example.test.model.User;
import com.example.test.tempThings.ShowCaseFragment;
import com.example.test.tempThings.ShowCaseMap;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private Server() {
    }

    private static ServerServiceImpl server = new ServerServiceImpl();

    public static void insertEvent(Event e, Context context) {
         server.insertEvent(e, context);
    }

    public static void findByLength(long length, SharedPreferences sharedPreferences, Context context, MapFragment mapFragment) {
        double x = Double.parseDouble(sharedPreferences.getString("start_lat", "57.333"));
        double y = Double.parseDouble(sharedPreferences.getString("start_lng", "37.333"));
        server.findByLength(length, x, y, context, mapFragment);
    }

    public static void getEventById(int id, Context context) {
        server.getById(id, context);
    }

    public static void getEventById(int id, Context context, ShowCaseFragment showCaseFragment) {
        server.getById(id, context,showCaseFragment);
    }

    public static void getEventById(int id, Context context, ShowCaseMap showCaseMap) {
        server.getById(id, context,showCaseMap);
    }

    public static void searchByName(String name, Context context, SearchFragment searchFragment) {
        server.searchByName(name, context, searchFragment);
    }

    public static void getByUserId(int id, Context context, HomeFragment homeFragment) {
        server.getByUserId(id, context, homeFragment);
    }

    public static void getByCategories(Context context, SharedPreferences sharedPreferences, long id, MapFragment mapFragment) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        res.add(sharedPreferences.getBoolean(id + " cr1", false));
        res.add(sharedPreferences.getBoolean(id + " cr2", false));
        res.add(sharedPreferences.getBoolean(id + " cr3", false));
        res.add(sharedPreferences.getBoolean(id + " cr4", false));
        res.add(sharedPreferences.getBoolean(id + " cr5", false));
        res.add(sharedPreferences.getBoolean(id + " cr6", false));
        res.add(sharedPreferences.getBoolean(id + " cr7", false));
        res.add(sharedPreferences.getBoolean(id + " cr8", false));
        server.getByCategories(res.get(0), res.get(1), res.get(2), res.get(3), res.get(4), res.get(5), res.get(6), res.get(7), context,mapFragment);
    }

    public static void getByCategories(Context context, SharedPreferences sharedPreferences, long id,SearchFragment searchFragment) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        res.add(sharedPreferences.getBoolean(id + " cr1", false));
        res.add(sharedPreferences.getBoolean(id + " cr2", false));
        res.add(sharedPreferences.getBoolean(id + " cr3", false));
        res.add(sharedPreferences.getBoolean(id + " cr4", false));
        res.add(sharedPreferences.getBoolean(id + " cr5", false));
        res.add(sharedPreferences.getBoolean(id + " cr6", false));
        res.add(sharedPreferences.getBoolean(id + " cr7", false));
        res.add(sharedPreferences.getBoolean(id + " cr8", false));
        server.getByCategories(res.get(0), res.get(1), res.get(2), res.get(3), res.get(4), res.get(5), res.get(7), res.get(6), context, searchFragment);
    }

    public static void removeEvent(Event event, Context context) {
        server.removeEvent(event, context);
    }

    public static void login(String login, String password, Context context) {
        server.login(login, password, context);
    }

    public static void register(String login, String password, String username, Context context) {
        server.register(login, password, username, context);
    }

    public static User getUserById(int id, Context context) {
        return server.getUserById(id, context);
    }

    public static void findByEvent(Event event, Context context) {
        server.findByEvent(event, context);
    }

    public static void insertComment(Comment comment, Context context) {
        server.insertComment(comment, context);
    }
}
