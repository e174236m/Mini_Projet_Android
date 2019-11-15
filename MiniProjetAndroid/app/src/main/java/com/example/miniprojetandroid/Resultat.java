package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Resultat extends AppCompatActivity {

    String clientID = "3QDIUJBML1TAXPPLEAY1BHTWKQFKF5G2K1E1ECBEQCVIQWV1";
    String clientSecret = "DVPRNSSYX3PFD42I3BDOGHRCWYJC5V4QQ03WFFSJHZ0DCONM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        ListView listVResultat = findViewById(R.id.listVResultat);

        Bundle extras = getIntent().getExtras();
        final String keyNom = extras.getString("KEY_NOM");
        String ville = extras.getString("VILLE");
        String recherche = extras.getString("RECHERCHE");

        if(keyNom.equals("IS")){

            List<InstallationSportives_Bean> maListe = new ArrayList<InstallationSportives_Bean>();
            final ArrayAdapter monAdapter = new ArrayAdapter<InstallationSportives_Bean>(this, android.R.layout.simple_list_item_1, maListe);
            listVResultat.setAdapter(monAdapter);

            if (!recherche.isEmpty() && recherche != null){
                String URL = "https://nosql-workshop.herokuapp.com/api/installations/search?query="+recherche;

                Ion.with(getApplicationContext()).load(URL).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        Iterator<JsonElement> ite = result.iterator();
                        while(ite.hasNext()) {
                            JsonObject item = ite.next().getAsJsonObject();

                            String nom = item.getAsJsonPrimitive("nom").getAsString();
                            JsonObject jadresse = item.getAsJsonObject("adresse");
                            String commune = jadresse.getAsJsonPrimitive("commune").getAsString();
                            JsonObject jlocation = item.getAsJsonObject("location");
                            String coord = jlocation.getAsJsonArray("coordinates").toString();

                            InstallationSportives_Bean install = new InstallationSportives_Bean(nom, null, commune, coord);
                            monAdapter.add(install);
                        }
                        monAdapter.sort(InstallationSportives_Bean.TRI_PAR_COMMUNE);
                    }
                });

            }else{
                System.out.println("ERREUR VILLE");
            }

        }
        else if(keyNom.equals("VT")){

            List<VoyagesTransports_Bean> maListe = new ArrayList<VoyagesTransports_Bean>();
            final ArrayAdapter monAdapter = new ArrayAdapter<VoyagesTransports_Bean>(this, android.R.layout.simple_list_item_1, maListe);
            listVResultat.setAdapter(monAdapter);

            if (!ville.isEmpty() && ville != null){
                String URL = "https://api.foursquare.com/v2/venues/search?near="+ville+"&query="+choose+"&categoryId=4d4b7105d754a06379d81259&client_id="+clientID+"&client_secret="+clientSecret+"&v=20191104";

                Ion.with(getApplicationContext()).load(URL).asJsonArray().setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        Iterator<JsonElement> ite = result.iterator();
                        while(ite.hasNext()) {
                            JsonObject item = ite.next().getAsJsonObject();

                            String nom = item.getAsJsonPrimitive("nom").getAsString();
                            JsonObject jadresse = item.getAsJsonObject("adresse");
                            String commune = jadresse.getAsJsonPrimitive("commune").getAsString();
                            JsonObject jlocation = item.getAsJsonObject("location");
                            String coord = jlocation.getAsJsonArray("coordinates").toString();

                            InstallationSportives_Bean install = new InstallationSportives_Bean(nom, null, commune, coord);
                            monAdapter.add(install);
                        }
                        monAdapter.sort(InstallationSportives_Bean.TRI_PAR_COMMUNE);
                    }
                });

            }else{
                System.out.println("ERREUR VILLE");
            }

        }



    }
}
