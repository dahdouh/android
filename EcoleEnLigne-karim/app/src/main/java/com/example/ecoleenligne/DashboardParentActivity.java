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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.Compte;
import com.example.ecoleenligne.model.Offre;
import com.example.ecoleenligne.model.User;
import com.example.ecoleenligne.util.StudentListAdapter;
import com.example.ecoleenligne.util.ToDoListAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardParentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Response.Listener<JSONArray>, Response.ErrorListener {

    /*------------------ Menu ----------------*/
    final Context context = this;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    private StudentListAdapter studentListAdapter;
    ListView listview;
    List<User> students = new ArrayList<User>();

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_parent);

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

        /*------------------------ Button add student ---------------------*/
        final ImageButton student_add_imageButton = findViewById(R.id.student_add_imageButton);
        student_add_imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent=new Intent(DashboardParentActivity.this, StudentAddActivity.class);
                context.startActivity(intent);
            }
        });

        /*------------------------ List of students  ---------------------*/
        this.studentListAdapter = new StudentListAdapter(this, students);
        ListView studentsListView = findViewById(R.id.list_students);
        studentsListView.setAdapter(studentListAdapter);

        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected = sharedpreferences.getString(MainActivity.Id, null);
        /*-------------- call restful webservices ----------*/
        //String url =  "https://onlineschool.cfapps.io/students/"+ user_connected;
        String url =  "http://192.168.43.203:8085/students/"+ user_connected;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);

    }



    /*-------------------- get students from server RESTful ------------------*/
        @Override
        public void onResponse(JSONArray response) {
            try {
                students = new ArrayList<>(response.length());
                for (int i = 0; i < response.length(); i++) {
                    JSONObject item = response.getJSONObject(i);
                    String firstname = item.getString("firstname");
                    String lastname = item.getString("lastname");
                    students.add(new User(firstname, lastname));
                }
                studentListAdapter.setStudents(students);
            } catch (JSONException error) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage(error.toString())
                        .show();
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            studentListAdapter.setStudents(new ArrayList<User>());
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(error.getMessage())
                    .show();
        }



    /*---------------------- Menu actions ---------------------*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent intent_home = new Intent(this, HomeActivity.class);
                startActivity(intent_home);
                break;
            case R.id.nav_courses:
                Intent intent_courses = new Intent(this, DashboardActivity.class);
                startActivity(intent_courses);
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
                //menu.findItem(R.id.nav_logout).setVisible(false);
                //menu.findItem(R.id.nav_profile).setVisible(false);
                //menu.findItem(R.id.nav_login).setVisible(true);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Partager", Toast.LENGTH_SHORT).show(); break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
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
            String url = "http://192.168.43.203:8085/logout/" + user_connected_id;
            RequestQueue queue = Volley.newRequestQueue(context);

            JSONObject jsonObject = new JSONObject();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject userJsonObject = response.getJSONObject("user");
                        String connected = userJsonObject.getString("firstname");
                        //if (!connected.equals("not found")) {
                            /*---------------clear session ------*/
                            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.commit();
                            Toast.makeText(context, getString(R.string.logout_success), Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(DashboardParentActivity.this, LoginActivity.class);
                            context.startActivity(intent);
                       // } else {
                        //    Toast.makeText(context, "You are already disconnected!", Toast.LENGTH_LONG).show();
                        //}

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



    }

    @Override
    protected void onResume() {
        super.onResume();
        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected = sharedpreferences.getString(MainActivity.Id, null);
        /*-------------- call restful webservices ----------*/
        //String url =  "https://onlineschool.cfapps.io/students/"+ user_connected;
        String url =  "http://192.168.43.203:8085/students/"+ user_connected;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);
    }

}
