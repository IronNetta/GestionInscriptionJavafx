package activite;

import java.util.List;
import java.util.Scanner;

public class ActiviteView {
    private String nomEntre;
    private final Scanner scanner = new Scanner(System.in);

    public void afficherActivites(List<Activite> activites) {
        if (activites.isEmpty()) {
            System.out.println("Aucune activité disponible.");
        } else {
            System.out.println("Liste des activités :");
            for (Activite activite : activites) {
                System.out.println(activite);
            }
        }
    }

    public Activite demanderNouvelleActivite() {
        System.out.print("Nom de l'activité : ");
        String nom = scanner.nextLine();
        System.out.print("Durée en heures : ");
        int heuresStage = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Logement nécessaire (oui/non) : ");
        boolean logement = scanner.nextLine().equalsIgnoreCase("oui");
        System.out.print("Repas du soir inclus (oui/non) : ");
        boolean repasSoir = scanner.nextLine().equalsIgnoreCase("oui");
        System.out.print("Activité le week-end (oui/non) : ");
        boolean estWeekend = scanner.nextLine().equalsIgnoreCase("oui");

        return new Activite(nom, heuresStage, logement, repasSoir, estWeekend);
    }

    public String demanderNomActivite() {
        System.out.print("Nom de l'activité : ");
        return scanner.nextLine();
    }

    public void afficherMenuActivites() {
        System.out.println("\n--- Gestion des Activités ---");
        System.out.println("1. Ajouter une activité");
        System.out.println("2. Lister les activités");
        System.out.println("3. Modifier une activité");
        System.out.println("4. Supprimer une activité");
        System.out.println("0. Retour au menu principal");
        System.out.print("Votre choix : ");
    }

    public int lireChoix() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public Activite lireNouvelleActivite() {
        System.out.print("Nom de l'activité : ");
        String nom = lireTexte();
        System.out.print("Durée (en heures) : ");
        int duree = lireEntier();
        System.out.print("Inclut un logement ? (oui/non) : ");
        boolean logement = lireBooleen();
        System.out.print("Inclut un souper ? (oui/non) : ");
        boolean souper = lireBooleen();
        System.out.print("Est-ce un stage de weekend ? (oui/non) : ");
        boolean weekend = lireBooleen();

        return new Activite(nom, duree, logement, souper, weekend);
    }

    public String lireTexte() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public String lireTexte(String prompt) {
        System.out.print(prompt);
        return lireTexte();
    }

    public int lireEntier() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un entier valide : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public boolean lireBooleen() {
        String reponse = lireTexte();
        return reponse.equalsIgnoreCase("oui");
    }

    public void simulerEntreeNom(String nom) {
        this.nomEntre = nom; // Stocke le nom à utiliser pour supprimer
    }
}

