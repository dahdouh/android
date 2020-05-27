package com.example.ecoleenligne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.LogSession;
import com.example.ecoleenligne.model.User;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    SharedPreferences sharedpreferences;
    TextView fullname, role, level, dateLogin, dateLogout;
    String user_selected;
    /*---- Menu ---*/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);


        /*------------------------ Menu ---------------------*/
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.bringToFront();
        toolbar=findViewById(R.id.toolbar);
        toolbar.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_darawer_open, R.string.navigation_darawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        fullname = findViewById(R.id.fullname);
        role = findViewById(R.id.role);
        level = findViewById(R.id.level);
        dateLogin = findViewById(R.id.dateLogin);
        dateLogout = findViewById(R.id.dateLogout);

        /*--------------get selected user from previous listView --------------*/
        Intent intent = getIntent();
        user_selected = intent.getStringExtra("user_id");

        /*--------------------- profile update listener -----------------*/
        ImageView img = (ImageView) findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(StudentDetailActivity.this, StudentUpdateActivity.class);
                intent.putExtra("user_id", user_selected);
                context.startActivity(intent);
            }
        });

        /*------------------  get profile from server  -----------------*/
        String url = MainActivity.IP+"/profile/"+user_selected;
        RequestQueue queue = Volley.newRequestQueue(context);


        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String user_exist = response.getString("firstname");
                    if(user_exist.equals("user not exist")) {
                        Toast.makeText(context, "User not exist", Toast.LENGTH_LONG).show();
                    } else {
                        String fullname_data = response.getString("firstname")+" "+response.getString("lastname");

                        JSONObject compteJsonObject=response.getJSONObject("compte");
                        JSONObject profileJsonObject=compteJsonObject.getJSONObject("profile");
                        String profile_role_data = profileJsonObject.getString("libelle");
                        String level_data = response.getString("level");

                        try {
                            List<LogSession> listLogSession = new ArrayList<>(response.getJSONArray("logSession").length());
                            //for (int i = 0; i < response.getJSONArray("logSession").length(); i++) {
                                 /*------------- check if user was never connected -------------*/
                            if(response.getJSONArray("logSession").length() == 0) {
                                dateLogin.setText(R.string.login_never_connected);
                                dateLogout.setText("");
                            } else {
                                JSONObject item = response.getJSONArray("logSession").getJSONObject(response.getJSONArray("logSession").length() - 1);
                                String id = item.getString("id");
                                String dateConnexion = item.getString("dateConnexion");
                                String dateDeconnexion = item.getString("dateDeconnexion");

                                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
                                //String dateString = sdf.format(dateConnexion);

                                String[] parts = dateConnexion.split("-");
                                String yy = parts[0];
                                String mm = parts[1];
                                String[] partsTime = parts[2].split(":");
                                String dd = partsTime[0];
                                String hh = partsTime[1];
                                //String mn = partsTime[2];
                                //String ss = partsTime[3];
                                dateLogin.setText(yy +"-"+ mm +"-"+ dd +":"+ hh);
                                if(dateDeconnexion != null) {
                                    String[] partsDateDeconx  = dateDeconnexion.split("-");
                                    yy = partsDateDeconx[0];
                                    mm = partsDateDeconx[1];
                                    String[] partsTimeDateDeconx = partsDateDeconx[2].split(":");
                                    dd = partsTimeDateDeconx[0];
                                    hh = partsTimeDateDeconx[1];
                                    dateLogout.setText(yy +"-"+ mm +"-"+ dd +":"+ hh);
                                }


                                //Toast.makeText(context, "yyyyyyyy " + dateDeconnexion, Toast.LENGTH_SHORT).show();
                                //String level = item.getString("level");
                                //listLogSession.add(new LogSession(Integer.parseInt(id), firstname,  level));
                            }
                        } catch (JSONException error) {
                            new AlertDialog.Builder(context)
                                     .setTitle("Error")
                                     .setMessage(error.toString())
                                     .show();
                        }


                            fullname.setText(fullname_data);
                            role.setText(profile_role_data);
                            level.setText(level_data);
                            //tel.setText(first_login+"");
                            //ville.setText(last_logout+"");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage(e.toString())
                                .show();
                    }
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

                    Intent intent=new Intent(StudentDetailActivity.this, LoginActivity.class);
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



    /*---------------------- Menu actions ---------------------*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_profile = sharedpreferences.getString(MainActivity.Role, null);

        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                Intent intent_profile = new Intent(this, ProfileActivity.class);
                startActivity(intent_profile);
                break;
            case R.id.nav_home:
                //Intent intent_home = new Intent(this, HomeActivity.class);
                //startActivity(intent_home);
                break;
            case R.id.nav_courses:
                Intent intent_courses = new Intent(this, DashboardActivity.class);
                startActivity(intent_courses);
                break;
            case R.id.nav_dashboard:
                Intent intent_dashboard;
                if(user_profile.equals("ROLE_PARENT")) {
                    intent_dashboard = new Intent(this, DashboardParentActivity.class);
                } else {
                    intent_dashboard = new Intent(this, DashboardActivity.class);
                }
                startActivity(intent_dashboard);
                break;
            case R.id.nav_evaluation:
                Intent intent_evaluation = new Intent(this, LoginActivity.class);
                startActivity(intent_evaluation);
                break;
            case R.id.nav_login:
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_profile).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                break;
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Partager", Toast.LENGTH_SHORT).show(); break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }
}
