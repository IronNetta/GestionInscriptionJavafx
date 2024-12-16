package club;

import inscription.InscriptionView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClubManager {
    private static final String FILE_NAME = "Clubs.json";
    private List<Club> clubs = new ArrayList<>();

    public ClubManager() {
        charger();
    }

    public List<Club> getClubs() {
        return new ArrayList<>(clubs); // Retourne une copie pour éviter les modifications directes
    }

    public void ajouterClub(String nomClub) {
        if (nomClub == null || nomClub.trim().isEmpty()) {
            System.out.println("Le nom du club ne peut pas être vide.");
            return;
        }

        if (clubs.stream().noneMatch(club -> club.getClubName().equalsIgnoreCase(nomClub))) {
            clubs.add(new Club(nomClub));
            sauvegarder();
            System.out.println("Club ajouté avec succès : " + nomClub);
        } else {
            System.out.println("Le club existe déjà : " + nomClub);
        }
    }

    public void sauvegarder() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(clubs);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des clubs : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void charger() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            clubs = (ArrayList<Club>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aucune donnée de clubs existante. Nouvelle liste créée.");
            clubs.addAll(List.of(Club.dataTest())); // Chargement des données par défaut
        }
    }
}