package com.example.tp1_exercice6;

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

import java.security.DomainCombiner;
//developped by karim dahdouh
public class MainActivity extends AppCompatActivity {

    final Context context = this;
    Intent intent;
    //private Button button;

    EditText getNom, getPrenom, getAge, getDomaineComp, getTel;
    String nom, prenom, age, domaineComp, tel;

    //utilisé par intent pour passer les données au deuxième activité pour les affichées.
    public static final String NOM_MESSAGE = null, PRENOM_MESSAGE = null, AGE_MESSAGE = null, DOMAINECOMP_MESSAGE = null, TEL_MESSAGE = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.validerID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* recupérer les données du formulaire, y compris!
                 * nom, prenom, tel, domaine de compétences et téléphone
                 */
                getNom = (EditText) findViewById(R.id.nomID);
                nom = getNom.getText().toString();
                getPrenom = (EditText) findViewById(R.id.prenomID);
                prenom = getPrenom.getText().toString();
                getAge = (EditText) findViewById(R.id.ageID);
                age = getAge.getText().toString();
                getDomaineComp = (EditText) findViewById(R.id.domainecompID);
                domaineComp = getDomaineComp.getText().toString();
                getTel = (EditText) findViewById(R.id.telID);
                tel = getTel.getText().toString();




                Intent intent = new Intent(context, DisplayDataActivity.class);
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                intent.putExtra("age", age);
                intent.putExtra("domainecomp", domaineComp);
                intent.putExtra("tel", tel);

                context.startActivity(intent);

                Toast.makeText(context, nom+" "+prenom+"age: "+ age +" domaine comp: "+ domaineComp +" "+ tel , Toast.LENGTH_LONG).show();

            }
        });
    }
}
