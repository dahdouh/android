package com.example.tp1exercice5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//developped by karim dahdouh
public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.validerID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                //le titre de dialogue
                alertDialogBuilder.setTitle("Dialogue bouton valider");

                //spécifier le message de dialogue
                alertDialogBuilder
                        .setMessage("Cliquer yes pour afficher le message Toast ou sur No pour quitter !")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                //Afficher le Toast message
                                Toast.makeText(context, "Votre Action à bien été validée", Toast.LENGTH_LONG).show();
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

            }
        });
    }
}
