package com.example.miniprojetandroid;

public class Favories_Bean {

    protected String nom;
    protected String description;

    public Favories_Bean(String nom, String description){
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
