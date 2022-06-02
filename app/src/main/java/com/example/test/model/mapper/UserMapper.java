package com.example. test.model.mapper;

import android.util.Log;

import com.example.test.model.Event;
import com.example.test.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserMapper {
    public User userFromJsonArray(JSONObject jsonObject){
        User user = null;
        try {
            user = new User(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name")
                    );
        } catch (JSONException e) {
            Log.e("WTF","Error in userMapper: "+e.getMessage());
            e.printStackTrace();
        }

        return user;
    }
}
