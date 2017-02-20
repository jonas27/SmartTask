package com.example.joe.smarttask.SignUp;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joe on 12/02/2017.
 */

public class ServerSettings extends AppCompatActivity {

    private static final String URL = "http://jonasburster.de/html/user_control.php";
    private static String[] s;
    private Context context;
    private String firstName, lastName, email, birthday, userName, password;
    private com.android.volley.RequestQueue queue;
    private StringRequest request;


    public ServerSettings(Context context) {
        this.context = context;
    }

    public void setData(String[] s) {
        ServerSettings.s = s;
        sendData();

    }


    //See https://developer.android.com/training/volley/simple.html for documentation

    private void sendData() {

        queue = Volley.newRequestQueue(context);

        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).toString().equals("success")) {
                        Toast.makeText(context, "SUCCESS " + jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                        SignUpActivity.createdNewAccount = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException jsonE) {
                    //resean why empty!

                } finally {
                    //release resources --> not needed because JSONObject is created insided try --> is local variable
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("firstname", s[0]);
                hashMap.put("lastname", s[1]);
                hashMap.put("birthday", s[2]);
                hashMap.put("email", s[3]);
                hashMap.put("username", s[4]);
                hashMap.put("password", s[5]);

                return hashMap;
            }
        };
        queue.add(request);


    }


}
