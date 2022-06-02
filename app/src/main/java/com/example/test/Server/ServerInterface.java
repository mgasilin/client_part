package com.example.test.Server;

import android.content.Context;

import com.example.test.hubActivityFragments.HomeFragment;
import com.example.test.hubActivityFragments.MapFragment;
import com.example.test.hubActivityFragments.SearchFragment;
import com.example.test.model.Comment;
import com.example.test.model.Event;
import com.example.test.model.User;
import com.example.test.tempThings.ShowCaseFragment;
import com.example.test.tempThings.ShowCaseMap;

import java.util.List;

public interface ServerInterface {
    void insertEvent(Event e, Context context);

    void getById(int id, Context context);

    void getById(int id, Context context, ShowCaseFragment showCaseFragment);

    void getById(int id, Context context, ShowCaseMap showCaseMap);

    void searchByName(String name, Context context, SearchFragment searchFragment);

    void getByUserId(int id, Context context, HomeFragment homeFragment);

   void getByCategories(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, Context context, MapFragment mapFragment);

   void getByCategories(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, Context context,SearchFragment searchFragment);

    void removeEvent(Event event, Context context);

    void login(String login, String password, Context context);

    void findByLength(long length, double x, double y, Context context, MapFragment mapFragment);

    void register(String login, String password, String username, Context context);

    User getUserById(int id, Context context);

    void findByEvent(Event event, Context context);

    void insertComment(Comment comment, Context context);

}
