package com.example.tp2exercice2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

//developped by Karim Dahdouh
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

        contacts.add("Dahdouh_Karim_0654365478");
        contacts.add("Michel_Colucci_0765465309");

        final Button button = findViewById(R.id.validerID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                compteur++;

                getNom = (EditText) findViewById(R.id.nomID);
                nom = getNom.getText().toString();
                getPrenom = (EditText) findViewById(R.id.prenomID);
                prenom = getPrenom.getText().toString();
                getTel = (EditText) findViewById(R.id.telID);
                tel = getTel.getText().toString();

                // ajouter les informations saisies à la liste de contacts
                contacts.add(nom+"_"+prenom+"_"+tel);
                Intent intent = new Intent(context, ListContactActivity.class);
                intent.putExtra("contacts", contacts);

                Toast.makeText(context, " compteur = "+ compteur, Toast.LENGTH_SHORT).show();

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
        Toast.makeText(this, " l'état de l'activité est sauvegardé (onSaveInstanceState)", Toast.LENGTH_SHORT).show();
    }

    //  restaurer l'état de l'application en  cas de retour.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        compteur = savedInstanceState.getInt("compteur");
        contacts = savedInstanceState.getStringArrayList("contact");

        Toast.makeText(this, " l'état de l'activité est restauré (onRestoreInstanceState)", Toast.LENGTH_SHORT).show();
    }

}
