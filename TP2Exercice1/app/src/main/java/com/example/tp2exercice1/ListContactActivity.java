package com.example.tp2exercice1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListContactActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        String nom = getIntent().getStringExtra("nom");
        String prenom =  getIntent().getStringExtra("prenom");
        String tel = getIntent().getStringExtra("tel");

        ArrayList<String> contacts = getIntent().getStringArrayListExtra("contacts");

        // ajouter les informations de nouveau contact à la liste de contacts qui existe déjà.
        contacts.add(nom+"_"+prenom+"_"+tel);


        Toast.makeText(this, "contact: "+nom+"_"+prenom+"_"+tel,
                Toast.LENGTH_SHORT).show();

        listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListContactActivity.this, android.R.layout.simple_list_item_1, contacts);
        listview.setAdapter(adapter);

    }
}
