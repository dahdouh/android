package com.example.tp1exercice9;

import java.io.Serializable;

public class Evenement implements Serializable {

    private String date;
    private String heure;
    private String nom;
    private String description;

    public Evenement(String date, String heure, String nom, String description) {
        this.date = date;
        this.heure = heure;
        this.nom = nom;
        this.description = description;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setHeure(String heure){
        this.heure = heure;
    }

    public String getHeure() {
        return this.heure;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setDescription(String descirption){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.date +" => "+ this.nom +"\n " + this.heure+", "+description;
    }
}
