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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.Compte;
import com.example.ecoleenligne.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.LongToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    SharedPreferences sharedpreferences;
    boolean register = false;
    TextInputLayout login, password, firstname, lastname, email, tel, ville;
    String login_data, password_data, firstname_data, lastname_data, email_data, tel_data, ville_data;
    String userAlreadyExist ="";
    TextView msg_error;

    /*------------------ Menu ----------------*/
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


         //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*------------------------ Menu ---------------------*/
        /*drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);

        navigationView.bringToFront();
        toolbar=findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_darawer_open, R.string.navigation_darawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

         */

        /*------------------ Form validation ----------------*/

        login = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        ville = findViewById(R.id.ville);
        msg_error = findViewById(R.id.msg_error);

        final Button register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!validateFirstname() || !validateLastname() || !validateEmail() || !validateTel() || !validateVille() ||
                    !validateLogin() || !validatePassword()){
                    return;
                } else {
                    Compte compte = new Compte(login_data, password_data);
                    User user = new User(firstname_data, lastname_data, email_data, tel_data, ville_data);

                    //call rest webservice
                    registerRest(user, compte);
                }
            }
        });

        /*-------------- if user have already an account:  Signin -------------*/
        final Button haveAcount_btn = findViewById(R.id.haveAcount_btn);
        haveAcount_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        context.startActivity(intent);
            }
        });


    }

    /*------------------ save data in server database ----------------*/
    public void registerRest(User user, Compte compte) {
        String url = MainActivity.IP+"/register/"+compte.getLogin()+"/"+compte.getPassword()+"/"+user.getFirstname()+"/"+user.getLastname()+"/"+user.getEmail()+"/"+user.getTel()+"/"+user.getVille();
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    userAlreadyExist = response.getString("firstname");
                    if(userAlreadyExist.equals("already exist")) {
                        msg_error.setText(getString(R.string.register_user_alreadyexist));
                        Toast.makeText(context, getString(R.string.register_user_alreadyexist), Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(RegisterActivity.this, WelcomeActivationActivity.class);
                        context.startActivity(intent);
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
/*
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }
 */
    private Boolean validateLogin() {
        login_data = login.getEditText().getText().toString();
        if (login_data.isEmpty()) {
            login.setError(getString(R.string.register_login_validat_empty));
            return false;
        } else {
            login.setError(null);
            login.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        password_data = password.getEditText().getText().toString();
        if (password_data.isEmpty()) {
            password.setError(getString(R.string.register_password_validat_empty));
            return false;
        } else {

            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password_data);
            if(!matcher.matches() && password_data.length()<4) {
                password.setError(getString(R.string.register_password_validat_regex));
                return false;
            }else {
                return true;
            }
        }
    }

    private Boolean validateFirstname() {
        firstname_data = firstname.getEditText().getText().toString();
        if (firstname_data.isEmpty()) {
            firstname.setError(getString(R.string.register_firstname_validat_empty));
            return false;
        } else {
            firstname.setError(null);
            firstname.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateLastname() {
        lastname_data = lastname.getEditText().getText().toString();
        if (lastname_data.isEmpty()) {
            lastname.setError(getString(R.string.register_lastname_validat_empty));
            return false;
        } else {
            lastname.setError(null);
            lastname.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        email_data = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email_data.isEmpty()) {
            email.setError(getString(R.string.register_email_validat_empty));
            return false;
        } else if (!email_data.matches(emailPattern)) {
            email.setError(getString(R.string.register_email_validat_invalid));
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateTel() {
        tel_data = tel.getEditText().getText().toString();
        if (tel_data.isEmpty()) {
            tel.setError(getString(R.string.register_tel_validat_empty));
            return false;
        } else {
            tel.setError(null);
            tel.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateVille() {
        ville_data = ville.getEditText().getText().toString();
        if (ville_data.isEmpty()) {
            ville.setError(getString(R.string.register_ville_validat_empty));
            return false;
        } else {
            ville.setError(null);
            ville.setErrorEnabled(false);
            return true;
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
                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
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

                    Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
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

    /*
    @Override
    public void onResponse(JSONArray response) {
        try {
            //offres = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
                // test if the user is already exist.
                userAlreadyExist = item.getString("firstname");
                Toast.makeText(this, getString(R.string.login_username) +" ############ ?????? "+ firstname , Toast.LENGTH_LONG).show();
            }
            //toDoListAdapter.setOffres(offres);
        } catch (JSONException error) {
            //new AlertDialog.Builder(this)
            //        .setTitle("Error")
            //        .setMessage(error.toString())
            //        .show();

        }
    }

     */

    /*
    @Override
    public void onErrorResponse(VolleyError error) {
        //toDoListAdapter.setOffres(new ArrayList<Offre>());

          //  new AlertDialog.Builder(this)
          //     .setTitle("Error")
          //      .setMessage(error.getMessage())
          //      .show();


    }
    */

}
