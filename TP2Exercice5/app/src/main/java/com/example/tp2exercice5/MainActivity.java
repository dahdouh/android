package com.example.tp2exercice5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    List<String> contacts_fichier = new ArrayList<String>();
    List<String> contacts_sqlite = new ArrayList<String>();
    SQLiteHelper sqLiteHelper;
    ListView listview;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instancier l'objet sqLiteHelper
        sqLiteHelper= new SQLiteHelper(this);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listview);


        contacts_fichier.add("dahdouh_karim_0754358742");
        contacts_fichier.add("Michel_Colucci_0765435678");
        contacts_fichier.add("Lionardo_davinchi_0785436549");

        //enregistrer la liste de contacts dans le fichier
        String nom_fichier = "fichier.txt";
        try {
            FileOutputStream fos = context.openFileOutput(nom_fichier, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(contacts_fichier);
            out.close();
            fos.close();
            Toast.makeText(getApplicationContext(), "fichier est bien créé et la liste des contacts ajoutée au fichier", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Au début, la table contacts de la base de données SQLite est vide.
        //extraire la liste de contacts
        contacts_sqlite = sqLiteHelper.getContactsFromDb();

        //attribuer au composant listview la liste de contacs récupérer à partir de la base de données
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, contacts_sqlite);
        listview.setAdapter(adapter);


        final Button button = findViewById(R.id.synchnisationButtonID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //démarrer le service de synchronisation
                Intent intent = new Intent(context, SynchronisationService.class);
                startService(intent);

                Toast.makeText(context, "Cela prend un peu de temps. Patientez!...", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "La synchronisation est bien faite.", Toast.LENGTH_SHORT).show();

                //recharger la liste de contacts après la synchronisation avec les contacts du fichier
                contacts_sqlite = sqLiteHelper.getContactsFromDb();
                 adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1, contacts_sqlite);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //Sauvegarder l'état de lactivité principale
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //savaugarder la liste de contacts
        //savedInstanceState.putStringArrayList("contacts", contacts);
    }

    //Restaurer l'état de l'activité principale
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //contacts = savedInstanceState.getStringArrayList("contact");

    }

}
