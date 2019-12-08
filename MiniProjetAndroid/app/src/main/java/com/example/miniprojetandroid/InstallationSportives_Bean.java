package com.example.miniprojetandroid;

import java.io.Serializable;

public class InstallationSportives_Bean extends Favories_Bean implements Serializable {

    public InstallationSportives_Bean(String id, String nom, String description, String lieu, String latitudeGPS, String longitudeGPS){
        super(id, nom, description, lieu, latitudeGPS, longitudeGPS);
    }

}
