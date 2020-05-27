package com.example.tp1exercice7;

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
    //private Button button;

    EditText getNom, getPrenom, getAge, getDomaineComp, getTel;
    String nom, prenom, age, domaineComp, tel;

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

                intent = new Intent(context, DisplayDataActivity.class);
                intent.putExtra("nom",nom);
                intent.putExtra("prenom",prenom);
                intent.putExtra("age", age);
                intent.putExtra("domainecomp", domaineComp);
                intent.putExtra("tel", tel);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                //le titre de dialogue
                alertDialogBuilder.setTitle("validation de données");
                //spécifier le message de dialogue
                alertDialogBuilder
                        .setMessage("Cliquer Oui pour passer à l'activité suivante ou sur No pour quitter !")
                        .setCancelable(false)
                        .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Afficher le Toast message
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // fermer le dialogue
                                dialog.cancel();
                            }
                        });
                //creer un dialogue d'alert
                AlertDialog alertDialog = alertDialogBuilder.create();
                //afficher le dialogue
                alertDialog.show();

                Toast.makeText(context, nom+" "+prenom+"age: "+ age +" domaine comp: "+ domaineComp +" "+ tel , Toast.LENGTH_LONG).show();

            }
        });
    }
}
