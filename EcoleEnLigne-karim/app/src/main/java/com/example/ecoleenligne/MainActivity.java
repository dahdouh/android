package com.example.ecoleenligne;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1000;
    final Context context = this;
    //Variables
    ImageView image;
    TextView logo, slogan;
    Animation topAnimation, bottomAnimation;

    SharedPreferences sharedpreferences;


    /*--------------- user connected session ------*/
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Login = "loginKey";
    public static final String Password = "passwordKey";
    public static final String Email = "emailKey";
    public static final String Id = "idKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //add annimation
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        image.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        // after 5 seconds, DahsboardActivity will open automatically
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                //Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                /*--------------get user from session --------------*/
                sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                String user_connected_id = sharedpreferences.getString(MainActivity.Id, null);
                String user_connected_login = sharedpreferences.getString(MainActivity.Login, null);

                /*-------------- check if the user is connected or not--------------*/
                Intent intent;
                if(user_connected_id == null && user_connected_login == null) {
                     intent=new Intent(MainActivity.this, LoginActivity.class);
                } else {
                   intent=new Intent(MainActivity.this, DashboardParentActivity.class);
                }

                // Attach all the elements those you want to animate in design
                Pair[]pairs=new Pair[2];
                pairs[0]=new Pair<View, String>(image,"logo_image");
                pairs[1]=new Pair<View, String>(logo,"logo_text");

                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
                {ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent,options.toBundle());
                }
            }
        },SPLASH_SCREEN);

        /*
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //Intent myintent = new Intent(MainActivity.this, LoginActivity.class);
                //startActivity(myintent);
            }
        };
    */
        //Button button = (Button) findViewById(R.id.login_button);
        //button.setOnClickListener(onClickListener);
    }


}
