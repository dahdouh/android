package com.example.tp2exercice2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

//developped by Karim Dahdouh
public class ListContactActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);


        // my_child_toolbar is defined in the layout file

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        contacts = getIntent().getStringArrayListExtra("contacts");

        listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListContactActivity.this, android.R.layout.simple_list_item_1, contacts);
        listview.setAdapter(adapter);

        Button retourButton = (Button) findViewById(R.id.retourButtonID);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    //sauvegarder l'état de l'activité
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("contacts", contacts);

        Toast.makeText(this, " l'état de l'activité est sauvegardé (onSaveInstanceState)", Toast.LENGTH_SHORT).show();
    }

    // restaurer l'état de l'activité en cas de retour.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        contacts = savedInstanceState.getStringArrayList("contact");

        Toast.makeText(this, " l'état de l'activité est restauré (onRestoreInstanceState)", Toast.LENGTH_SHORT).show();
    }



}
