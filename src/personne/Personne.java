package personne;

import activite.Activite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Personne implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private String club;
    private String mail;
    private boolean payementEnCours;
    private List<Activite> activites = new ArrayList<>();

    public Personne() {
    }

    public Personne(String nom, String prenom, String club, String mail, boolean payementEnCours) {
        this.nom = Objects.requireNonNull(nom);
        this.prenom = Objects.requireNonNull(prenom);
        this.club = club;
        this.mail = mail;
        this.payementEnCours = payementEnCours;
        this.activites = new ArrayList<>();
    }

    public void ajouterActivite(Activite activite) {
        activites.add(activite);
    }

    @Override
    public String toString() {
        return nom + " " + prenom + ", " + club + ", " + mail + ", " + payementEnCours+" "+activites;
    }

    public void setActivites(List<Activite> activites) {
        this.activites = activites;
    }
    public List<Activite> getActivites() {
        return activites;
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

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isPayemmentEnCours() {
        return payementEnCours;
    }

    public void setPayemmentEnCours(boolean payemmentEnCours) {
        this.payementEnCours = payemmentEnCours;
    }
}
