package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RechercheVT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_vt);

        TextView textRechercheNom = findViewById(R.id.textRechercheNom);
        final EditText editRechercheVille = findViewById(R.id.editRechercheVille);
        final EditText editRechercheR = findViewById(R.id.editRechercheR);
        Button btRechercheGO = findViewById(R.id.btRechercheGO);

        Bundle extras = getIntent().getExtras();
        String nom = extras.getString("NOM");
        textRechercheNom.setText(nom);

        btRechercheGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ville = editRechercheVille.getText().toString();
                String recherche = editRechercheR.getText().toString();

                Intent rq = new Intent(RechercheVT.this, Resultat.class);
                rq.putExtra("KEY_NOM", "VT");
                rq.putExtra("VILLE", ville);
                rq.putExtra("RECHERCHE", recherche);
                startActivity(rq);

            }
        });




    }
}
