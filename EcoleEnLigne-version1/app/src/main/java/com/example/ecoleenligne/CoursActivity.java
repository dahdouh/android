package com.example.ecoleenligne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CoursActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener{

    final Context context = this;
    SharedPreferences sharedpreferences;

    ListView listview;
    String id;
    List<String> cours = new ArrayList<String>();
    List<String> description = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_description_layout);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        /*------------------------ Webservices  ---------------------*/
        ArrayList<String> offres = new ArrayList<String>();



        // issue the request
        //String url = "https://onlineschool.cfapps.io/api/cours/"+id;
        String url = "http://192.168.43.203:8085/api/cours/"+id;
        // String url = "http://10.119.112.21:8080/api/cours/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);


    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            cours = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                // String id = item.getString("_id");
                String message = item.getString("libelle");
                String dec = item.getString("description");
                final String lien = item.getString("image");

                final ImageView image = (ImageView)findViewById(R.id.imgCours);


               /* Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
*/

                            URL url = null;
                            try {
                                url = new URL(lien);
                                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

                                httpConn.connect();
                                int resCode = httpConn.getResponseCode();

                                if (resCode == HttpURLConnection.HTTP_OK) {
                                    InputStream in = httpConn.getInputStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(in);

                                    image.setImageBitmap(bitmap);
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

            /*catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();

*/






                TextView cours = (TextView)findViewById(R.id.titre);
                    TextView description = (TextView)findViewById(R.id.desc);
                    cours.setText(message);
                    description.setText(dec);

                Button b = (Button) findViewById(R.id.btnIns);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CoursActivity.this, TypeCoursActivity.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                });

            }
        } catch (JSONException error) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(error.toString())
                    .show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error.getMessage())
                .show();
    }

    /*---------------------- Log out function --------------------------*/
    public  void logout(){
        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);
        String user_connected_login = sharedpreferences.getString(MainActivity.Login, null);

        if(user_connected_id == null && user_connected_login == null) {
            Toast.makeText(context, "You are already disconnected!", Toast.LENGTH_LONG).show();
        } else {
            /*-------------- user logout ----------*/
            //String url = "https://onlineschool.cfapps.io/logout/" + user_connected_id;
            String url = MainActivity.IP+"/logout/" + user_connected_id;
            RequestQueue queue = Volley.newRequestQueue(context);

            JSONObject jsonObject = new JSONObject();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //String connected = userJsonObject.getString("firstname");
                    //if (!connected.equals("not found")) {
                    /*---------------clear session ------*/
                    SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(context, getString(R.string.logout_success), Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(CoursActivity.this, LoginActivity.class);
                    context.startActivity(intent);
                    // } else {
                    //    Toast.makeText(context, "You are already disconnected!", Toast.LENGTH_LONG).show();
                    //}

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage(R.string.server_restful_error)
                            .show();
                }
            });

            queue.add(request);
        }

    }





}
