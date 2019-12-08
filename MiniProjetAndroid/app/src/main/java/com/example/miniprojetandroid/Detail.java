package com.example.miniprojetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final TextView textDetailNom = findViewById(R.id.textDetailNom);
        final ImageView imgDetail = findViewById(R.id.imgDetail);
        final TextView textDetailAdresse = findViewById(R.id.textDetailAdresse);
        //final TextView textDetailDescription = findViewById(R.id.textDetailDescription);
        final RadioButton radioBtDetailOUI = findViewById(R.id.radioBtDetailOUI);
        final RadioButton radioBtDetailNON = findViewById(R.id.radioBtDetailNON);
        final Button buttonLienMap = findViewById(R.id.buttonLienMap);
        final RadioGroup groupRadio = (RadioGroup) findViewById(R.id.groupRadio);
        final String baseURL = "https://www.google.fr/maps/search/";
        final String URL;
        final Button btFavories = findViewById(R.id.btFavories);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        Bundle extras = getIntent().getExtras();
        final String key = extras.getString("KEY");
        final Favories_Bean objectFavorie = (Favories_Bean) extras.get("OBJECT");

        if("IS".equals(key)){
            InstallationSportives_Bean installationSportive = (InstallationSportives_Bean) objectFavorie;
            textDetailNom.setText(installationSportive.getNom());
            textDetailAdresse.setText(installationSportive.getCommune());
            //textDetailDescription.setText(installationSportive.getDescription());
            URL = baseURL+installationSportive.getLatitudeGPS()+","+installationSportive.getLongitudeGPS();
        }
        else {
            VoyagesTransports_Bean voyageTransport = (VoyagesTransports_Bean) objectFavorie;
            System.out.println(voyageTransport.toString());
            /*java.net.URL url = null;
            Bitmap bmp = null;
            try {
                url = new URL(voyageTransport.getImage());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            imgDetail.setImageBitmap(bmp);*/
            textDetailNom.setText(voyageTransport.getNom());
            textDetailAdresse.setText(voyageTransport.getCommune());
            //textDetailDescription.setText(voyageTransport.getDescription());
            URL = baseURL+voyageTransport.getLatitudeGPS()+","+voyageTransport.getLongitudeGPS();
        }

        Set<String> setFavories = preferences.getStringSet("setFavories", null);
        if(setFavories != null){
            Iterator iter = setFavories.iterator();
            String idObject = objectFavorie.getId();
            int flag = 0;
            while (iter.hasNext()) {
                String[] parts = iter.next().toString().split("-");
                if(idObject.equals(parts[1])) {
                    flag = 1;
                    radioBtDetailOUI.setChecked(true);
                    break;
                }
            }
            if(flag == 0){
                radioBtDetailNON.setChecked(true);
            }
        }

        buttonLienMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Set<String> setFavories = preferences.getStringSet("setFavories", null);
                System.out.println("SET FAVORIES : "+setFavories);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(URL));
                startActivity(intent);
            }
        });

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton ra=(RadioButton)findViewById(checkedId);
                Set<String> setFavories = new HashSet<>();
                if(preferences.getStringSet("setFavories",null) != null){
                    /*editor.clear();
                    editor.apply();*/
                    setFavories = preferences.getStringSet("setFavories",null);
                    editor.remove("setFavories");
                    editor.apply();
                }
                String idObjectFavorie = objectFavorie.getId();
                String prefix = "";
                if(objectFavorie instanceof InstallationSportives_Bean){
                    prefix = "IS";
                }else{
                    prefix = "VT";
                }

                if(ra.equals(radioBtDetailNON)){
                    if(setFavories.contains(prefix+"-"+idObjectFavorie)){
                        setFavories.remove(prefix+"-"+idObjectFavorie);
                    }
                }else if(ra.equals(radioBtDetailOUI)){
                    if(!setFavories.contains(prefix+"-"+idObjectFavorie)){
                        setFavories.add(prefix+"-"+idObjectFavorie);
                    }
                }
                editor.putStringSet("setFavories",setFavories);
                editor.commit();
            }
        });

        btFavories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent rq = new Intent(Detail.this, Favorie.class);
                startActivity(rq);

            }
        });

    }
}
