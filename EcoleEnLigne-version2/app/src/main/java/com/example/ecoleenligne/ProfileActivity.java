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
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.Compte;
import com.example.ecoleenligne.model.Profile;
import com.example.ecoleenligne.model.User;
import com.example.ecoleenligne.util.SQLiteHelper;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    SharedPreferences sharedpreferences;
    TextView fullname, role, email, tel, ville;
    /*---- Menu ---*/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    /*------ offline mode -------*/
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sqLiteHelper= new SQLiteHelper(this);

        /*------------------  make transparent Status Bar  -----------------*/
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
         //   Window w = getWindow();
         //  w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //}

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

        /*------------------ profile update listener -----------------*/
        ImageView img = (ImageView) findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this, ProfileUpdateActivity.class);
                context.startActivity(intent);
            }
        });

        fullname = findViewById(R.id.fullname);
        role = findViewById(R.id.role);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        ville = findViewById(R.id.ville);

        /*--------------get user from session --------------*/
        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);


        /*--------------get selected user from previous listView --------------*/
        Intent intent = getIntent();

        /*-------------- check if there is connection--------------*/
        if(MainActivity.MODE.equals("ONLINE")) {
            /*------------------  get profile from server  -----------------*/
            String url = MainActivity.IP + "/profile/" + user_connected_id;
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject jsonObject = new JSONObject();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String user_exist = response.getString("firstname");


                        if (user_exist.equals("user not exist")) {
                            Toast.makeText(context, "=====>  User not exist", Toast.LENGTH_LONG).show();
                        } else {
                            String fullname_data = response.getString("firstname") + " " + response.getString("lastname");

                            JSONObject compteJsonObject = response.getJSONObject("compte");
                            JSONObject profileJsonObject = compteJsonObject.getJSONObject("profile");
                            String profile_role_data = profileJsonObject.getString("libelle");
                            String email_data = response.getString("email");
                            String tel_data = response.getString("tel");
                            String ville_data = response.getString("ville");

                            fullname.setText(fullname_data);
                            role.setText(profile_role_data);
                            email.setText(email_data);
                            tel.setText(tel_data);
                            ville.setText(ville_data);
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
        } else {
            User user= sqLiteHelper.getUserByIdFromDb(Integer.parseInt(user_connected_id));

            String fullname_data = user.getFirstname()+" "+user.getLastname();
            int compte_id = user.getCompte_id();
            Compte compte = sqLiteHelper.getCompteByIdFromDb(compte_id);
            int profile_id = compte.getProfile_id();
            Profile profile = sqLiteHelper.getProfileByIdFromDb(profile_id);
            String profile_role_data = profile.getLibelle();

            fullname.setText(fullname_data);
            role.setText(profile_role_data);
            email.setText(user.getEmail());
            tel.setText(user.getTel());
            ville.setText(user.getVille());

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

                    Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
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
                    Intent intent=new Intent(ProfileActivity.this, LoginActivity.class);
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
                Intent intent_courses = new Intent(this, DashboardActivity.class);
                startActivity(intent_courses);
                break;
            case R.id.nav_synchronisation:
                if(MainActivity.MODE.equals("ONLINE")) {
                    Intent intent_synchronisation = new Intent(this, SynchronisationActivity.class);
                    startActivity(intent_synchronisation);
                }
                break;
            case R.id.nav_unsubscribe:
                confirmUnsubscribe();
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
