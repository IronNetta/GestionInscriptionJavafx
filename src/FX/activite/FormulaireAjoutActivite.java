package FX.activite;

import activite.Activite;
import activite.ActiviteController;
import inscription.InscriptionController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.util.List;


public class FormulaireAjoutActivite {
    public static Stage afficher(Stage parentStage, ActiviteController controller) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Ajouter une nouvelle activité");

        // Création des champs du formulaire
        Label labelNom = new Label("Nom de l'activité :");
        TextField textNom = new TextField();

        Label labelHeures = new Label("Durée en heures :");
        TextField textHeures = new TextField();

        Label labelLogement = new Label("Logement nécessaire :");
        CheckBox checkboxLogement = new CheckBox();

        Label labelRepas = new Label("Repas du soir inclus :");
        CheckBox checkboxRepas = new CheckBox();

        Label labelWeekend = new Label("Activité le week-end :");
        CheckBox checkboxWeekend = new CheckBox();

        Button btnAjouter = new Button("Enregistrer");
        Button btnAnnuler = new Button("Annuler");

        // Layout du formulaire
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(labelNom, 0, 0);
        grid.add(textNom, 1, 0);
        grid.add(labelHeures, 0, 1);
        grid.add(textHeures, 1, 1);
        grid.add(labelLogement, 0, 2);
        grid.add(checkboxLogement, 1, 2);
        grid.add(labelRepas, 0, 3);
        grid.add(checkboxRepas, 1, 3);
        grid.add(labelWeekend, 0, 4);
        grid.add(checkboxWeekend, 1, 4);
        grid.add(btnAjouter, 0, 5);
        grid.add(btnAnnuler, 1, 5);

        // Action du bouton Ajouter
        btnAjouter.setOnAction(event -> {
            String nom = textNom.getText().trim();
            int heuresStage;
            try {
                heuresStage = Integer.parseInt(textHeures.getText().trim());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("La durée doit être un nombre valide.");
                alert.showAndWait();
                return;
            }
            boolean logement = checkboxLogement.isSelected();
            boolean repasSoir = checkboxRepas.isSelected();
            boolean estWeekend = checkboxWeekend.isSelected();

            if (nom.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le nom de l'activité ne peut pas être vide.");
                alert.showAndWait();
            } else {
                // Vérifier si l'activité existe déjà
                if (controller.getActivites().stream()
                        .noneMatch(a -> a.getNom().equals(nom))) {
                    // Ajouter l'activité
                    Activite nouvelleActivite = new Activite(nom, heuresStage, logement, repasSoir, estWeekend);
                    controller.ajouterActivite(nouvelleActivite);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Activité '" + nom + "' ajoutée avec succès !");
                    alert.showAndWait();

                    stage.close(); // Fermer la fenêtre
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText(null);
                    alert.setContentText("Cette activité existe déjà.");
                    alert.showAndWait();
                }
            }
        });

        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> stage.close());

        // Affichage de la scène
        Scene scene = new Scene(grid, 300, 250);
        stage.setScene(scene);
        stage.showAndWait();
        return stage;
    }
}