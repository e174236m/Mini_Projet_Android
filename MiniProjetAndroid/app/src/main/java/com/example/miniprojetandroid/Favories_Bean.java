package com.example.miniprojetandroid;

import java.io.Serializable;
import java.util.Comparator;

public class Favories_Bean implements Serializable {

    protected String id;
    protected String nom;
    protected String description;
    protected String lieu;
    protected String latitudeGPS;
    protected String longitudeGPS;

    public Favories_Bean(String id, String nom, String description, String lieu, String latitudeGPS, String longitudeGPS){
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.latitudeGPS = latitudeGPS;
        this.longitudeGPS = longitudeGPS;
    }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getCommune() {
        return lieu;
    }

    public void setCommune(String commune) {
        this.lieu = commune;
    }

    public String getLatitudeGPS() {
        return latitudeGPS;
    }

    public void setLatitudeGPS(String latitudeGPS) {
        this.latitudeGPS = latitudeGPS;
    }

    public String getLongitudeGPS() {
        return longitudeGPS;
    }

    public void setLongitudeGPS(String longitudeGPS) {
        this.longitudeGPS = longitudeGPS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Comparator<Favories_Bean> TRI_PAR_COMMUNE = new Comparator<Favories_Bean>() {
        @Override
        public int compare(Favories_Bean o1, Favories_Bean o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.nom, o2.nom);
        }
    };

    @Override
    public String toString() {
        return "Favories_Bean{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", latitudeGPS='" + latitudeGPS + '\'' +
                ", longitudeGPS='" + longitudeGPS + '\'' +
                '}';
    }
}
