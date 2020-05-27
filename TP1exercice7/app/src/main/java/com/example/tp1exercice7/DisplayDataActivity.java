package com.example.tp1exercice7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//developped by karim dahdouh
public class DisplayDataActivity extends AppCompatActivity {

    final Context context = this;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        String nom = getIntent().getStringExtra("nom");
        String prenom =  getIntent().getStringExtra("prenom");
        String age =  getIntent().getStringExtra("age");
        String domainecomp =  getIntent().getStringExtra("domainecomp");
        String tel = getIntent().getStringExtra("tel");

        TextView nomTextView = (TextView) findViewById(R.id.nomTextView);
        nomTextView.append(nom);

        TextView prenomTextView = (TextView) findViewById(R.id.prenomTextView);
        prenomTextView.append(prenom);

        TextView ageTextView = (TextView) findViewById(R.id.ageTextView);
        ageTextView.append(age);

        TextView domaineCompTextView = (TextView) findViewById(R.id.domaineCompTextView);
        domaineCompTextView.append(domainecomp);

        TextView telTextView = (TextView) findViewById(R.id.telTextView);
        telTextView.append(tel);

        //Bouton retour
        Button retourButton = (Button) findViewById(R.id.retourButtonID);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        //Bouton Ok qui permet de lancer la nouvelle activité
        Button okButton = (Button) findViewById(R.id.okButtonID);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, TroisiemeActivity.class);
                intent.putExtra("tel", getIntent().getStringExtra("tel"));
                context.startActivity(intent);
            }
        });




    }
}
