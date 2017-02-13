package com.example.joe.smarttask.SignUp;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by joe on 12/02/2017.
 */

public class ServerSettings extends AsyncTask<String, Void, Void> {

    private Context context;

    private boolean emailExists;


    private String firstName, lastName, email, birthday, userName, password;
    private String serverUser = "web22387422", serverPassword = "joe12345", serverLink = "http://alfa3205.alfahosting-server.de";


    public ServerSettings(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
    }

    @Override
    public Void doInBackground(String... params) {
        if (emailExists) {
            try {
                String link = serverLink + serverUser + "& password=" + serverPassword;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();
            } catch (Exception e) {
                Log.d("sdf", "dfw");
            }
        } else {
            try {
                String link = "http://myphpmysqlweb.hostei.com/loginpost.php";
                String data = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(serverUser, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(serverPassword, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }


            } catch (Exception e) {

            }
        }
    }


    public void setUploadStrings(String[] dataString) {
        firstName = dataString[0];
        lastName = dataString[1];
        birthday = dataString[2];
        email = dataString[3];
        userName = dataString[4];
        password = dataString[5];
        uploadData();
    }


    protected void onPostExecute(String result) {

    }

    private void uploadData() {

    }
}
