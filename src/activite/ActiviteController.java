package activite;


import java.util.ArrayList;
import java.util.List;

public class ActiviteController {
    private final List<Activite> activites = new ArrayList<>();

    public final Activite model;
    public final ActiviteView view;

    public ActiviteController(Activite model, ActiviteView view) {
        this.model = model;
        this.view = view;
    }

    public ActiviteView getActiviteView() {
        return view;
    }

    public void ajouterActivite(Activite activite) {
        activites.add(activite);
    }

    public List<Activite> listerActivites() {
        return new ArrayList<>(activites);
    }

    public Activite rechercherActivite(String nom) {
        return activites.stream()
                .filter(a -> a.getNom().equalsIgnoreCase(nom))
                .findFirst()
                .orElse(null);
    }

    public boolean modifierActivite(String nom, Activite nouvelleActivite) {
        Activite activite = rechercherActivite(nom);
        if (activite != null) {
            activites.remove(activite);
            activites.add(nouvelleActivite);
            return true;
        }
        return false;
    }

    public boolean supprimerActivite(String nom) {
        Activite activite = rechercherActivite(nom);
        if (activite != null) {
            activites.remove(activite);
            return true;
        }
        return false;
    }

    public void demarrer() {
        int choix;
        do {
            view.afficherMenuActivites();
            choix = view.lireChoix();

            switch (choix) {
                case 1 -> {
                    Activite nouvelleActivite = view.lireNouvelleActivite();
                    ajouterActivite(nouvelleActivite);
                    view.afficherMessage("Activité ajoutée avec succès.");
                }
                case 2 -> {
                    List<Activite> activites = listerActivites();
                    view.afficherActivites(activites);
                }
                case 3 -> {
                    String nom = view.lireTexte("Nom de l'activité à modifier : ");
                    Activite nouvelleActivite = view.lireNouvelleActivite();
                    if (modifierActivite(nom, nouvelleActivite)) {
                        view.afficherMessage("Activité modifiée avec succès.");
                    } else {
                        view.afficherMessage("Activité non trouvée.");
                    }
                }
                case 4 -> {
                    String nom = view.lireTexte("Nom de l'activité à supprimer : ");
                    if (supprimerActivite(nom)) {
                        view.afficherMessage("Activité supprimée avec succès.");
                    } else {
                        view.afficherMessage("Activité non trouvée.");
                    }
                }
                case 0 -> view.afficherMessage("Retour au menu principal.");
                default -> view.afficherMessage("Choix invalide. Réessayez.");
            }
        } while (choix != 0);
    }
}