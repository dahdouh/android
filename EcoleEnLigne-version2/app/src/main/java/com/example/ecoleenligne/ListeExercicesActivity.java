package com.example.ecoleenligne;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListeExercicesActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener  {

    List<String> cours = new ArrayList<String>();
    List<String> liens = new ArrayList<>();

    String[] test={"moi","mamadou","mabbru"};

    String etat ;

int[] img ={R.drawable.pdf,R.drawable.pdf,R.drawable.pdf};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        ListView listView = (ListView) findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


            /*------------------------ Webservices  ---------------------*/


        // issue the request https://onlineschool.cfapps.io/
        //String url = "https://onlineschool.cfapps.io/api/cours/contenu/"+id;

        String url = MainActivity.IP+"/api/cours/exercice/"+id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);




    }

    @Override
    public void onResponse(JSONArray response) {

        try {

            if(response.length()==0){
                Toast.makeText(ListeExercicesActivity.this,"Aucun Exercice trouvé",Toast.LENGTH_SHORT);
                Toast.makeText(ListeExercicesActivity.this,"Aucun Exercice trouvé",Toast.LENGTH_SHORT);

            }

            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
               // String id = item.getString("_id");
                String message = item.getString("libelle");
                String path = item.getString("id");
                cours.add(message);
                liens.add(path);

                ListView listView = (ListView) findViewById(R.id.listview);

                CustomAdapter customAdapter = new CustomAdapter();
                listView.setAdapter(customAdapter);



            }


        } catch (JSONException error) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(error.toString())
                    .show();
        }
      /*  ArrayList<String> liste = new ArrayList<>();
        for(String c : cours) {
            liste.add(c);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, liste);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(adapter);
        }*/

        
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //toDoListAdapter.setOffres(new ArrayList<Offre>());
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error.getMessage())
                .show();
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return cours.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout,null);
            ImageView image = (ImageView)convertView.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.exercie);
            TextView titre = (TextView)convertView.findViewById(R.id.nbrdone);
            titre.setText(cours.get(position));
            RelativeLayout lb = (RelativeLayout)convertView.findViewById(R.id.rlc);
            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
            if(checkBox.isChecked()==true){
                etat="done";
            }
            else{
                etat="notdone";
            }
            if (etat.equals("done")){
                checkBox.setChecked(true);
            }

            lb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListeExercicesActivity.this, ListeQuestionsActivity.class);
                    String lien = liens.get(position);
                    intent.putExtra("id",lien);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }



}
