package activite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActiviteController {
    private List<Activite> activites = new ArrayList<>();
    private List<String> nomsActivites = new ArrayList<>();

    public final Activite model;
    public final ActiviteView view;

    public ActiviteController(Activite model, ActiviteView view) {
        this.model = model;
        this.view = view;
        this.model.charger();
        nomsActivites.addAll(activites.stream().map(Activite::getNom).toList());
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

    public void ajouterNomActivite(String nom) {
        if (!nomsActivites.contains(nom)) {
            nomsActivites.add(nom);
            model.sauvegarder();
            System.out.println("Nom d'activité ajouté : " + nom);
        } else {
            System.out.println("Cette activité existe déjà");
        }
    }

    public List<String> getNomsActivites() {
        return nomsActivites;
    }
}