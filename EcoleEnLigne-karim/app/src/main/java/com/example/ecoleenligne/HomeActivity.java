package com.example.ecoleenligne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ecoleenligne.model.Item;
import com.example.ecoleenligne.model.Offre;
import com.example.ecoleenligne.model.ToDoItem;
import com.example.ecoleenligne.util.CustomListAdapter;
import com.example.ecoleenligne.util.ToDoListAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener  {

    private ToDoListAdapter toDoListAdapter;
    ListView listview;

    List<Offre> offres = new ArrayList<Offre>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        /*------------------------ Webservices  ---------------------*/
        ArrayList<Offre> offres = new ArrayList<Offre>();
        this.toDoListAdapter = new ToDoListAdapter(this, offres);
        ListView toDoItemsListView = findViewById(R.id.to_do_items);
        toDoItemsListView.setAdapter(toDoListAdapter);

        // issue the request
        String url = "https://hotel-webservices.cfapps.io/restful/offres/karim/dahdouh/2020-03-05/2020-03.09/1";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest microPostsRequest = new JsonArrayRequest(url, this, this);
        queue.add(microPostsRequest);


        /*
        ArrayList<String> itemsArrayList = new ArrayList<String>();
        itemsArrayList.add("eeeeeeeee");
        itemsArrayList.add("bbb");
        itemsArrayList.add("ffffffff");

        ToDoListAdapter toDoListAdapter = new ToDoListAdapter(this, itemsArrayList);
        ListView toDoItemsListView = (ListView) findViewById(R.id.to_do_items);
        toDoItemsListView.setAdapter(toDoListAdapter);

    */

        // simple listview
      /*  ArrayList<String> contacts = new ArrayList<String>();
        contacts.add("eeee");
        listview = (ListView) findViewById(R.id.to_do_items);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, contacts);
        listview.setAdapter(adapter);
       */

        // Setup the data source
        //ArrayList<Item> itemsArrayList = generateItemsList(); // calls function to get items list
     /* customer list adapter
        ArrayList<Item> itemsArrayList = generateItemsList();
        CustomListAdapter adapter = new CustomListAdapter(this, itemsArrayList);
        ListView itemsListView  = (ListView) findViewById(R.id.list_view_items);
        itemsListView.setAdapter(adapter);

      */
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            offres = new ArrayList<>(response.length());
            for (int i = 0; i < response.length(); i++) {
                JSONObject item = response.getJSONObject(i);
               // String id = item.getString("_id");
                String message = item.getString("nomHotel");
                offres.add(new Offre(message));
                Toast.makeText(this, getString(R.string.login_username) +" ############ "+ message , Toast.LENGTH_LONG).show();
            }
            toDoListAdapter.setOffres(offres);
        } catch (JSONException error) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(error.toString())
                    .show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        toDoListAdapter.setOffres(new ArrayList<Offre>());
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(error.getMessage())
                .show();
    }




}
