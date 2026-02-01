package com.example.secw2.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class UserService {
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";
    private static final String separator = "/";
    private static final String studentId = "20337840";
    private static final String create = "create_user";
    private static final String read = "read_user";
    private static final String readAll = "read_all_users";
    private static final String update = "update_user";
    private static final String delete = "delete_user";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    public static void getAllUsers(Context context) {
        initQueue(context);
        String url = BASE_URL + separator + readAll + separator + studentId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<UserBean>>() {
                }.getType();
                List<UserBean> userList = gson.fromJson(response.toString(), listType);

                for (UserBean user : userList) {
                    Log.d("UserInfo", "Firstname: " + user.getFirstname());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserError", "Error retrieving Users: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    public static void getUserById(Context context, int id) {
        initQueue(context);
        String url = BASE_URL + separator + read + separator + studentId + separator + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                UserBean user = gson.fromJson(response.toString(), UserBean.class);
                Log.d("UserInfo", "Firstname: " + user.getFirstname());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserError", "Error retrieving user by ID: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    public static void insertUser(Context context, UserBean user) {
        initQueue(context);
        String url = BASE_URL + separator + create + separator + studentId;

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(user));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String message = response.optString("message", "User added successfully");
                    Log.d("UserService", message);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("UserError", "Error adding user: " + error.getMessage());
                }
            });
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("UserError", "Invalid JSON format: " + e.getMessage());
        }
    }

    public static void updateUser(Context context, UserBean user) {
        initQueue(context);
        String url = BASE_URL + separator + update + separator + studentId + user.getUsername();

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(user));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String message = response.optString("message", "User updated successfully");
                    Log.d("UserService", message);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMessage = "Unknown error";

                    if (error.networkResponse != null) {
                        errorMessage = "Status Code: " + error.networkResponse.statusCode;
                        String responseBody = new String(error.networkResponse.data);
                        Log.e("UserError", "Error Body: " + responseBody);
                    }

                    Log.e("UserError", "Error updating user: " + errorMessage);
                }
            });
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("UserError", "Invalid JSON format: " + e.getMessage());
        }
    }

    public static void deleteUser(Context context, String id) {
        initQueue(context);
        String url = BASE_URL + separator + delete + separator + studentId + separator + id;

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UserService", "User deleted successfully");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserError", "Error deleting User: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }
}
