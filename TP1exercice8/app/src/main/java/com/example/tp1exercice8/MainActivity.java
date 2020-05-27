package com.example.tp1exercice8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//developped by karim dahdouh
public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Intent intent;

    EditText getDepart, getArrive;
    String depart, arrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.rechercheButtonID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* cherafficher la liste des horaires de trains       */
                getDepart = (EditText) findViewById(R.id.departEditText);
                depart = getDepart.getText().toString();
                getArrive = (EditText) findViewById(R.id.arriveEditText);
                arrive = getArrive.getText().toString();

                intent = new Intent(context, ListHoraireTrainActivity.class);
                intent.putExtra("depart",depart);
                intent.putExtra("arrive",arrive);
                context.startActivity(intent);




            }
        });

    }
}
