package com.ensta.librarymanager.model;
import java.time.LocalDate;

public class Emprunt {
	
	int id;
	Membre membre;
	Livre livre;
    LocalDate dateEmprunt;
    LocalDate dateRetour;

    @Override
    public String toString() {
        return "Emprunt [id=" + id + ", idMembre=" + membre.getId() + ", Livre=" + livre.getId() + ", dateEmprunt=" + dateEmprunt.toString() + ", dateRetour=" + dateRetour.toString() + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
    
    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }
    
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }
    
    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public Emprunt() {
    }

    public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }
}
