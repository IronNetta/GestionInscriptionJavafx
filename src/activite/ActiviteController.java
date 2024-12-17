package activite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActiviteController {
    private final List<Activite> activites = new ArrayList<>();

    public final Activite model;
    public final ActiviteView view;

    public ActiviteController(Activite model, ActiviteView view) {
        this.model = model;
        this.view = view;
        this.model.charger();
    }

    public ActiviteView getActiviteView() {
        return view;
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

    public void ajouterActivite(Activite activite) {
            model.ajouterActivite(activite);
            view.afficherMessage("activité ajouté avec succès.");

    }

    public List<Activite> getActivites() {
        return activites;
    }
}