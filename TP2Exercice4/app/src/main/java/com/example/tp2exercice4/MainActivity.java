package com.example.tp2exercice4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Intent intent;
    ArrayList<String> contacts = new ArrayList<String>();
    int compteur =0;
    EditText getNom, getPrenom, getTel;
    String nom, prenom, tel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.validerID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                compteur++;
                //recupérer les données saisies
                getNom = (EditText) findViewById(R.id.nomID);
                nom = getNom.getText().toString();
                getPrenom = (EditText) findViewById(R.id.prenomID);
                prenom = getPrenom.getText().toString();
                getTel = (EditText) findViewById(R.id.telID);
                tel = getTel.getText().toString();

                //passer les données saisies à la deuxième activité
                Intent intent = new Intent(context, ListContactActivity.class);
                intent.putExtra("nom", nom);
                intent.putExtra("prenom", prenom);
                intent.putExtra("tel", tel);
                context.startActivity(intent);

            }
        });
    }

    //Sauvegarder l'état de lactivité principale
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savaugarder la liste de contacts
        savedInstanceState.putStringArrayList("contacts", contacts);
        //sauvegarder le compteur (nombre de fois que l'acitivité Main est affichée)
        savedInstanceState.putInt("compteur", compteur);
    }

    //Restaurer l'état de l'activité principale
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        compteur = savedInstanceState.getInt("compteur");
        contacts = savedInstanceState.getStringArrayList("contact");

    }
}
