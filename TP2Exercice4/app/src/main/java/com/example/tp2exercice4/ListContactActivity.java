package com.example.tp2exercice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListContactActivity extends AppCompatActivity {

    ListView listview;
    List<String> contacts = new ArrayList<String>();

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialiser l'objet sqLiteHelper
        sqLiteHelper= new SQLiteHelper(this);
        setContentView(R.layout.activity_list_contact);

        listview = (ListView) findViewById(R.id.listview);

        //récupérer les données saisies dans l'activité principale
        String nom = getIntent().getStringExtra("nom");
        String prenom = getIntent().getStringExtra("prenom");
        String tel = getIntent().getStringExtra("tel");

        //insérer les données dans la table de contact
        sqLiteHelper.insertContact(sqLiteHelper.getDatabase(), nom, prenom, tel);

        //extraire la liste de contacts
        contacts = sqLiteHelper.getContactsFromDb();

        //attribuer au composant listview la liste de contacs récupérer à partir de la base de données
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListContactActivity.this,
                android.R.layout.simple_list_item_1, contacts);
        listview.setAdapter(adapter);

        Button retourButton = (Button) findViewById(R.id.retourButtonID);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });







    }
}
