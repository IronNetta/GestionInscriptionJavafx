package activite;

import club.Club;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activite implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "NomActivites.json";
    private List<Activite> activites = new ArrayList<>();

    private String nom;
    private int heuresStage;
    private boolean logement;
    private boolean repasSoir;
    private boolean estWeekend;

    public Activite() {
    }

    public Activite(String nom, int heuresStage, boolean logement, boolean repasSoir, boolean estWeekend) {
        this.nom = nom;
        this.heuresStage = heuresStage;
        this.logement = logement;
        this.repasSoir = repasSoir;
        this.estWeekend = estWeekend;
    }

    public Activite(String activites) {
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getHeuresStage() { return heuresStage; }
    public void setHeuresStage(int heuresStage) { this.heuresStage = heuresStage; }

    public boolean isLogement() { return logement; }
    public void setLogement(boolean logement) { this.logement = logement; }

    public boolean isRepasSoir() { return repasSoir; }
    public void setRepasSoir(boolean repasSoir) { this.repasSoir = repasSoir; }

    public boolean isEstWeekend() { return estWeekend; }
    public void setEstWeekend(boolean estWeekend) { this.estWeekend = estWeekend; }

    @Override
    public String toString() {
        return "Activite{" +
                "nom='" + nom + '\'' +
                ", heuresStage=" + heuresStage +
                ", logement=" + logement +
                ", repasSoir=" + repasSoir +
                ", estWeekend=" + estWeekend +
                '}';
    }

    private List<String> nomsActivites = new ArrayList<>();

    public void sauvegarder() {
        if (nomsActivites == null || nomsActivites.isEmpty()) {
            System.out.println("Aucun nom d'activité à sauvegarder.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(nomsActivites);
            oos.flush();
            System.out.println("Noms d'activités sauvegardés : " + nomsActivites);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des noms d'activités : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void charger() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            nomsActivites = (List<String>) ois.readObject();
            System.out.println("Noms d'activités chargés : " + nomsActivites);
        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier trouvé. Une nouvelle liste sera créée.");
            nomsActivites = new ArrayList<>(Arrays.asList("Activité 1", "Activité 2", "Activité 3"));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des noms d'activités : " + e.getMessage());
        }
    }

    private static Activite[] dataTest() {
        Activite[] activites = new Activite[3];
        activites[0] = new Activite("activites1");
        activites[1] = new Activite("activites2");
        activites[2] = new Activite("activites3");
        return activites;
    }
    }

