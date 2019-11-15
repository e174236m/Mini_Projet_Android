package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_accueil);

        final Button btISport = findViewById(R.id.btISport);
        final Button btVTransport = findViewById(R.id.btVTransport);

        btISport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rq = new Intent(Accueil.this, RechercheIS.class);
                rq.putExtra("NOM", btISport.getText().toString());
                startActivity(rq);

            }
        });

        btVTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rq = new Intent(Accueil.this, RechercheVT.class);
                rq.putExtra("NOM", btVTransport.getText().toString());
                startActivity(rq);

            }
        });


    }
}
