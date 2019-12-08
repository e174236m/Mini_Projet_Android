package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Favorie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorie);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        final ListView listVFavories = findViewById(R.id.listVFavories);
        final ArrayAdapter monAdapter;
        final Button btRemoveAll = findViewById(R.id.btRemoveAll);

        Set<String> setFavories = preferences.getStringSet("setFavories", null);
        //System.out.println("SET FAVORIES (onglet favories) : "+setFavories);

        String clientID = "3QDIUJBML1TAXPPLEAY1BHTWKQFKF5G2K1E1ECBEQCVIQWV1";
        String clientSecret = "DVPRNSSYX3PFD42I3BDOGHRCWYJC5V4QQ03WFFSJHZ0DCONM";

        final ProgressDialog dialog = new ProgressDialog(Favorie.this);
        dialog.setTitle("Chargement");
        dialog.show();

        if(setFavories != null && setFavories.size() > 0){
            List<Favories_Bean> maListe = new ArrayList<Favories_Bean>();
            monAdapter = new ArrayAdapterPerso(Favorie.this, maListe);
            listVFavories.setAdapter(monAdapter);

            Iterator iter = setFavories.iterator();

            while (iter.hasNext()) {
                String[] parts = iter.next().toString().split("-");
                String prefixRecherche = parts[0];
                String idRecherche = parts[1];

                if("IS".equals(prefixRecherche)){

                    if (!idRecherche.isEmpty() && idRecherche != null){
                        String URL = "https://nosql-workshop.herokuapp.com/api/installations/"+idRecherche;

                        Ion.with(getApplicationContext()).load(URL).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                if(result != null) {

                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }

                                    try {
                                        String id = result.getAsJsonPrimitive("_id").getAsString();
                                        String nom = result.getAsJsonPrimitive("nom").getAsString();

                                        JsonObject jadresse = result.getAsJsonObject("adresse");
                                        String lieuNumero = jadresse.getAsJsonPrimitive("numero").getAsString();
                                        String lieuVoie = jadresse.getAsJsonPrimitive("voie").getAsString();
                                        String lieuCodePostal = jadresse.getAsJsonPrimitive("codePostal").getAsString();
                                        String lieuCommune = jadresse.getAsJsonPrimitive("commune").getAsString();
                                        String lieu = lieuNumero + " " + lieuVoie + " " + lieuCodePostal + " " + lieuCommune;

                                        JsonObject jlocation = result.getAsJsonObject("location");
                                        String longitude = jlocation.getAsJsonArray("coordinates").get(0).toString();
                                        String latitude = jlocation.getAsJsonArray("coordinates").get(1).toString();
                                        System.out.println("LAT : " + latitude + " LONG : " + longitude);

                                        InstallationSportives_Bean install = new InstallationSportives_Bean(id, nom, null, lieu, latitude, longitude);

                                        monAdapter.add(install);

                                    } catch (Exception a) {
                                        a.printStackTrace();
                                    }
                                }
                            }
                        });

                    }else{
                        System.out.println("ERREUR RECHERCHE");
                    }
                }
                else if(prefixRecherche.equals("VT")){

                    if (!idRecherche.isEmpty() && idRecherche != null){
                        String URL2 = "https://api.foursquare.com/v2/venues/"+idRecherche+"?client_id="+clientID+"&client_secret="+clientSecret+"&v=20191104";

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

                                            try {
                                                JsonObject item = response.getResult().getAsJsonObject("response").getAsJsonObject("venue");
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
                                    }
                                });
                    }else{
                        System.out.println("ERREUR RECHERCHE");
                    }
                }
                //END
                monAdapter.sort(Favories_Bean.TRI_PAR_COMMUNE);

            }
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(Favorie.this, "Aucun favori présent !", Toast.LENGTH_LONG).show();
        }

        listVFavories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Favories_Bean oneFavori = (Favories_Bean) listVFavories.getAdapter().getItem(position);
                Intent intent = new Intent(Favorie.this, Detail.class);

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

        btRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //editor.clear();
                editor.remove("setFavories");
                editor.apply();
                finish();
                startActivity(getIntent());

            }
        });

    }
}
