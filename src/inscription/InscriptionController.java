package inscription;

import java.util.ArrayList;
import java.util.List;

import activite.Activite;
import activite.ActiviteController;
import club.Club;
import club.ClubManager;
import personne.Personne;

public class InscriptionController {
    public final Inscription model;
    public final InscriptionView view;
    private final ActiviteController activiteController;
    private final ClubManager clubManager;
    private final List<Club> clubs = new ArrayList<>(List.of(Club.dataTest()));

    public InscriptionController(Inscription model, InscriptionView view, ActiviteController activiteController) {
        this.model = model;
        this.view = view;
        this.activiteController = activiteController;
        this.clubManager = new ClubManager();
    }


    public void demarrer() {
        model.charger();

        int choix;
        do {
            view.afficherMenu();
            choix = view.lireChoix();

            Personne nouvelEleve = new Personne();
            switch (choix) {
                case 1 -> ajouterEleve(nouvelEleve);
                case 2 -> listerEleves();
                case 3 -> rechercherParPrenom();
                case 4 -> rechercherParNom();
                case 5 -> modifierEleve();
                case 6 -> supprimerEleve();
                case 7 -> gererActivites();
                case 0 -> view.afficherMessage("Fermeture de l'application.");
                default -> view.afficherMessage("Choix invalide. Réessayez.");
            }
        } while (choix != 0);
    }

    private void gererActivites() {
        activiteController.demarrer();
    }

    public void modifierEleve() {
        String nom = view.lireNom();
        Personne eleve = model.rechercherEleve(e -> e.getNom().equalsIgnoreCase(nom));

        if (eleve == null) {
            view.afficherMessage("Aucun élève trouvé avec ce nom.");
            return;
        }

        boolean modificationTerminee = false;
        while (!modificationTerminee) {
            view.afficherMessage("\n--- Modification de l'élève ---");
            view.afficherMessage(eleve.toString());
            view.afficherMessage("Que souhaitez-vous modifier ?");
            view.afficherMessage("1. Nom");
            view.afficherMessage("2. Prénom");
            view.afficherMessage("3. Club");
            view.afficherMessage("4. Adresse email");
            view.afficherMessage("5. Paiement");
            view.afficherMessage("0. Terminer et sauvegarder");

            int choix = view.lireChoix();
            switch (choix) {
                case 1 -> {
                    String nouveauNom = view.lireNom();
                    eleve.setNom(nouveauNom);
                    view.afficherMessage("Nom modifié avec succès.");
                }
                case 2 -> {
                    String nouveauPrenom = view.lirePrenom();
                    eleve.setPrenom(nouveauPrenom);
                    view.afficherMessage("Prénom modifié avec succès.");
                }
                case 3 -> {
                    String nouveauClub = view.lireText("club");
                    eleve.setClub(nouveauClub);
                    view.afficherMessage("Club modifié avec succès.");
                }
                case 4 -> {
                    String nouveauEmail = view.lireText("email");
                    eleve.setMail(nouveauEmail);
                    view.afficherMessage("Email modifié avec succès.");
                }
                case 5 -> {
                    boolean paiement = view.lireBoolean("Attendez-vous un paiement ?");
                    eleve.setPayemmentEnCours(paiement);
                    view.afficherMessage("Paiement modifié avec succès.");
                }
                case 0 -> modificationTerminee = true;
                default -> view.afficherMessage("Choix invalide. Réessayez.");
            }
        }

        model.sauvegarder();
        view.afficherMessage("Élève modifié et sauvegardé avec succès.");
    }


    public void ajouterEleve(Personne nouvelEleve) {
        model.ajouterEleve(nouvelEleve);
        view.afficherMessage("Élève ajouté avec succès.");
    }

    public void listerEleves() {
    List<Personne> eleves = model.listerEleves();
    if (eleves.isEmpty()) {
        view.afficherMessage("Aucun élève inscrit.");
    } else {
        for (int i = 0; i < eleves.size(); i++) {
            System.out.println((i + 1) + ". " + eleves.get(i));
        }
    }
}

    private void rechercherParPrenom() {
        String prenom = view.lirePrenom();
        Personne eleve = model.rechercherEleve(e -> e.getPrenom().equalsIgnoreCase(prenom));
        view.afficherEleve(eleve);
    }

    private void rechercherParNom() {
        String nom = view.lireNom();
        Personne eleve = model.rechercherEleve(e -> e.getNom().equalsIgnoreCase(nom));
        view.afficherEleve(eleve);
    }

    public void supprimerEleve() {
        String nom = view.lireNom();
        Personne eleve = model.rechercherEleve(e -> e.getNom().equalsIgnoreCase(nom));
        if (eleve != null) {
            view.afficherMessage("Êtes-vous sûr?");
            model.supprimerEleve(eleve);
            view.afficherMessage("Élève supprimé avec succès.");
        } else {
            view.afficherMessage("Aucun élève trouvé.");
        }
    }

    public List<Club> getClubs() {
        return clubManager.getClubs();
    }

    public void ajouterClub(String nomClub) {
        clubManager.ajouterClub(nomClub);
    }

    public List<Activite> getActivites() {
        return activiteController.listerActivites();
    }

    public Activite rechercherActivite(String nom) {
        return activiteController.rechercherActivite(nom);
    }

    public void ajouterActivite(Activite activite) {
        activiteController.ajouterActivite(activite);
    }
}