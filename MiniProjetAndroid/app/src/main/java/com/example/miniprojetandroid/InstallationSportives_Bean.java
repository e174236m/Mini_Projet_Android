package com.example.miniprojetandroid;

import java.util.Comparator;

public class InstallationSportives_Bean extends Favories_Bean {

    private String commune;
    private String coordonneesGPS;

    public InstallationSportives_Bean(String nom, String description, String commune, String coordonneesGPS){
        super(nom, description);
        this.commune = commune;
        this.coordonneesGPS = coordonneesGPS;
    }

    @Override
    public String toString() {
        return nom + " (" + commune + ")";
    }

    public static Comparator<InstallationSportives_Bean> TRI_PAR_COMMUNE = new Comparator<InstallationSportives_Bean>() {
        @Override
        public int compare(InstallationSportives_Bean o1, InstallationSportives_Bean o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.commune, o2.commune);
        }
    };
}
