package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;

public class Resultat extends AppCompatActivity {

    String clientID = "3QDIUJBML1TAXPPLEAY1BHTWKQFKF5G2K1E1ECBEQCVIQWV1";
    String clientSecret = "DVPRNSSYX3PFD42I3BDOGHRCWYJC5V4QQ03WFFSJHZ0DCONM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        final ListView listVResultat = findViewById(R.id.listVResultat);
        final ArrayAdapter monAdapter;
        final Button btFavories = findViewById(R.id.btFavories);

        Bundle extras = getIntent().getExtras();
        final String keyNom = extras.getString("KEY_NOM");
        String ville = extras.getString("VILLE");
        String recherche = extras.getString("RECHERCHE");

        final ProgressDialog dialog = new ProgressDialog(Resultat.this);
        dialog.setTitle("Chargement");
        dialog.show();

        if("IS".equals(keyNom)){

            List<InstallationSportives_Bean> maListe = new ArrayList<InstallationSportives_Bean>();
            monAdapter = new ArrayAdapterPerso(Resultat.this, maListe);
            listVResultat.setAdapter(monAdapter);

            if (!recherche.isEmpty() && recherche != null){

                String newRecherche = "";
                for(int i=0; i < recherche.length(); i++)
                {
                    if(recherche.charAt(i) == ' ') {
                        newRecherche = newRecherche + "%20";
                    }else{
                        newRecherche = newRecherche + recherche.charAt(i);
                    }
                }

                String URL = "https://nosql-workshop.herokuapp.com/api/installations/search?query="+newRecherche;

                Ion.with(getApplicationContext())
                        .load(URL)
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        if(result != null && result.size() > 0){
                            System.out.println("RESULTAT IS : " +result);

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            Iterator<JsonElement> iteIS = result.iterator();

                            if(iteIS != null) {
                                while (iteIS.hasNext()) {
                                    JsonObject item = iteIS.next().getAsJsonObject();

                                    String id = item.getAsJsonPrimitive("_id").getAsString();
                                    String nom = item.getAsJsonPrimitive("nom").getAsString();

                                    JsonObject jadresse = item.getAsJsonObject("adresse");
                                    String lieuNumero = jadresse.getAsJsonPrimitive("numero").getAsString();
                                    String lieuVoie = jadresse.getAsJsonPrimitive("voie").getAsString();
                                    String lieuCodePostal = jadresse.getAsJsonPrimitive("codePostal").getAsString();
                                    String lieuCommune = jadresse.getAsJsonPrimitive("commune").getAsString();
                                    String lieu = lieuNumero + " " + lieuVoie + " " + lieuCodePostal + " " + lieuCommune;

                                    JsonObject jlocation = item.getAsJsonObject("location");
                                    String longitude = jlocation.getAsJsonArray("coordinates").get(0).toString();
                                    String latitude = jlocation.getAsJsonArray("coordinates").get(1).toString();
                                    System.out.println("LAT : " + latitude + " LONG : " + longitude);

                                    InstallationSportives_Bean install = new InstallationSportives_Bean(id, nom, null, lieu, latitude, longitude);
                                    monAdapter.add(install);
                                }
                                monAdapter.sort(InstallationSportives_Bean.TRI_PAR_COMMUNE);
                            }
                        }
                        else {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Toast.makeText(Resultat.this, "Aucun résultat trouvé !", Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }else{
                System.out.println("ERREUR RECHERCHE");
            }

        }
        else if(keyNom.equals("VT")){

            List<VoyagesTransports_Bean> maListe = new ArrayList<VoyagesTransports_Bean>();
            monAdapter = new ArrayAdapterPerso(Resultat.this, maListe);
            listVResultat.setAdapter(monAdapter);

            if ((!ville.isEmpty() && ville != null) || (!recherche.isEmpty() && recherche != null)){

                String newRecherche = "";
                String newVille = "";
                try {
                    for(int i=0; i < recherche.length(); i++)
                    {
                        if(recherche.charAt(i) == ' ') {
                            newRecherche = newRecherche + "%20";
                        }else{
                            newRecherche = newRecherche + recherche.charAt(i);
                        }
                    }

                    for(int j=0; j < ville.length(); j++)
                    {
                        if(ville.charAt(j) == ' ') {
                            newVille = newVille + "%20";
                        }else{
                            newVille = newVille + ville.charAt(j);
                        }
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                String URL2 = "https://api.foursquare.com/v2/venues/search?near="+newVille+"&query="+newRecherche+"&categoryId=4d4b7105d754a06379d81259&client_id="+clientID+"&client_secret="+clientSecret+"&v=20191104";

                Ion.with(getApplicationContext())
                        .load("GET", URL2)
                        .asJsonObject()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> response) {

                        if (response != null) {

                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            Iterator<JsonElement> ite = null;

                            try {
                                if(response.getResult().getAsJsonObject("response").getAsJsonArray("venues").size() > 0){
                                    ite = response.getResult().getAsJsonObject("response").getAsJsonArray("venues").iterator();
                                } else {
                                    Toast.makeText(Resultat.this, "Aucun résultat trouvé !", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e2) {
                                Toast.makeText(Resultat.this, "Aucun résultat trouvé !", Toast.LENGTH_LONG).show();
                            }
                            if(ite != null){
                                while(ite.hasNext()) {

                                    try {
                                        JsonObject item = ite.next().getAsJsonObject();

                                        String id = item.getAsJsonPrimitive("id").getAsString();
                                        String nom = item.getAsJsonPrimitive("name").getAsString();

                                        String latitude = item.getAsJsonObject("location").getAsJsonPrimitive("lat").getAsString();
                                        String longitude = item.getAsJsonObject("location").getAsJsonPrimitive("lng").getAsString();

                                        JsonArray adresse = item.getAsJsonObject("location").getAsJsonArray("formattedAddress");

                                        String lieuNumeroVoie = adresse.get(0).getAsString();
                                        String lieuCodePostalCommune = adresse.get(1).getAsString();
                                        String lieu = lieuNumeroVoie+" "+lieuCodePostalCommune;

                                        String imgPrefix =item.getAsJsonArray("categories").get(0).getAsJsonObject().getAsJsonObject("icon").getAsJsonPrimitive("prefix").getAsString();
                                        String imgSuffix = item.getAsJsonArray("categories").get(0).getAsJsonObject().getAsJsonObject("icon").getAsJsonPrimitive("suffix").getAsString();
                                        String img = imgPrefix+"64"+imgSuffix;

                                        VoyagesTransports_Bean install = new VoyagesTransports_Bean(id, nom, null, lieu, latitude, longitude, img);

                                        monAdapter.add(install);

                                    } catch (Exception a) {
                                        a.printStackTrace();
                                    }
                                }
                                monAdapter.sort(VoyagesTransports_Bean.TRI_PAR_COMMUNE);
                            }
                        }
                    }
                });
            }else{
                System.out.println("ERREUR RECHERCHE");
            }
        }

        listVResultat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Favories_Bean oneFavori = (Favories_Bean) listVResultat.getAdapter().getItem(position);
                Intent intent = new Intent(Resultat.this, Detail.class);

                if(oneFavori instanceof InstallationSportives_Bean){
                    intent.putExtra("KEY", "IS");
                    intent.putExtra("OBJECT", oneFavori);
                } else { //VoyagesTransports_Bean
                    intent.putExtra("KEY", "VT");
                    intent.putExtra("OBJECT", oneFavori);
                }
                startActivity(intent);

            }
        });

        btFavories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rq = new Intent(Resultat.this, Favorie.class);
                startActivity(rq);

            }
        });





    }
}
