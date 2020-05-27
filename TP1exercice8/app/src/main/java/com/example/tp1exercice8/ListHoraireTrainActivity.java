package com.example.tp1exercice8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


//develeopped by karim dahdouh
public class ListHoraireTrainActivity extends AppCompatActivity {

    ListView listview;
    private String[] horaires = new String[]{
            "05:15 -> 09:11, 3h 56", "05:35 -> 09:57, 4h 22",
            "06:30 -> 11:23, 4h 13", "11:45 -> 16:11, 4h 26",
            "11:58 -> 16:11, 4h26", "13:57 -> 18:02, 4h 12",
            "15:20 -> 20:21, 5h 02", "05:15 -> 21:14, 3h 53",
            "18:50 -> 23:14, 4h 24", "18:21 -> 22:20, 3h 59"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_horaire_train);

        String depart = getIntent().getStringExtra("depart");
        String arrive = getIntent().getStringExtra("arrive");
        TextView departTextView = (TextView) findViewById(R.id.departTextView);
        departTextView.append(depart);
        TextView arriveTextView = (TextView) findViewById(R.id.arriveTextView);
        arriveTextView.append(arrive+" ");

        //alimenter la listview avec données de horaires de trains
        listview = (ListView) findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListHoraireTrainActivity.this, android.R.layout.simple_list_item_1, horaires);
        listview.setAdapter(adapter);

    }
}
