package com.example.tp1_exercice3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

//devloppped by karim dahdouh
public class CodeJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));


        TextView nomView = new TextView(this);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nomView.setLayoutParams(lparams);
        nomView.setText("Nom : ");
        layout.addView(nomView);

        EditText nomET = new EditText(this);
        nomET.setText(" ");
        layout.addView(nomET);

        TextView prenomView = new TextView(this);
        prenomView.setLayoutParams(lparams);
        prenomView.setText("Prénom : ");
        layout.addView(prenomView);

        EditText prenomET = new EditText(this);
        prenomET.setText(" ");
        layout.addView(prenomET);

        TextView ageView = new TextView(this);
        ageView.setLayoutParams(lparams);
        ageView.setText("Age : ");
        layout.addView(ageView);

        EditText ageET = new EditText(this);
        ageET.setText(" ");
        layout.addView(ageET);


        TextView domainecompView = new TextView(this);
        domainecompView.setLayoutParams(lparams);
        domainecompView.setText("Domaine de compétences : ");
        layout.addView(domainecompView);

        EditText domainecompET = new EditText(this);
        domainecompET.setText(" ");
        layout.addView(domainecompET);

        TextView telView = new TextView(this);
        telView.setLayoutParams(lparams);
        telView.setText("Téléphone : ");
        layout.addView(telView);

        EditText telET = new EditText(this);
        telET.setText(" ");
        layout.addView(telET);

        Button validerBtn = new Button(this);
        validerBtn.setText("Valider");
        layout.addView(validerBtn);

        setContentView(layout);
    }
}
