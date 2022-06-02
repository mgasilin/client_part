package com.example.test.Server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test.AdressService.GeoThing;
import com.example.test.Entry.MainActivity;
import com.example.test.Entry.RegisterActivity;
import com.example.test.hubActivityFragments.HomeFragment;
import com.example.test.hubActivityFragments.MapFragment;
import com.example.test.hubActivityFragments.SearchFragment;
import com.example.test.model.Comment;
import com.example.test.model.Event;
import com.example.test.model.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.model.mapper.CommentMapper;
import com.example.test.model.mapper.EventMapper;
import com.example.test.model.mapper.UserMapper;
import com.example.test.tempThings.EventShowCaseActivity;
import com.example.test.tempThings.ShowCaseFragment;
import com.example.test.tempThings.ShowCaseMap;
import com.google.android.gms.maps.model.LatLng;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerServiceImpl implements ServerInterface {
    public static final String BASE_URL = "http://192.168.1.120:8081";

    private Response.ErrorListener errorListener;

    public ServerServiceImpl() {
        errorListener = new ErrorListenerImpl();
    }

    public void findByLength(long length, double x, double y, Context context, MapFragment mapFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/length";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                List<Event> events = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        User user = new UserMapper().userFromJsonArray(jsonObject);
                        Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                        events.add(event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("GET_ALL", events.toString());
                mapFragment.updateLen(events, x, y, (int) length);
            }
        }, errorListener);

        queue.add(jsonArrayRequest);
    }

    @Override
    public void insertEvent(Event e, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/add?x="+e.getX()+
                " &y="+ e.getY()+"&date="+ e.getDate()+"&place="+ e.getPlace() +
                "&description="+ e.getDescription()+
                "&ageRes="+ (e.isHasAgeRestrictions() > 0 ? "1" : "0")+
                "&covid="+ (e.isHasCovid() > 0 ? "1" : "0")+
                "&fam="+ (e.isFamily() > 0 ? "1" : "0") +
                "&free="+ (e.isFree() > 0 ? "1" : "0") +
                "&group="+ (e.isGroup() > 0 ? "1" : "0") +
                "&sport="+ (e.isSport() > 0 ? "1" : "0") +
                "&owner="+ e.getOwner_id() +
                "&street="+ (e.isStreet() > 0 ? "1" : "0")+
                "&name="+ e.getName()+
                "&register="+ (e.isHasRegister() > 0 ? "1" : "0");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = response;
                User user = new UserMapper().userFromJsonArray(jsonObject);
                Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                Log.d("ADDED_EVENT", event.toString());
            }
        }, errorListener);

        queue.add(jsonObjectRequest);
    }

    @Override
    public void getById(int id, Context context, ShowCaseFragment showCaseFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/" + id;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = response;
                User user = new UserMapper().userFromJsonArray(jsonObject);
                Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                showCaseFragment.update(event);
                Log.d("EVENT_BY_ID", event.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showCaseFragment.back();
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void getById(int id, Context context, ShowCaseMap showCaseMap) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/" + id;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = response;
                User user = new UserMapper().userFromJsonArray(jsonObject);
                Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                showCaseMap.update(event);
                Log.d("EVENT_BY_ID", event.toString());
            }
        }, errorListener);

        queue.add(jsonArrayRequest);
    }

    @Override
    public void getById(int id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/" + id;

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = response;
                User user = new UserMapper().userFromJsonArray(jsonObject);
                Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId(), user.getName());
                Log.d("EVENT_BY_ID", event.toString());
                ((EventShowCaseActivity) context).update(event);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EventById","null"+ error.getMessage());
                ((EventShowCaseActivity)context).back();
            }
        });

        queue.add(jsonArrayRequest);
    }

    @Override
    public void searchByName(String name, Context context, SearchFragment searchFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/name?name=" + name;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Event> events = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new UserMapper().userFromJsonArray(jsonObject);
                        Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                        events.add(event);
                    }
                    Log.d("NAME_EVENT_LIST", events.toString());
                    searchFragment.update(events);
                } catch (JSONException e) {
                    Log.d("NAME_EVENT_LIST", e.getMessage());
                }

            }
        }, errorListener);

        queue.add(jsonArrayRequest);

    }


    @Override
    public void getByUserId(int id, Context context, HomeFragment homeFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/user?id=" + id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Event> events = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new UserMapper().userFromJsonArray(jsonObject);
                        Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                        events.add(event);
                    }
                    homeFragment.update(events);
                    Log.d("USER_EVENT_LIST", events.toString());
                } catch (JSONException e) {
                    Log.d("USER_EVENT_LIST", e.getMessage());
                }
            }
        }, errorListener);

        queue.add(jsonArrayRequest);
    }

    @Override
    public void getByCategories(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean g, boolean h, Context context, MapFragment mapFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/categories?street=" + (a ? 1 : 0) + "&group=" + (b ? 1 : 0) + "&fam=" + (c ? 1 : 0) + "&free=" + (d ? 1 : 0) + "&covid=" + (e ? 1 : 0) + "&reg=" + (f ? 1 : 0) + "&sport=" + (g ? 1 : 0) + "&age=" + (h ? 1 : 0);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Event> events = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new UserMapper().userFromJsonArray(jsonObject);
                        Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                        events.add(event);
                    }
                    Log.d("CATEGORIES_EVENT_LIST", events.toString());
                    mapFragment.updateCat(events);

                } catch (JSONException e) {
                    Log.d("CATEGORIES_EVENT_LIST", e.getMessage());
                }
            }
        }, errorListener);
        queue.add(jsonArrayRequest);
    }

    @Override
    public void getByCategories(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f, boolean h, boolean g, Context context, SearchFragment searchFragment) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/categories?street=" + a + "&group=" + b + "&fam=" + c + "&free=" + d + "&covid=" + e + "&reg=" + f + "&sport=" + g + "&age=" + h;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Event> events = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);

                        User user = new UserMapper().userFromJsonArray(jsonObject);
                        Event event = new EventMapper().eventFromJsonArray(jsonObject, user.getId());
                        events.add(event);
                    }
                    Log.d("CATEGORIES_EVENT_LIST", events.toString());
                    searchFragment.update(events);
                } catch (JSONException e) {
                    Log.d("CATEGORIES_EVENT_LIST", e.getMessage());
                }
            }
        }, errorListener);
        queue.add(jsonArrayRequest);
    }

    @Override
    public void removeEvent(Event event, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/delete?id=" + event.getId() + "";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.DELETE,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            }
        }, errorListener);

        queue.add(jsonArrayRequest);
    }

    @Override
    public void login(String login, String password, Context context) {
        final boolean[] done = {false};
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/user/login" + "?login=" + login + "&password=" + password;
        Log.i("URL", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int result = response.getInt("result");
                    int id = response.getInt("id");
                    Log.d("LOGIN", result + " " + id);
                    ((MainActivity) context).update(result, id);
                } catch (JSONException e) {
                    Log.d("LOGIN", e.getMessage());
                }
            }
        }, errorListener);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void register(String login, String password, String username, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/user/register" + "?login=" + login + "&password=" + password + "&username=" + username;
        Log.i("URL", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int result = response.getInt("result");
                    Log.d("REGISTER", result + "");
                    ((RegisterActivity) context).update(result);
                } catch (JSONException e) {
                    Log.d("REGISTER", e.getMessage());
                }
            }
        }, errorListener);
        queue.add(jsonObjectRequest);
    }

    @Override
    public User getUserById(int id, Context context) {
        User[] result = null;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/user/" + id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                result[0] = new UserMapper().userFromJsonArray(response);
                Log.d("GET_USER_BY_ID", result[0].getId() + " " + result[0].getName());
            }

        }, errorListener);

        queue.add(jsonObjectRequest);
        return result[0];
    }

    @Override
    public void findByEvent(Event event, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/" + event.getId() + "/comments";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Comment> comments = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        User user = null;
                        JSONObject jsonObject = response.getJSONObject(i);
                        try {
                            user = new UserMapper().userFromJsonArray(jsonObject.getJSONObject("user"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Comment comment = new CommentMapper().commentFromJsonArray(jsonObject, user.getId(), user.getName());
                        comments.add(comment);
                    }
                    ((EventShowCaseActivity) context).update(comments);
                    Log.d("GET_EVENT_COMMENTS", comments.toString());

                } catch (JSONException e) {
                    Log.d("GET_EVENT_COMMENTS", e.getMessage());
                }
            }

        }, errorListener);

        queue.add(jsonArrayRequest);
    }

    @Override
    public void insertComment(Comment comment, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/events/comment/new?content=" + comment.getMessage() + "&event_id=" + comment.getEvent_id() + "&author_id=" + comment.getAuthor_id();//TODO: put parameters into a url
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Comment> comments = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        User user = new UserMapper().userFromJsonArray(jsonObject.getJSONObject("user"));
                        Comment comment = new CommentMapper().commentFromJsonArray(jsonObject, user.getId(), user.getName());
                        comments.add(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("COMMENT_INSERT", comments.toString());
                ((EventShowCaseActivity) context).update(comments);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((EventShowCaseActivity)context).back();
            }
        });
        queue.add(jsonArrayRequest);
    }

    private class ErrorListenerImpl implements Response.ErrorListener {


        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("VolleyError", "message:" + error.getMessage());
        }
    }
}
