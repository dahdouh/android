package com.example.tp2exercice3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

//développed by Karim Dahdouh
public class ListContactActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> contacts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);

        //lire les contacts à partir du fichier sauvegardé par l'activité principale.
        String nom_fichier = "fichier.txt";
        String text = "";

        try {

            //SOLUTION 1
            FileInputStream fis = this.openFileInput(nom_fichier);
            ObjectInputStream in = new ObjectInputStream(fis);
            contacts = (ArrayList<String>) in.readObject();
            in.close();
            fis.close();


            //SOLUTION 2
           /*
            FileInputStream fis = this.openFileInput(nom_fichier);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
           while ((line = bufferedReader.readLine()) != null) {
                contacts.add(line);
                sb.append(line);
                Toast.makeText(getApplicationContext(), "####### "+line, Toast.LENGTH_SHORT).show();
            }
            */

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




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
}
