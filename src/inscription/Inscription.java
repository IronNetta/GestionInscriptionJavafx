package inscription;

import personne.Personne;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inscription {
    private static final String FILE_NAME = "ListeInscrit.json";

    private ArrayList<Personne> inscrit = new ArrayList<>();

    public void ajouterEleve(Personne eleve) {
        inscrit.add(eleve);
        sauvegarder();
    }

    public List<Personne> listerEleves() {
    return new ArrayList<>(inscrit);
}

public Personne rechercherEleve(Predicate<Personne> condition) {
    return inscrit.stream().filter(condition).findFirst().orElse(null);
}


    public void supprimerEleve(Personne eleve) {
        inscrit.remove(eleve);
        sauvegarder();
    }

    public void sauvegarder() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inscrit);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void charger() {
        System.out.println("Chemin du fichier : " + new File(FILE_NAME).getAbsolutePath());
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            inscrit = (ArrayList<Personne>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aucune donnée existante. Nouvelle liste créée.");
        }
    }
}