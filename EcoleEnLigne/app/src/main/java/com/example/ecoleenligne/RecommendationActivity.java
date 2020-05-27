package com.example.ecoleenligne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.Course;
import com.example.ecoleenligne.model.Recommendation;
import com.example.ecoleenligne.model.User;
import com.example.ecoleenligne.util.RecommendationListAdapter;
import com.example.ecoleenligne.util.SQLiteHelper;
import com.example.ecoleenligne.util.StudentListAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecommendationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    SharedPreferences sharedpreferences;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Menu menu;
    TextView recommendationMsg;
    /*------ offline mode -------*/
    SQLiteHelper sqLiteHelper;

    private RecommendationListAdapter recommendationListAdapter;
    ListView listview;
    List<Course> recommendations = new ArrayList<Course>();
    List<Course> courses = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        sqLiteHelper= new SQLiteHelper(this);

        recommendationMsg = findViewById(R.id.recommendation_msg);

        /*------------------  make transparent Status Bar  -----------------*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        /*------------------------ List of students  ---------------------*/
        this.recommendationListAdapter = new RecommendationListAdapter(this, recommendations);
        ListView studentsListView = (ListView) findViewById(R.id.list_recommendations);
        studentsListView.setAdapter(recommendationListAdapter);

        if(MainActivity.MODE.equals("ONLINE")) {
            /*--------------retrieve data from customer student listView --------------*/
            studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String course_selected = ((TextView) view.findViewById(R.id.course_id)).getText().toString();
                    Intent intent = new Intent(RecommendationActivity.this, CoursActivity.class);
                    intent.putExtra("id", course_selected);
                    startActivity(intent);
                }

            });
        }

        /*------------------------ Menu ---------------------*/
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.bringToFront();
        //toolbar=findViewById(R.id.toolbar);
        //toolbar.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.navigation_darawer_open, R.string.navigation_darawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_dashboard);
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_profile_data = sharedpreferences.getString(MainActivity.Role, null);
        /*------------------- hide items from menu ---------------------*/
        if(user_profile_data.equals("ROLE_PARENT")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_courses).setVisible(false);
            menu.findItem(R.id.nav_recommendation).setVisible(false);
            menu.findItem(R.id.nav_exercices).setVisible(false);
        }


        /*-------------- check if there is connection--------------*/
        if(MainActivity.MODE.equals("ONLINE")) {
            /*------------------------ ONLINE MODE  ---------------------*/

            //--------------------- recommendation message --------------------//
            String url_recom_msg =MainActivity.IP+"/recommendation/msg";
            RequestQueue queueRecommendation = Volley.newRequestQueue(context);
            JsonArrayRequest requestRecommendation = new JsonArrayRequest(Request.Method.GET, url_recom_msg, null,
                    new Response.Listener<JSONArray>() {
                    String recommendation_msg = "";
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            List<String> messages = new ArrayList<String>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject item = response.getJSONObject(i);
                                String contenu = item.getString("contenu");
                                messages.add(contenu);
                            }

                            Random rand = new Random();
                            recommendation_msg =messages.get(rand.nextInt(messages.size()));
                            recommendationMsg.setText(recommendation_msg);
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

            queueRecommendation.add(requestRecommendation);


            /*--------------get user from session --------------*/
            sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);

            //--------------------- courses --------------------//
            String url =MainActivity.IP+"/api/cours/recommendation";
            RequestQueue queueCourses = Volley.newRequestQueue(context);
            JsonArrayRequest requestCourses = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                courses = new ArrayList<>(response.length());
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject item = response.getJSONObject(i);
                                    // String id = item.getString("_id");
                                    String libelle = item.getString("libelle");
                                    String path = item.getString("image");
                                    String iden = item.getString("id");
                                    String description = item.getString("description");
                                    courses.add(new Course(Integer.parseInt(iden), libelle, description,  path));
                                }
                                recommendationListAdapter.setCourses(courses);
                            } catch (JSONException error) {
                                new AlertDialog.Builder(context)
                                        .setTitle("Error")
                                        .setMessage(error.toString())
                                        .show();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            new AlertDialog.Builder(context)
                                    .setTitle("Error")
                                    .setMessage(error.getMessage())
                                    .show();
                        }
                    }
            );
            queueCourses.add(requestCourses);
        } else  {
            List<String> messages = new ArrayList<String>();
            List<Recommendation> list_recommendations_sqlite = sqLiteHelper.getRecommendationsFromDb();
            for (Recommendation r: list_recommendations_sqlite) {
                messages.add(r.getContenu());
            }
            Random rand = new Random();
            String recommendation_msg =messages.get(rand.nextInt(messages.size()));
            recommendationMsg.setText(recommendation_msg);

            List<Course> list_courses_recom_sqlite= sqLiteHelper.getCoursesRecommendationFromDb();
            courses = new ArrayList<>(list_courses_recom_sqlite.size());
            for(Course course : list_courses_recom_sqlite){
                courses.add(new Course(course.getId(), course.getLibelle(), course.getDescription(), course.getImage()));
            }
            recommendationListAdapter.setCourses(courses);





        }
    }

    /*
    @Override
    public void onResponse(JSONArray response) {

        try {
            courses = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                // String id = item.getString("_id");
                String libelle = item.getString("libelle");
                String path = item.getString("image");
                String iden = item.getString("id");
                String description = item.getString("description");
                courses.add(new Course(Integer.parseInt(iden), libelle, description,  path));
            }
            recommendationListAdapter.setCourses(courses);


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
    */


    /*---------------------- Unsubscribe out function --------------------------*/
    public void confirmUnsubscribe(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.presence_busy)
                .setTitle("Confirmation")
                .setMessage(R.string.unsubscribe_confirm)
                .setPositiveButton(R.string.unsubscribe_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        unsubscribe();
                    }
                })
                .setNegativeButton(R.string.unsubscribe_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public  void unsubscribe(){
        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);
        String user_connected_login = sharedpreferences.getString(MainActivity.Login, null);

        if(user_connected_id == null && user_connected_login == null) {
            Toast.makeText(context, "You must fisrt connect!", Toast.LENGTH_LONG).show();
        } else {
            /*-------------- user unsubscribe ----------*/
            String url = MainActivity.IP+"/unsubscribe/" + user_connected_id;
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject jsonObject = new JSONObject();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Toast.makeText(context, R.string.unsubscribe_success, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(RecommendationActivity.this, LoginActivity.class);
                    context.startActivity(intent);
                    logout();

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

    /*---------------------- Log out function --------------------------*/
    public  void logout(){
        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);
        String user_connected_login = sharedpreferences.getString(MainActivity.Login, null);

        if(user_connected_id == null && user_connected_login == null) {
            Toast.makeText(context, "You are already disconnected!", Toast.LENGTH_LONG).show();
        } else {
            /*-------------- check if there is connection--------------*/
            if(MainActivity.MODE.equals("ONLINE")) {
                /*-------------- user logout ----------*/
                String url = MainActivity.IP+"/logout/" + user_connected_id;
                RequestQueue queue = Volley.newRequestQueue(context);
                JSONObject jsonObject = new JSONObject();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent=new Intent(RecommendationActivity.this, LoginActivity.class);
                        context.startActivity(intent);

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
            } else {
                Intent intent=new Intent(RecommendationActivity.this, LoginActivity.class);
                context.startActivity(intent);
            }

            /*---------------clear session ------*/
            SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(context, getString(R.string.logout_success), Toast.LENGTH_LONG).show();
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
            case R.id.nav_dashboard:
                Intent intent_dashboard;
                if(user_profile.equals("ROLE_PARENT")) {
                    intent_dashboard = new Intent(this, DashboardParentActivity.class);
                } else {
                    intent_dashboard = new Intent(this, DashboardActivity.class);
                }
                startActivity(intent_dashboard);
                break;
            case R.id.nav_courses:
                Intent intent_courses = new Intent(this, ListeCoursActivity.class);
                startActivity(intent_courses);
                break;
            case R.id.nav_exercices:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intent_exercices = new Intent(this, ListeCoursExerciceActivity.class);
                    startActivity(intent_exercices);
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
            case R.id.nav_recommendation:
                Intent intent_recommendation = new Intent(this, RecommendationActivity.class);
                startActivity(intent_recommendation);
                break;
            case R.id.nav_chat:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intent_chat = new Intent(this, ChatActivity.class);
                    startActivity(intent_chat);
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
            case R.id.nav_payment:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intent_payment = new Intent(this, PayementActivity.class);
                    startActivity(intent_payment);
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
            case R.id.nav_synchronisation:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intent_synchronisation = new Intent(this, SynchronisationActivity.class);
                    startActivity(intent_synchronisation);
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
            case R.id.nav_unsubscribe:
                if(MainActivity.MODE.equals("ONLINE")) {
                    confirmUnsubscribe();
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_share:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                    intentShare.setType("text/plain");
                    intentShare.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_msg));
                    intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    startActivity(Intent.createChooser(intentShare, ""+R.string.share_title));
                } else {
                    Toast toast = Toast.makeText(this, Html.fromHtml("<font color='#FFFFFF'><b>"+ getString(R.string.connection_msg) +"</b></font>"), Toast.LENGTH_SHORT);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.parseColor("#ff0040"));
                    toast.show();
                }
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }


}
