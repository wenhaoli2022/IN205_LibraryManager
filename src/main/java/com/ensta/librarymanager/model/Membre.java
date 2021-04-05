package com.ensta.librarymanager.model;

public class Membre {

    int id;
    String nom;
    String prenom;
    String adresse;
    String email;
    String telephone;
    Abonnement type;

    @Override
    public String toString() {
        return "Membre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", email=" + email + ", telephone=" + telephone + ", abonnement=" + type + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Abonnement getAbonnement() {
        return type;
    }

    public void setAbonnement(Abonnement type) {
        this.type = type;
    }

    public Membre() {
    }

    public Membre(int id, String nom, String prenom, String adresse, String email, String telephone, Abonnement type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
    }

}