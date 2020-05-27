package com.example.tp2exercice1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Intent intent;

    EditText getNom, getPrenom, getTel;
    String nom, prenom, tel;

    ArrayList<String> contacts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button = findViewById(R.id.validerID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                contacts.add("Dahdouh_Karim_0654365478");
                contacts.add("Michel_Colucci_0765465309");

                getNom = (EditText) findViewById(R.id.nomID);
                nom = getNom.getText().toString();
                getPrenom = (EditText) findViewById(R.id.prenomID);
                prenom = getPrenom.getText().toString();
                getTel = (EditText) findViewById(R.id.telID);
                tel = getTel.getText().toString();

                Intent intent = new Intent(context, ListContactActivity.class);
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                intent.putExtra("tel", tel);
                intent.putExtra("contacts", contacts);

                context.startActivity(intent);

            }
        });

    }
}
