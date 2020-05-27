package com.example.tp1exercice9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    ListView listview;
    Intent intent;

    Evenement[] listEvents = new Evenement[]{
            new Evenement("14 Février 2020", "23h", "TP Android", "dérnier délai envoi tp"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //premiere solution: une listview simple
        /*
        listview representant l'agenda
        listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<Evenement> adapter = new ArrayAdapter<Evenement>(MainActivity.this, android.R.layout.simple_list_item_1, listEvents);
        listview.setAdapter(adapter);
        */

        // deuxieme solution: une listview personnalisée
        final ArrayList<Evenement> listevenements = getListData();
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new CustomListAdapter(this, listevenements));


        final Button button = findViewById(R.id.addEventButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //intent.putExtra("evenements", new ArrayList<Evenement>());
                intent = new Intent(context, AddEvementsActivity.class);
                context.startActivity(intent);
            }
        });

    }

    private  ArrayList<Evenement> getListData() {
        ArrayList<Evenement> list = new ArrayList<Evenement>();
        Evenement event1 = new Evenement("14 Février", "23h", "TP Android", "dérnier délai envoi tp");
        Evenement event2 = new Evenement("05 Mai", "14h", "Projet android", "...");
        Evenement event3 = new Evenement("25 Decembre", "00h", "Noel", "fêtes ...");


        list.add(event1);
        list.add(event2);
        list.add(event3);

        return list;
    }


}
