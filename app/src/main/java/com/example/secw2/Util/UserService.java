package com.example.secw2.Util;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
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
    private static final String TAG = "UserService";
    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";
    private static final String STUDENT_ID = "20337840";
    private static final String CREATE = "create_user";
    private static final String READ = "read_user";
    private static final String READ_ALL = "read_all_users";
    private static final String UPDATE = "update_user";
    private static final String DELETE = "delete_user";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    private static String url(String... parts) {
        StringBuilder sb = new StringBuilder(BASE_URL);
        for (String p : parts) {
            if (sb.charAt(sb.length() - 1) != '/') sb.append('/');
            sb.append(p);
        }
        return sb.toString();
    }

    private static JSONObject toApiJson(UserBean u) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("username", u.getUsername());
        obj.put("password", u.getPassword());
        obj.put("firstname", u.getFirstname());
        obj.put("lastname", u.getLastname());
        obj.put("email", u.getEmail());
        obj.put("contact", u.getContact());
        obj.put("usertype", u.getUserType());
        return obj;
    }

    public static void getAllUser(Context context) {
        initQueue(context);
        String full = url(READ_ALL, STUDENT_ID);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, full, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type listType = new TypeToken<List<UserBean>>() {
                }.getType();
                List<UserBean> users = gson.fromJson(response.toString(), listType);
                for (UserBean u : users) {
                    Log.d(TAG, "User: " + u.getUsername() + ", name=" + u.getFirstname() + " " + u.getLastname());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error retrieving users: " + (error.getMessage() != null ? error.getMessage() : "unknown"));
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10_000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


    public static void getUser(Context context, String username, java.util.function.Consumer<UserBean> onSuccess, java.util.function.Consumer<String> onError) {
        initQueue(context);
        String full = url(READ, STUDENT_ID, username);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, full, null, new Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(org.json.JSONObject response) {
                try {
                    org.json.JSONObject payload = response.has("user") ? response.getJSONObject("user") : response;
                    UserBean user = gson.fromJson(payload.toString(), UserBean.class);
                    if (onSuccess != null) onSuccess.accept(user);
                } catch (Exception ex) {
                    if (onError != null) onError.accept("Parse error: " + ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = (error.getMessage() != null ? error.getMessage() : "unknown");
                if (onError != null) onError.accept(msg);
            }
        });
        requestQueue.add(request);
    }

    public static void createUser(Context context, UserBean user) {
        initQueue(context);
        String full = url(CREATE, STUDENT_ID);
        try {
            JSONObject body = toApiJson(user);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, full, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String message = response.optString("message", "User created successfully");
                    Log.d(TAG, message);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error creating user: " + (error.getMessage() != null ? error.getMessage() : "unknown"));
                }
            });
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e(TAG, "Invalid JSON for user creation: " + e.getMessage());
        }
    }

    public static void updateUser(Context context, String username, UserBean user) {
        initQueue(context);
        String full = url(UPDATE, STUDENT_ID, username);
        try {
            JSONObject body = toApiJson(user);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, full, body, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String message = response.optString("message", "User updated successfully");
                    Log.d(TAG, message);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMessage = "Unknown error";
                    if (error.networkResponse != null) {
                        errorMessage = "Status Code: " + error.networkResponse.statusCode;
                        try {
                            String respBody = new String(error.networkResponse.data);
                            Log.e(TAG, "Error Body: " + respBody);
                        } catch (Exception ignore) {
                        }
                    }
                    Log.e(TAG, "Error updating user: " + errorMessage);
                }
            });
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e(TAG, "Invalid JSON for user update: " + e.getMessage());
        }
    }

    public static void deleteUser(Context context, String username) {
        initQueue(context);
        String full = url(DELETE, STUDENT_ID, username);
        StringRequest request = new StringRequest(Request.Method.DELETE, full, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "User deleted successfully");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error deleting user: " + (error.getMessage() != null ? error.getMessage() : "unknown"));
            }
        });
        requestQueue.add(request);
    }
}