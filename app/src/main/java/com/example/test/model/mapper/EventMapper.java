package com.example.test.model.mapper;

import com.example.test.model.Event;

import org.json.JSONException;
import org.json.JSONObject;

public class EventMapper {

    public Event eventFromJsonArray(JSONObject jsonObject, int user_id){
        Event event = null;

        try {
            event = new Event(
                    jsonObject.getString("date"),
                    jsonObject.getInt("id"),
                    user_id,
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("place"),
                    jsonObject.getDouble("coordX"),
                    jsonObject.getDouble("coordY"),
                    jsonObject.getInt("isStreet")>0,
                    jsonObject.getInt("isGroup")>0,
                    jsonObject.getInt("isFamily")>0,
                    jsonObject.getInt("isFree")>0,
                    jsonObject.getInt("hasCovid")>0,
                    jsonObject.getInt("hasRegister")>0,
                    jsonObject.getInt("isSport")>0,
                    jsonObject.getInt("hasAgeRestrictions")>0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }


    public Event eventFromJsonArray(JSONObject jsonObject, int user_id, String user_name){
        Event event = null;

        try {
            event = new Event(
                    jsonObject.getString("date"),
                    jsonObject.getInt("id"),
                    user_id,
                    jsonObject.getString("name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("place"),
                    jsonObject.getDouble("coordX"),
                    jsonObject.getDouble("coordY"),
                    jsonObject.getInt("isStreet")>0,
                    jsonObject.getInt("isGroup")>0,
                    jsonObject.getInt("isFamily")>0,
                    jsonObject.getInt("isFree")>0,
                    jsonObject.getInt("hasCovid")>0,
                    jsonObject.getInt("hasRegister")>0,
                    jsonObject.getInt("isSport")>0,
                    jsonObject.getInt("hasAgeRestrictions")>0,
                    user_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }

}
