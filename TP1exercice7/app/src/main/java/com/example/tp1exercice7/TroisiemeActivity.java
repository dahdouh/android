package com.example.tp1exercice7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//developped by karim dahdouh
public class TroisiemeActivity extends AppCompatActivity {

    String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troisieme);

        tel = getIntent().getStringExtra("tel");

        TextView telTextView = (TextView) findViewById(R.id.telTextView);
        telTextView.append(tel);

        //Bouton  de retour de troisième activité
        Button retourButton = (Button) findViewById(R.id.appelerButtonID);
        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tel));
                startActivity(intent);
                //startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tel, null)));
            }
        });
    }
}
