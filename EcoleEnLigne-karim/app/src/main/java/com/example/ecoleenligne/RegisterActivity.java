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
    boolean register = false;
    TextInputLayout login, password, firstname, lastname, email, facebook, twitter;
    String login_data, password_data, firstname_data, lastname_data, email_data, facebook_data, twitter_data;
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


         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.tweeter);
        msg_error = findViewById(R.id.msg_error);

        final Button register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!validateFirstname() || !validateLastname() || !validateEmail() || !validateFacebook() || !validateTweeter() ||
                    !validateLogin() || !validatePassword()){
                    return;
                } else {
                    Compte compte = new Compte(login_data, password_data);
                    User user = new User(firstname_data, lastname_data, email_data, facebook_data, twitter_data);

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
        //String url = "https://onlineschool.cfapps.io/register/"+compte.getLogin()+"/"+compte.getPassword()+"/"+user.getFirstname()+"/"+user.getLastname()+"/"+user.getEmail()+"/"+user.getFacebook()+"/"+user.getTwitter();
        String url = "http://192.168.43.203:8085/register/"+compte.getLogin()+"/"+compte.getPassword()+"/"+user.getFirstname()+"/"+user.getLastname()+"/"+user.getEmail()+"/"+user.getFacebook()+"/"+user.getTwitter();
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

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

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
    private Boolean validateFacebook() {
        facebook_data = facebook.getEditText().getText().toString();
        if (facebook_data.isEmpty()) {
            facebook.setError(getString(R.string.register_facebook_validat_empty));
            return false;
        } else {
            facebook.setError(null);
            facebook.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateTweeter() {
        twitter_data = twitter.getEditText().getText().toString();
        if (twitter_data.isEmpty()) {
            twitter.setError(getString(R.string.register_tweeter_validat_empty));
            return false;
        } else {
            twitter.setError(null);
            twitter.setErrorEnabled(false);
            return true;
        }
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
                menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);
                menu.findItem(R.id.nav_login).setVisible(true);
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
