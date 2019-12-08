package com.example.miniprojetandroid;

import java.io.Serializable;
import java.util.Comparator;

public class VoyagesTransports_Bean extends Favories_Bean implements Serializable {

    private String image;

    public VoyagesTransports_Bean(String id, String nom, String description, String lieu, String latitudeGPS, String longitudeGPS, String image){
        super(id, nom, description, lieu, latitudeGPS, longitudeGPS);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
