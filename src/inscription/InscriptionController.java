package inscription;

import java.util.List;

import activite.Activite;
import activite.ActiviteController;
import club.Club;
import club.ClubManager;
import personne.Personne;

public class InscriptionController {
    public final Inscription model;
    public final InscriptionView view;
    public final Activite activiteModel;
    private final ActiviteController activiteController;
    private final ClubManager clubManager;

    public InscriptionController(Inscription model, InscriptionView view, Activite activiteModel, ActiviteController activiteController) {
        this.model = model;
        this.view = view;
        this.activiteModel = activiteModel;
        this.activiteController = activiteController;
        this.clubManager = new ClubManager();
    }


    public void ajouterEleve(Personne nouvelEleve) {
        model.ajouterEleve(nouvelEleve);
        view.afficherMessage("Élève ajouté avec succès.");
    }

    public void supprimerEleve() {
        String nom = view.lireNom();
        Personne eleve = model.rechercherEleve(e -> e.getNom().equals(nom));
        if (eleve != null) {
            model.supprimerEleve(eleve);
        }
    }

    public List<Club> getClubs() {
        return clubManager.getClubs();
    }

    public void ajouterClub(String nomClub) {
        clubManager.ajouterClub(nomClub);
    }

    public List<String> getNomsActivites() {
        return activiteModel.getActivites().stream().map(Activite::getNom).toList();
    }
}