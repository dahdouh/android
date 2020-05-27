package com.example.tp1exercice9;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddEvementsActivity extends AppCompatActivity {

    EditText getDate, getNom, getHeure, getDescription;
    String date, nom, heure, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_evements);


        // ajouter l'événement saisi à l'agenda
        getDate = (EditText) findViewById(R.id.editText_date);
        date = getDate.getText().toString();
        getNom = (EditText) findViewById(R.id.editText_nom);
        nom = getNom.getText().toString();
        getHeure = (EditText) findViewById(R.id.editText_heure);
        heure = getHeure.getText().toString();
        getDescription = (EditText) findViewById(R.id.editText_description);
        description = getDescription.getText().toString();

        //ArrayList<Evenement> evenements = (ArrayList<Evenement>) getIntent().getSerializableExtra("evenements");
        //evenements.add(new Evenement(date, heure, nom, description));

        Toast.makeText(this, "L'événement bien ajouté à l'agenda: ", Toast.LENGTH_SHORT).show();

        Button retourButton = (Button) findViewById(R.id.button_ajouterEvenement);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });



    }
}
